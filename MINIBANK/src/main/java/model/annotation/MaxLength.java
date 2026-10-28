package model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation specifying the maximum allowed string length.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
    int value();
    String message() default "length exceeds maximum allowed";
}
