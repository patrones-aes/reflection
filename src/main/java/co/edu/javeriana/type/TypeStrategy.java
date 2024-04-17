package co.edu.javeriana.type;

import co.edu.javeriana.annotation.Column;

public interface TypeStrategy {
    String createQuery(Column annotation);
}
