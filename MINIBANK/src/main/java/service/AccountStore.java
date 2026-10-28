package service;

import model.Account;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AccountStore provides centralized in-memory storage using Java Collections Framework.
 * Demonstrates HashMap lookup, ArrayList history, Comparator & Comparable sorting,
 * and ConcurrentHashMap for high-concurrency access.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 12
 */
public class AccountStore {

    private final Map<String, Account> accounts;
    private final ConcurrentHashMap<String, Account> concurrentStore;
    private final List<String> transactionHistory;

    public AccountStore() {
        this.accounts = new HashMap<>();
        this.concurrentStore = new ConcurrentHashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public synchronized void add(Account account) {
        if (account != null && account.getAccountNumber() != null) {
            accounts.put(account.getAccountNumber(), account);
            concurrentStore.put(account.getAccountNumber(), account);
        }
    }

    public synchronized Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Account getConcurrent(String accountNumber) {
        return concurrentStore.get(accountNumber);
    }

    public void putConcurrent(String accountNumber, Account account) {
        concurrentStore.put(accountNumber, account);
    }

    public ConcurrentHashMap<String, Account> getConcurrentStore() {
        return concurrentStore;
    }

    public synchronized List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public synchronized void logTransaction(String record) {
        transactionHistory.add(record);
    }

    public synchronized List<String> getTransactionHistory() {
        return Collections.unmodifiableList(new ArrayList<>(transactionHistory));
    }

    /**
     * Natural sort using Account's Comparable implementation (by accountNumber ascending).
     */
    public synchronized List<Account> sortedNatural() {
        List<Account> accountList = new ArrayList<>(accounts.values());
        Collections.sort(accountList);
        return accountList;
    }

    /**
     * Custom sorting using Comparator (by balance descending).
     */
    public synchronized List<Account> sortedByBalance() {
        List<Account> accountList = new ArrayList<>(accounts.values());
        accountList.sort(new Comparator<Account>() {
            @Override
            public int compare(Account account1, Account account2) {
                // Descending order: higher balance comes first
                return Long.compare(account2.getBalance(), account1.getBalance());
            }
        });
        return accountList;
    }

    /**
     * Custom sorting using lambda Comparator (by owner name alphabetically).
     */
    public synchronized List<Account> sortedByOwnerName() {
        List<Account> accountList = new ArrayList<>(accounts.values());
        accountList.sort((account1, account2) -> account1.getOwnerName().compareToIgnoreCase(account2.getOwnerName()));
        return accountList;
    }

    public synchronized int size() {
        return accounts.size();
    }
}
