package co.edu.javeriana.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {
    String name();
    boolean nullable() default false;
    boolean primaryKey() default false;
    boolean unique() default false;
}
