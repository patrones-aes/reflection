package co.edu.javeriana.repository.impl;

import co.edu.javeriana.annotation.Column;
import co.edu.javeriana.annotation.Table;
import co.edu.javeriana.config.DatabaseConnectionConfig;
import co.edu.javeriana.repository.Repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
        return null;
    }

    @Override
    public List<T> readAll() {
        return List.of();
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public void delete(ID id) {

    }

    protected String getTableName() {
        if (type.isAnnotationPresent(Table.class)) {
            Table table = type.getAnnotation(Table.class);
            return table.name();
        }
        return type.getSimpleName().toLowerCase();
    }
}
