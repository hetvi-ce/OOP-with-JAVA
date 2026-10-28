package model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation requiring numeric field values to be strictly positive (> 0).
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Positive {
    String message() default "must be > 0";
}
