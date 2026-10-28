package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

/**
 * Transactable defines deposit and withdrawal capabilities with custom exception declarations.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 06 & 08
 */
public interface Transactable {
    void deposit(long amount) throws InvalidAmountException;
    boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
}
