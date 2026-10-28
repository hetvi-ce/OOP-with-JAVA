package model;

/**
 * InterestBearing interface providing interest rate and default annual interest calculation.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 06
 */
public interface InterestBearing {

    double interestRate();
    long getBalance();

    default double yearlyInterest() {
        return (interestRate() * getBalance()) / 100.0;
    }
}
