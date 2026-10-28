package model;

/**
 * Functional interface defining custom withdrawal authorization rules.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 06
 */
@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
