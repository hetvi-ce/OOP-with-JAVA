package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import repository.Repository;
import util.AnnotationValidator;
import util.StatePersister;
import util.StatementFormatter;
import util.TransactionLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * BankService orchestrates domain operations, validation, repository storage,
 * file logging, persistence, and safe transactional operations.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 06 & 14
 */
public class BankService {

    private final Repository<Account> repository;
    private final Path logPath;

    public BankService(Repository<Account> repository, Path logPath) {
        this.repository = repository != null ? repository : new Repository<>();
        this.logPath = logPath != null ? logPath : Paths.get("target", "logs", "service_transactions.log");
    }

    public BankService() {
        this(new Repository<>(), Paths.get("target", "logs", "service_transactions.log"));
    }

    public Repository<Account> getRepository() {
        return repository;
    }

    /**
     * Opens a new bank account with annotation-based domain validation.
     */
    public Account openAccount(String type, String ownerName, long initialBalance, long extraParam) throws BankException {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new InvalidAmountException("Owner name cannot be null or blank.");
        }
        if (initialBalance < 0) {
            throw new InvalidAmountException("Initial balance cannot be negative: ₹" + initialBalance);
        }

        String normalizedType = (type != null) ? type.trim().toUpperCase() : "SAVINGS";
        Account account = createAccountByType(normalizedType, ownerName.trim(), initialBalance, extraParam);

        // Validate annotations via reflection
        String[] violations = AnnotationValidator.validate(account);
        if (violations.length > 0) {
            throw new InvalidAmountException("Validation failed on new account: " + String.join(", ", violations));
        }

        repository.save(account);
        logAction("OPEN " + account.getAccountNumber() + " TYPE:" + account.getClass().getSimpleName() + " BAL:" + initialBalance);
        return account;
    }

    // Helper method to instantiate the appropriate Account subclass based on type
    private Account createAccountByType(String normalizedType, String cleanOwnerName, long initialBalance, long extraParam) {
        return switch (normalizedType) {
            case "CURRENT" -> new CurrentAccount(cleanOwnerName, initialBalance, extraParam > 0 ? extraParam : 10000);
            case "FD", "FIXED_DEPOSIT", "FIXED DEPOSIT" -> new FixedDepositAccount(cleanOwnerName, initialBalance, (int) (extraParam > 0 ? extraParam : 12));
            default -> new SavingsAccount(cleanOwnerName, initialBalance, extraParam > 0 ? extraParam : 1000);
        };
    }

    /**
     * Deposits funds into the targeted account with audit logging.
     */
    public synchronized void deposit(String accountNumber, long amount) throws BankException {
        Account account = getAccountOrThrow(accountNumber);
        account.deposit(amount);
        logAction("DEPOSIT " + accountNumber + " " + amount);
    }

    /**
     * Withdraws funds subject to account-specific rules and balance limits.
     */
    public synchronized boolean withdraw(String accountNumber, long amount) throws BankException {
        Account account = getAccountOrThrow(accountNumber);
        boolean success = account.withdraw(amount);
        if (success) {
            logAction("WITHDRAW " + accountNumber + " " + amount);
        }
        return success;
    }

    /**
     * Executes atomic, deadlock-free inter-account transfer.
     */
    public void transfer(String fromAccount, String toAccount, long amount) throws BankException {
        Account sourceAccount = getAccountOrThrow(fromAccount);
        Account targetAccount = getAccountOrThrow(toAccount);

        SafeTransferService.safeTransfer(sourceAccount, targetAccount, amount);
        logAction("TRANSFER FROM:" + fromAccount + " TO:" + toAccount + " " + amount);
    }

    /**
     * Formats and returns a comprehensive account statement.
     */
    public String getStatement(String accountNumber) throws BankException {
        Account account = getAccountOrThrow(accountNumber);
        return StatementFormatter.buildStatement(account);
    }

    /**
     * Lists all active accounts currently managed by the repository.
     */
    public List<Account> listAccounts() {
        return repository.findAll();
    }

    /**
     * Persists all repository accounts to disk via binary serialization.
     */
    public void saveAll(Path stateFile) throws IOException {
        List<Account> allAccounts = repository.findAll();
        StatePersister.save(allAccounts.toArray(new Account[0]), stateFile);
    }

    /**
     * Loads persisted accounts from disk and restores them into the repository.
     */
    public void loadAll(Path stateFile) throws IOException, ClassNotFoundException {
        Account[] loadedAccounts = StatePersister.load(stateFile);
        if (loadedAccounts != null) {
            for (Account account : loadedAccounts) {
                if (account != null) {
                    repository.save(account);
                }
            }
        }
    }

    private Account getAccountOrThrow(String accountNumber) throws AccountNotFoundException {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new AccountNotFoundException("Account number cannot be empty or null.");
        }
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    private void logAction(String message) {
        try {
            TransactionLog.append(logPath, message);
        } catch (IOException ignored) {
            // Logging failure does not disrupt banking transaction
        }
    }
}
