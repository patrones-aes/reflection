package co.edu.javeriana.type;

import co.edu.javeriana.annotation.Column;

public abstract class BaseStrategy implements TypeStrategy {
    protected String formatColumnDefinition(String columnName, String dataType, Column annotation) {
        return String.format(
                "%s %s %s %s %s",
                columnName,
                dataType,
                annotation.primaryKey() ? "PRIMARY KEY" : "",
                annotation.nullable() ? "" : "NOT NULL",
                annotation.unique() ? "UNIQUE" : ""
        ).trim();
    }
}
