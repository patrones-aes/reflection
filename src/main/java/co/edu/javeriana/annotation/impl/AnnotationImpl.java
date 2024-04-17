package co.edu.javeriana.annotation.impl;

import co.edu.javeriana.annotation.Column;
import co.edu.javeriana.annotation.Table;
import co.edu.javeriana.config.DatabaseConnectionConfig;
import co.edu.javeriana.type.TypeStrategy;
import co.edu.javeriana.type.impl.IntegerStrategy;
import co.edu.javeriana.type.impl.StringStrategy;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AnnotationImpl {
    public static void createTable(Object object) {
        if (Objects.isNull(object)) {
            throw new NullPointerException("Object cannot be null");
        }
        Class<?> objectClass = object.getClass();
        if (objectClass.isAnnotationPresent(Table.class)) {
            Table annotation = objectClass.getAnnotation(Table.class);
            try (Statement statement = DatabaseConnectionConfig.getConnection().createStatement()) {
                String query = "CREATE TABLE IF NOT EXISTS "
                        + annotation.name()
                        + "("
                        + getTableFields(objectClass)
                        + ")";
                statement.executeUpdate(query);
            } catch (SQLException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
    }

    private static String getTableFields(Class<?> objectClass) {
        StringBuilder statement = new StringBuilder();
        TypeStrategy strategy;
        String delimiter = "";
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column annotation = field.getAnnotation(Column.class);
                if (field.getType().equals(String.class)) {
                    strategy = new StringStrategy();
                } else if (field.getType().equals(Integer.class)) {
                    strategy = new IntegerStrategy();
                } else {
                    continue;
                }
                statement.append(delimiter).append(strategy.createQuery(annotation));
                delimiter = ",";
            }
        }
        return statement.toString();
    }
}
