package repository;

import model.annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Generic in-memory Repository<T> implementing CRUD operations.
 * Uses reflection to automatically identify fields annotated with @Id across class hierarchies.
 * Includes bounded generic utilities for numerical calculations.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 13
 *
 * @param <T> The entity type managed by this repository.
 */
public class Repository<T> {

    private final Map<String, T> storage;

    public Repository() {
        this.storage = new HashMap<>();
    }

    /**
     * Extracts the @Id field value from the entity using reflection, traversing superclasses.
     */
    private String extractId(T entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");
        Class<?> currentClass = entity.getClass();
        while (currentClass != null && currentClass != Object.class) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object idValue = field.get(entity);
                        return idValue != null ? idValue.toString() : null;
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Failed to access @Id field: " + field.getName(), e);
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        throw new IllegalArgumentException("Entity class " + entity.getClass().getName() + " lacks a field annotated with @Id");
    }

    /**
     * Saves or updates an entity, keyed by its reflective @Id.
     */
    public synchronized T save(T entity) {
        String id = extractId(entity);
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Entity @Id value cannot be null or empty");
        }
        storage.put(id, entity);
        return entity;
    }

    /**
     * Saves multiple entities using varargs.
     */
    @SafeVarargs
    public final synchronized void saveAll(T... items) {
        if (items != null) {
            for (T item : items) {
                if (item != null) {
                    save(item);
                }
            }
        }
    }

    /**
     * Retrieves an entity by its identifier.
     */
    public synchronized T findById(String id) {
        if (id == null) return null;
        return storage.get(id);
    }

    /**
     * Retrieves all managed entities.
     */
    public synchronized List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Deletes an entity by identifier.
     */
    public synchronized boolean delete(String id) {
        if (id == null) return false;
        return storage.remove(id) != null;
    }

    /**
     * Counts the total number of managed entities.
     */
    public synchronized int count() {
        return storage.size();
    }

    /**
     * Bounded generic method to compute average across any Number array (Long, Double, Integer, etc.).
     */
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N number : values) {
            if (number != null) {
                sum += number.doubleValue();
            }
        }
        return sum / values.length;
    }
}
