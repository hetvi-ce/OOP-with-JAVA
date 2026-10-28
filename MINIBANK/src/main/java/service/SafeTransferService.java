package service;

import model.Account;
import exception.BankException;
import exception.AccountNotFoundException;

/**
 * SafeTransferService provides deadlock-free transfers using global lock hierarchy ordering.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 10
 */
public class SafeTransferService {

    /**
     * Executes safe transfer by always acquiring locks in strict lexicographical order of accountNumber.
     * Always locking the smaller accountNumber first prevents circular wait and guarantees deadlock freedom.
     */
    public static void safeTransfer(Account sourceAccount, Account destinationAccount, long amount) throws BankException {
        if (sourceAccount == null || destinationAccount == null) {
            throw new AccountNotFoundException("Transfer failed: account cannot be null.");
        }
        if (sourceAccount.equals(destinationAccount)) {
            throw new BankException("Transfer failed: cannot transfer to identical account.");
        }

        // Determine consistent lock order once: smaller accountNumber is always locked first
        boolean lockSourceFirst = sourceAccount.getAccountNumber().compareTo(destinationAccount.getAccountNumber()) < 0;
        Account firstLock = lockSourceFirst ? sourceAccount : destinationAccount;
        Account secondLock = lockSourceFirst ? destinationAccount : sourceAccount;

        synchronized (firstLock) {
            synchronized (secondLock) {
                sourceAccount.withdraw(amount);
                destinationAccount.deposit(amount);
            }
        }
    }
}
