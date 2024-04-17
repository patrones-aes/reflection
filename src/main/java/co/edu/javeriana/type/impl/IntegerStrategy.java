package co.edu.javeriana.type.impl;

import co.edu.javeriana.annotation.Column;
import co.edu.javeriana.type.BaseStrategy;

public class IntegerStrategy extends BaseStrategy {
    @Override
    public String createQuery(Column annotation) {
        return formatColumnDefinition(annotation.name(), "INT", annotation);
    }
}
