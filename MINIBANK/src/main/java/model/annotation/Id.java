package model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation identifying the primary unique identifier field of an entity.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 07 & 13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    // Marker annotation - indicates primary key field
}
