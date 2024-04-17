package co.edu.javeriana.type.impl;

import co.edu.javeriana.annotation.Column;
import co.edu.javeriana.type.BaseStrategy;

public class StringStrategy extends BaseStrategy {
    @Override
    public String createQuery(Column annotation) {
        return formatColumnDefinition(annotation.name(), "VARCHAR(255)", annotation);
    }
}
