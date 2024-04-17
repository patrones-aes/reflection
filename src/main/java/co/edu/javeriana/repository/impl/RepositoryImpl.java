package co.edu.javeriana.repository.impl;

import co.edu.javeriana.annotation.Column;
import co.edu.javeriana.annotation.Table;
import co.edu.javeriana.config.DatabaseConnectionConfig;
import co.edu.javeriana.repository.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl<T, ID> implements Repository<T, ID> {
    private final Class<T> type;
    protected Connection connection;

    public RepositoryImpl(Class<T> type) {
        this.type = type;
        this.connection = DatabaseConnectionConfig.getConnection();
    }

    @Override
    public T create(T entity) {
        Field[] fields = type.getDeclaredFields();
        List<String> columnNames = new ArrayList<>();
        List<String> valuePlaceholders = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnNames.add(column.name());
                valuePlaceholders.add("?");

                try {
                    field.setAccessible(true);
                    values.add(field.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field", e);
                }
            }
        }

        String sql = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                getTableName(),
                String.join(", ", columnNames),
                String.join(", ", valuePlaceholders)
        );

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing insert", e);
        }
    }

    @Override
    public T readById(ID id) {
        String primaryKeyColumnName = getPrimaryKeyColumnName();
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", getTableName(), primaryKeyColumnName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing readById", e);
        }
        return null;
    }

    @Override
    public List<T> readAll() {
        List<T> entities = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", getTableName());
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                entities.add(mapResultSetToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing readAll", e);
        }
        return entities;
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public void delete(ID id) {
        String primaryKeyColumnName = getPrimaryKeyColumnName();
        String sql = String.format("DELETE FROM %s WHERE %s = ?", getTableName(), primaryKeyColumnName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting entity failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing delete", e);
        }
    }

    protected String getTableName() {
        if (type.isAnnotationPresent(Table.class)) {
            Table table = type.getAnnotation(Table.class);
            return table.name();
        }
        return type.getSimpleName().toLowerCase();
    }

    protected String getPrimaryKeyColumnName() {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (column.primaryKey()) {
                    return column.name();
                }
            }
        }
        throw new RuntimeException("Primary key column not found.");
    }

    private T mapResultSetToObject(ResultSet resultSet) throws SQLException {
        T entity;
        try {
            entity = type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error creating entity instance", e);
        }

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();
                try {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(columnName));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field", e);
                }
            }
        }

        return entity;
    }
}
