import java.util.Scanner;

// Practical 06 & 13: Import organized packages
import model.*;
import util.*;
import service.*;
import repository.*;

// Practical 08: Exception hierarchy
import exception.*;

// Practical 06: Static import requirement
import static util.Validator.isValidMobile;
import static util.Validator.isValidEmail;
import static util.Validator.isValidPan;
import static util.Validator.isValidIfsc;

/**
 * MiniBank — Java Console Banking Application
 * Student: Hetvi Sangani (Enrollment No: 25CE105)
 * Subject: Object-Oriented Programming (CEUE203) | Semester 3 (2026-27)
 * Progressively integrating Practicals 01 through 14.
 */
public class MiniBank {

    // 3. Define a record named BankInfo with two String fields: name and branch
    public record BankInfo(String name, String branch) {
        @Override
        public String toString() {
            return "========================================================\n" +
                   "        " + name + " — " + branch + " Branch\n" +
                   "========================================================";
        }
    }

    // 4. Define an enum named MenuOption with the required constants
    public enum MenuOption {
        OPEN_ACCOUNT(1, "Open New Account"),
        DEPOSIT(2, "Deposit Funds"),
        WITHDRAW(3, "Withdraw Funds"),
        TRANSFER(4, "Transfer Funds"),
        WORKING_HOURS(5, "View Bank Working Hours"),
        EXIT(6, "Exit MiniBank");

        private final int code;
        private final String description;

        MenuOption(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static MenuOption fromCode(int code) {
            for (MenuOption option : values()) {
                if (option.code == code) {
                    return option;
                }
            }
            return null;
        }
    }

    public static void displayMenu() {
        System.out.println("\n----------- MiniBank Operations Menu -----------");
        for (MenuOption option : MenuOption.values()) {
            System.out.printf("%d. %s%n", option.getCode(), option.getDescription());
        }
        System.out.print("Please enter your choice (1-" + MenuOption.values().length + "): ");
    }

    public static String handleChoice(MenuOption option) {
        return switch (option) {
            case OPEN_ACCOUNT -> "Open Account — Managed via BankService.openAccount(type, ownerName, balance, extraParam).";
            case DEPOSIT -> "Deposit Funds — Managed via BankService.deposit(accountNumber, amount).";
            case WITHDRAW -> "Withdraw Funds — Managed via BankService.withdraw(accountNumber, amount).";
            case TRANSFER -> "Transfer Funds — Managed via BankService.transfer(fromAccount, toAccount, amount).";
            case WORKING_HOURS -> "Working Hours: Monday to Friday, 09:30 AM – 04:30 PM IST (Saturday: 09:30 AM – 01:30 PM).";
            case EXIT -> "Thank you for banking with MiniBank. Have a great day!";
        };
    }

    // Helper method to parse numeric menu input safely
    private static int parseMenuChoice(String inputLine) {
        try {
            return Integer.parseInt(inputLine);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Practical 02: Encapsulation Demonstration
    public static void demonstrateEncapsulation() {
        System.out.println("\n--- [Practical 02] Encapsulation Demonstration ---");
        Account[] accounts = new Account[] {
            new SavingsAccount("Hetvi Sangani", 15000, 1000),
            new CurrentAccount("Diya Patel", 25000, 10000),
            new SavingsAccount("Rohan Mehta", 0, 0)
        };

        System.out.println("Initial State:");
        for (Account account : accounts) {
            System.out.printf("  Account [%s] Owner: %s | Balance: ₹%d | Active: %s%n",
                    account.getAccountNumber(), account.getOwnerName(), account.getBalance(), account.isActive());
        }

        System.out.println("\nExecuting transactions:");
        try {
            accounts[0].deposit(5000);
            System.out.printf("  Deposited ₹5000 to %s. New Balance: ₹%d%n",
                    accounts[0].getAccountNumber(), accounts[0].getBalance());

            boolean withdrawSuccess = accounts[1].withdraw(7000);
            System.out.printf("  Withdrew ₹7000 from %s (Success: %s). New Balance: ₹%d%n",
                    accounts[1].getAccountNumber(), withdrawSuccess, accounts[1].getBalance());

            try {
                accounts[2].withdraw(1000);
            } catch (InsufficientFundsException e) {
                System.out.printf("  Attempted to withdraw ₹1000 from %s: Caught expected InsufficientFundsException (Shortfall: ₹%d)%n",
                        accounts[2].getAccountNumber(), e.getShortfall());
            }

            try {
                accounts[0].deposit(-500);
            } catch (InvalidAmountException e) {
                System.out.printf("  Attempted negative deposit ₹(-500) to %s: Caught expected InvalidAmountException ('%s')%n",
                        accounts[0].getAccountNumber(), e.getMessage());
            }
        } catch (BankException e) {
            System.err.println("Unexpected banking exception in Practical 02 demo: " + e.getMessage());
        }

        System.out.println("\nFinal Balances after Practical 02 operations:");
        for (Account account : accounts) {
            System.out.printf("  %s (%s): ₹%d%n",
                    account.getAccountNumber(), account.getOwnerName(), account.getBalance());
        }
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 03: Object Methods Demonstration
    public static void demonstrateObjectMethods() {
        System.out.println("--- [Practical 03] Object Methods Demonstration ---");
        Account account1 = new SavingsAccount("Hetvi Sangani", 50000);
        Account account2 = new SavingsAccount("Hetvi Sangani", 50000);

        System.out.println("1. toString() verification:");
        System.out.println("   account1.toString() -> " + account1);
        System.out.println("   account2.toString() -> " + account2);

        System.out.println("\n2. equals() & hashCode() verification:");
        System.out.println("   account1.equals(account2) [Different generated IDs]: " + account1.equals(account2));
        System.out.println("   account1.equals(account1) [Same object]: " + account1.equals(account1));

        System.out.println("\n3. Static Nested Class & Cloneable verification:");
        Customer.Address address1 = new Customer.Address("101 Orchid Heights", "Anand", "388001");
        Customer customer1 = new Customer("Hetvi Sangani", "25ce105@charusat.edu.in", "9876543210", address1);
        Customer customerCloned = customer1.clone();

        System.out.println("   Original: " + customer1);
        System.out.println("   Cloned  : " + customerCloned);
        System.out.println("   customer1.equals(customerCloned): " + customer1.equals(customerCloned));
        System.out.println("   customer1 != customerCloned (Separate instances): " + (customer1 != customerCloned));

        System.out.println("\n4. instanceof type checks:");
        Object testObj1 = account1;
        Object testObj2 = customer1;
        if (testObj1 instanceof Account matchedAccount) {
            System.out.printf("   testObj1 is an Account with number %s%n", matchedAccount.getAccountNumber());
        }
        if (testObj2 instanceof Customer matchedCustomer) {
            System.out.printf("   testObj2 is a Customer with ID %s%n", matchedCustomer.getCustomerId());
        }
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 04: Validation, Command Parsing & Statement Formatting
    public static void demonstrateValidationAndParsing() {
        System.out.println("--- [Practical 04] Validation, Command & Statement ---");

        System.out.println("1. Regex Validation Checks (via static import):");
        System.out.printf("   Mobile '9876543210' valid? %s | '12345' valid? %s%n",
                isValidMobile("9876543210"), isValidMobile("12345"));
        System.out.printf("   Email 'user@bank.com' valid? %s | 'bad-email' valid? %s%n",
                isValidEmail("user@bank.com"), isValidEmail("bad-email"));
        System.out.printf("   PAN 'ABCDE1234F' valid? %s | '12345ABCDE' valid? %s%n",
                isValidPan("ABCDE1234F"), isValidPan("12345ABCDE"));
        System.out.printf("   IFSC 'SBIN0001234' valid? %s | 'sbin123' valid? %s%n",
                isValidIfsc("SBIN0001234"), isValidIfsc("sbin123"));

        System.out.println("\n2. Command Parsing Test:");
        String commandText = "DEPOSIT AC0001 500";
        Command command = CommandParser.parse(commandText);
        System.out.println("   Input string : " + commandText);
        System.out.println("   Parsed Command: " + command);
        System.out.printf("   Fields -> Type: %s | Account: %s | Amount: ₹%d%n",
                command.type(), command.accountNumber(), command.amount());

        System.out.println("\n3. StatementFormatter Output:");
        Account demoAccount = new SavingsAccount("Hetvi Sangani", 35000);
        String statement = StatementFormatter.buildStatement(demoAccount);
        System.out.print(statement);
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 05: Inheritance and Dynamic Polymorphism
    public static void demonstrateInheritanceAndPolymorphism() {
        System.out.println("--- [Practical 05] Inheritance & Polymorphism ---");
        Account[] accountPortfolio = new Account[] {
            new SavingsAccount("Hetvi Sangani", 20000, 2000),
            new CurrentAccount("Apex Global Trading", 10000, 50000),
            new FixedDepositAccount("Suman Sharma", 100000, 24)
        };

        System.out.println("Polymorphic Interest Rates & Pattern Matching:");
        for (Account account : accountPortfolio) {
            System.out.printf("  Account [%s] Type: %-20s | Owner: %-20s | Balance: ₹%-7d | Rate: %.1f%%%n",
                    account.getAccountNumber(), account.getClass().getSimpleName(),
                    account.getOwnerName(), account.getBalance(), account.interestRate());

            if (account instanceof CurrentAccount currentAccount) {
                System.out.printf("    -> [Special Handling] Current Account Overdraft Facility Active: up to ₹%d%n",
                        currentAccount.getOverdraftLimit());
            } else if (account instanceof SavingsAccount savingsAccount) {
                System.out.printf("    -> [Special Handling] Savings Account Minimum Reserve required: ₹%d%n",
                        savingsAccount.getMinBalance());
            } else if (account instanceof FixedDepositAccount fdAccount) {
                System.out.printf("    -> [Special Handling] Fixed Deposit Tenure: %d months (Locked)%n",
                        fdAccount.getTenureMonths());
            }
        }

        System.out.println("\nPolymorphic Withdrawal Verification:");
        try {
            try {
                accountPortfolio[0].withdraw(19000);
            } catch (InsufficientFundsException e) {
                System.out.printf("  SavingsAccount withdraw ₹19000 caught InsufficientFundsException: Shortfall = ₹%d | Balance = ₹%d%n",
                        e.getShortfall(), accountPortfolio[0].getBalance());
            }

            boolean currentWithdrawSuccess = accountPortfolio[1].withdraw(30000);
            System.out.printf("  CurrentAccount withdraw ₹30000 (Overdraft): Success = %s | Balance = ₹%d%n",
                    currentWithdrawSuccess, accountPortfolio[1].getBalance());

            try {
                accountPortfolio[2].withdraw(5000);
            } catch (InsufficientFundsException e) {
                System.out.printf("  FixedDepositAccount withdraw ₹5000 (Locked) caught InsufficientFundsException: Shortfall = ₹%d%n",
                        e.getShortfall());
            }
        } catch (BankException e) {
            System.err.println("Exception: " + e.getMessage());
        }

        System.out.println("--------------------------------------------------\n");
    }

    // Practical 06: Interfaces, Functional Interfaces, Lambdas, and Packages
    public static void demonstrateInterfacesAndPackages() {
        System.out.println("--- [Practical 06] Interfaces & Packages Demonstration ---");

        SavingsAccount savingsAccount = new SavingsAccount("Hetvi Sangani", 40000, 2000);
        CurrentAccount currentAccount = new CurrentAccount("Zenith Enterprises", 150000, 50000);

        try {
            Transactable transactableAccount = savingsAccount;
            transactableAccount.deposit(10000);
            System.out.printf("1. Transactable deposit ₹10000 into %s. New Balance: ₹%d%n",
                    savingsAccount.getAccountNumber(), savingsAccount.getBalance());
        } catch (BankException e) {
            System.err.println("Deposit failed: " + e.getMessage());
        }

        InterestBearing interestAccount = savingsAccount;
        System.out.printf("2. InterestBearing default yearlyInterest() for %s (Rate: %.1f%%): ₹%.2f%n",
                savingsAccount.getAccountNumber(), interestAccount.interestRate(), interestAccount.yearlyInterest());

        WithdrawRule vipRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.getBalance() > 0 && amount <= account.getBalance() + 20000;
            }
        };

        WithdrawRule dailyCapRule = (account, amount) -> amount <= 25000 && account.canWithdraw(amount);

        System.out.println("\n3. WithdrawRule Functional Interface Checks:");
        System.out.printf("   VIP Rule (Anonymous Class) allow ₹60000 on %s? %s%n",
                savingsAccount.getAccountNumber(), vipRule.allow(savingsAccount, 60000));
        System.out.printf("   Daily Cap Rule (Lambda, cap ₹25000) allow ₹20000 on %s? %s%n",
                savingsAccount.getAccountNumber(), dailyCapRule.allow(savingsAccount, 20000));
        System.out.printf("   Daily Cap Rule (Lambda, cap ₹25000) allow ₹35000 on %s? %s%n",
                savingsAccount.getAccountNumber(), dailyCapRule.allow(savingsAccount, 35000));

        System.out.println("\n4. Marker Interface (Premium) Verification:");
        System.out.printf("   Is CurrentAccount %s an instance of Premium? %s%n",
                currentAccount.getAccountNumber(), (currentAccount instanceof Premium));
        System.out.printf("   Is SavingsAccount %s an instance of Premium? %s%n",
                savingsAccount.getAccountNumber(), (savingsAccount instanceof Premium));

        System.out.println("--------------------------------------------------\n");
    }

    // Practical 07: Annotations and Reflection Demonstration
    public static void demonstrateAnnotationsAndReflection() {
        System.out.println("--- [Practical 07] Annotations & Reflection ---");

        Account validAccount = new SavingsAccount("Hetvi Sangani", 12000, 1000);
        String[] validErrors = AnnotationValidator.validate(validAccount);
        System.out.println("1. Valid Account Validation:");
        System.out.println("   Target: " + validAccount);
        System.out.println("   Errors: " + java.util.Arrays.toString(validErrors) + " (Expected: empty array)");

        System.out.println("\n2. Invalid (Negative Balance) Account via Reflection:");
        Account invalidAccount = new SavingsAccount("Invalid Test User", 500);
        try {
            java.lang.reflect.Field balanceField = Account.class.getDeclaredField("balance");
            balanceField.setAccessible(true);
            balanceField.set(invalidAccount, -100L);
        } catch (Exception e) {
            System.err.println("Could not reflectively inject negative balance: " + e.getMessage());
        }

        String[] negativeErrors = AnnotationValidator.validate(invalidAccount);
        System.out.println("   Target: " + invalidAccount);
        System.out.println("   Errors: " + java.util.Arrays.toString(negativeErrors));
        System.out.println("   Expected: [\"must be > 0\"] -> Matches: " +
                (negativeErrors.length > 0 && negativeErrors[0].contains("must be > 0")));

        System.out.println("--------------------------------------------------\n");
    }

    // Practical 08: Custom Exceptions, Safe Transfers & try-with-resources
    public static void demonstrateExceptionsAndSafeTransfers() {
        System.out.println("--- [Practical 08] Custom Exceptions & Safe Transfers ---");

        // 1. Expected PDF Output demonstration:
        // Account with balance 1000, calling withdraw(5000)
        Account testAccount = new SavingsAccount("Hetvi Sangani", 1000, 0);
        System.out.println("1. Testing InsufficientFundsException with shortfall:");
        System.out.printf("   Account %s initial balance: ₹%d%n", testAccount.getAccountNumber(), testAccount.getBalance());
        try {
            testAccount.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("   >>> Caught InsufficientFundsException: Withdrawal failed: short by " + e.getShortfall());
            System.out.printf("   Account %s balance unchanged: ₹%d (Expected: 1000)%n",
                    testAccount.getAccountNumber(), testAccount.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("   Caught InvalidAmountException: " + e.getMessage());
        }

        // 2. Testing InvalidAmountException
        System.out.println("\n2. Testing InvalidAmountException with zero/negative amount:");
        try {
            testAccount.deposit(-250);
        } catch (InvalidAmountException e) {
            System.out.println("   >>> Caught InvalidAmountException on deposit(-250): " + e.getMessage());
        }

        // 3. Testing safe transfer with try-catch-finally
        System.out.println("\n3. Testing safe inter-account transfer:");
        Account sourceAccount = new SavingsAccount("Hetvi Sangani", 8000, 1000);
        Account targetAccount = new SavingsAccount("Anita Sen", 2000, 500);

        try {
            sourceAccount.transfer(targetAccount, 3000);
            System.out.printf("   Post-transfer balances: %s = ₹%d | %s = ₹%d%n",
                    sourceAccount.getAccountNumber(), sourceAccount.getBalance(),
                    targetAccount.getAccountNumber(), targetAccount.getBalance());

            // Test over-transfer that triggers InsufficientFundsException
            System.out.println("   Attempting excessive transfer of ₹10,000:");
            sourceAccount.transfer(targetAccount, 10000);
        } catch (BankException e) {
            System.out.println("   >>> Caught BankException during transfer: " + e.getMessage());
        }

        // 4. try-with-resources demonstration using AutoCloseable BankingSession
        System.out.println("\n4. Testing try-with-resources with AutoCloseable BankingSession:");
        try (BankingSession session = new BankingSession(sourceAccount.getOwnerName())) {
            session.auditAction("Authorized online statement inspection");
            session.auditAction("Validated PAN & Mobile binding");
        } // session.close() is called automatically here!

        System.out.println("--------------------------------------------------\n");
    }

    // Practical 09: Multithreading and Synchronization
    public static void demonstrateMultithreadingAndSynchronization() {
        System.out.println("--- [Practical 09] Multithreading & Synchronization ---");

        int threadCount = 10;
        int operationsPerThread = 100;
        long depositAmount = 10;
        long expectedTotal = (long) threadCount * operationsPerThread * depositAmount; // 10,000

        // 1. Unsynchronized demonstration (Race condition)
        System.out.println("1. Running Unsynchronized Threads against single Account:");
        Account unsafeAccount = new SavingsAccount("Unsafe Demo Account", 0, 0);
        Thread[] unsafeThreads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            unsafeThreads[i] = new Thread(
                    new AccountWorker(unsafeAccount, operationsPerThread, depositAmount, false),
                    "UnsafeWorker-" + (i + 1)
            );
            unsafeThreads[i].start();
        }

        for (Thread workerThread : unsafeThreads) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long unsafeFinalBalance = unsafeAccount.getBalance();
        System.out.printf("   Expected Balance: ₹%d | Actual Balance: ₹%d%n", expectedTotal, unsafeFinalBalance);
        if (unsafeFinalBalance < expectedTotal) {
            System.out.printf("   >>> RACE CONDITION OBSERVED! Lost updates detected: ₹%d deficit.%n",
                    expectedTotal - unsafeFinalBalance);
        } else {
            System.out.println("   >>> Note: Race conditions are nondeterministic; thread interleaving was minimal in this execution.");
        }

        // 2. Synchronized demonstration (Thread-safe)
        System.out.println("\n2. Running Synchronized Threads against single Account:");
        Account safeAccount = new SavingsAccount("Safe Demo Account", 0, 0);
        Thread[] safeThreads = new Thread[threadCount];
        StringBuilder threadNames = new StringBuilder();

        for (int i = 0; i < threadCount; i++) {
            safeThreads[i] = new Thread(
                    new AccountWorker(safeAccount, operationsPerThread, depositAmount, true),
                    "SafeWorker-" + (i + 1)
            );
            if (i > 0) {
                threadNames.append(", ");
            }
            threadNames.append(safeThreads[i].getName());
            safeThreads[i].start();
        }
        System.out.println("   Started Worker Threads: [" + threadNames + "]");

        for (Thread workerThread : safeThreads) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long safeFinalBalance = safeAccount.getBalance();
        System.out.printf("   Expected Balance: ₹%d | Actual Balance: ₹%d%n", expectedTotal, safeFinalBalance);
        System.out.printf("   >>> THREAD SAFETY CONFIRMED: Balances match exactly: %s%n",
                (safeFinalBalance == expectedTotal));

        System.out.println("--------------------------------------------------\n");
    }

    // Practical 10: Concurrency, Thread Pool, Producer-Consumer, and Deadlock Prevention
    public static void demonstrateConcurrencyAndThreadCoordination() {
        System.out.println("--- [Practical 10] Concurrency Engine & Deadlock Prevention ---");

        // 1. TransactionProcessor fixed thread pool of 4
        System.out.println("1. Batch Processing via 4-Thread Pool:");
        TransactionProcessor processor = new TransactionProcessor(4);
        Account poolAccount = new SavingsAccount("Corporate Pool Account", 50000, 1000);

        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            processor.submit(() -> {
                try {
                    poolAccount.deposit(1000);
                    System.out.printf("   [Pool Task %d by %s] Deposited ₹1000. Current Balance: ₹%d%n",
                            taskId, Thread.currentThread().getName(), poolAccount.getBalance());
                } catch (InvalidAmountException e) {
                    System.err.println(e.getMessage());
                }
            });
        }
        processor.stop();
        System.out.println("   >>> Thread pool shutdown complete. Final Pool Account Balance: ₹" + poolAccount.getBalance());

        // 2. Producer-Consumer using wait() / notifyAll()
        System.out.println("\n2. Producer-Consumer Synchronization (Bounded Buffer):");
        TransactionBuffer buffer = new TransactionBuffer(3);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String transactionRecord = "TXN-" + i + " (₹" + (i * 500) + ")";
                    buffer.produce(transactionRecord);
                    System.out.println("   [Producer] Produced: " + transactionRecord);
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "TxnProducer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String transactionRecord = buffer.consume();
                    System.out.println("   [Consumer] Consumed & Settled: " + transactionRecord);
                    Thread.sleep(40);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "TxnConsumer");

        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("   >>> Producer-Consumer coordination finished successfully.");

        // 3. Deadlock avoidance demonstration
        System.out.println("\n3. Deadlock Avoidance via Consistent Lock Ordering:");
        Account accountA = new SavingsAccount("Branch Alpha", 30000, 1000);
        Account accountB = new SavingsAccount("Branch Beta", 30000, 1000);

        // Safe transfer using global lock ordering
        Thread transferAToB = new Thread(() -> {
            try {
                SafeTransferService.safeTransfer(accountA, accountB, 5000);
            } catch (BankException e) {
                System.err.println(e.getMessage());
            }
        }, "Transfer-A-to-B");

        Thread transferBToA = new Thread(() -> {
            try {
                SafeTransferService.safeTransfer(accountB, accountA, 3000);
            } catch (BankException e) {
                System.err.println(e.getMessage());
            }
        }, "Transfer-B-to-A");

        transferAToB.start();
        transferBToA.start();
        try {
            transferAToB.join();
            transferBToA.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.printf("   Transfers resolved without deadlock! %s = ₹%d | %s = ₹%d%n",
                accountA.getAccountNumber(), accountA.getBalance(),
                accountB.getAccountNumber(), accountB.getBalance());
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 11: File Handling, NIO.2 & Object Serialization
    public static void demonstratePersistenceAndReporting() {
        System.out.println("--- [Practical 11] File Persistence & NIO.2 Reporting ---");
        try {
            java.nio.file.Path statePath = java.nio.file.Paths.get("target", "minibank_state.dat");
            java.nio.file.Path logsDir = java.nio.file.Paths.get("target", "logs");
            java.nio.file.Path logFile = logsDir.resolve("daily_transactions.log");
            java.nio.file.Path reportPath = java.nio.file.Paths.get("target", "audit_report.txt");

            // 1. Serialization
            System.out.println("1. Binary Object Serialization (StatePersister):");
            Account[] accountsToSave = new Account[] {
                new SavingsAccount("Hetvi Sangani", 45000, 2000),
                new CurrentAccount("Zenith Logistics", 85000, 40000)
            };
            StatePersister.save(accountsToSave, statePath);
            System.out.printf("   Saved %d accounts to %s (File size: %d bytes)%n",
                    accountsToSave.length, statePath, java.nio.file.Files.size(statePath));

            // 2. Deserialization & Counter Synchronization
            Account[] loadedAccounts = StatePersister.load(statePath);
            System.out.printf("   Loaded %d accounts successfully. First account: %s, Balance: ₹%d%n",
                    loadedAccounts.length, loadedAccounts[0].getAccountNumber(), loadedAccounts[0].getBalance());

            Account nextAccount = new SavingsAccount("Next Sequential User", 12000);
            System.out.printf("   Next created account after sync: %s (Collision-free sequence)%n",
                    nextAccount.getAccountNumber());

            // 3. NIO.2 Append & Transaction Logs
            System.out.println("\n2. NIO.2 Transaction Logging (TransactionLog):");
            TransactionLog.append(logFile, "DEPOSIT AC0001 5000");
            TransactionLog.append(logFile, "WITHDRAW AC0002 2000");
            TransactionLog.append(logFile, "DEPOSIT AC0001 10000");
            System.out.printf("   Appended audit entries to %s%n", logFile);

            // 4. Report Generation
            System.out.println("\n3. Report Generation from Directory Streams (ReportGenerator):");
            String report = ReportGenerator.generateSummary(logsDir, reportPath);
            System.out.println(report);

        } catch (Exception e) {
            System.err.println("Persistence demo error: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 12: Java Collections Framework, Sorting & ConcurrentHashMap
    public static void demonstrateCollectionsAndThreadSafeStorage() {
        System.out.println("--- [Practical 12] Java Collections & Thread-Safe Storage ---");
        AccountStore store = new AccountStore();

        // 1. Populate HashMap
        Account account1 = new SavingsAccount("Hetvi Sangani", 15000, 1000);
        Account account2 = new CurrentAccount("Apex Solutions", 85000, 20000);
        Account account3 = new FixedDepositAccount("Geeta Bai", 50000, 12);
        Account account4 = new SavingsAccount("Aman Gupta", 120000, 5000);

        store.add(account1);
        store.add(account2);
        store.add(account3);
        store.add(account4);

        System.out.printf("1. AccountStore HashMap contains %d accounts.%n", store.size());
        Account fetched = store.get(account2.getAccountNumber());
        System.out.printf("   HashMap O(1) Lookup: %s -> %s | Balance: ₹%d%n",
                account2.getAccountNumber(), fetched.getOwnerName(), fetched.getBalance());

        // 2. Transaction History in ArrayList
        store.logTransaction("Account " + account1.getAccountNumber() + " opened with ₹15,000");
        store.logTransaction("Account " + account2.getAccountNumber() + " opened with ₹85,000");
        System.out.printf("   ArrayList Audit History: %s%n", store.getTransactionHistory());

        // 3. Natural Sort via Comparable (by Account Number)
        System.out.println("\n2. Natural Sorting via Comparable (by Account Number):");
        for (Account account : store.sortedNatural()) {
            System.out.printf("   [%s] %-18s | Balance: ₹%d%n",
                    account.getAccountNumber(), account.getOwnerName(), account.getBalance());
        }

        // 4. Custom Sort via Comparator (by Balance Descending)
        System.out.println("\n3. Custom Sorting via Comparator (by Balance Descending):");
        for (Account account : store.sortedByBalance()) {
            System.out.printf("   [%s] %-18s | Balance: ₹%d%n",
                    account.getAccountNumber(), account.getOwnerName(), account.getBalance());
        }

        // 5. ConcurrentHashMap verification with parallel threads
        System.out.println("\n4. ConcurrentHashMap Thread-Safe Operations:");
        Thread worker1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                Account tempAccount = new SavingsAccount("Worker1-User" + i, 1000 * i);
                store.putConcurrent(tempAccount.getAccountNumber(), tempAccount);
            }
        });

        Thread worker2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                Account tempAccount = new CurrentAccount("Worker2-User" + i, 2000 * i);
                store.putConcurrent(tempAccount.getAccountNumber(), tempAccount);
            }
        });

        worker1.start();
        worker2.start();
        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.printf("   ConcurrentHashMap thread-safe inserts completed. Total entries: %d%n",
                store.getConcurrentStore().size());
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 13: Generic Repository Pattern & Bounded Type Parameters
    public static void demonstrateGenericRepository() {
        System.out.println("--- [Practical 13] Generic Repository & Bounded Type Parameters ---");
        Repository<Account> accountRepo = new Repository<>();

        // 1. SaveAll varargs using reflective @Id discovery
        Account repoAcc1 = new SavingsAccount("Hetvi Sangani", 40000, 1000);
        Account repoAcc2 = new CurrentAccount("Reliance Retail", 250000, 50000);
        Account repoAcc3 = new FixedDepositAccount("Omkar Singh", 100000, 36);

        accountRepo.saveAll(repoAcc1, repoAcc2, repoAcc3);
        System.out.printf("1. Repository<Account> saved %d entities via reflective @Id resolution.%n",
                accountRepo.count());

        // 2. FindById
        Account found = accountRepo.findById(repoAcc2.getAccountNumber());
        System.out.printf("   findById(%s): Found -> %s | Balance: ₹%d%n",
                repoAcc2.getAccountNumber(), found.getOwnerName(), found.getBalance());

        // 3. FindAll
        System.out.println("\n2. findAll() Retrieved Managed Accounts:");
        for (Account account : accountRepo.findAll()) {
            System.out.printf("   [%s] %-18s (%-20s) | Balance: ₹%d%n",
                    account.getAccountNumber(), account.getOwnerName(),
                    account.getClass().getSimpleName(), account.getBalance());
        }

        // 4. Delete operation
        boolean deleted = accountRepo.delete(repoAcc3.getAccountNumber());
        System.out.printf("\n3. delete(%s) result: %s | Remaining count: %d%n",
                repoAcc3.getAccountNumber(), deleted, accountRepo.count());

        // 5. Bounded Generic Method: Repository.average(N[] values)
        System.out.println("\n4. Bounded Generic Method average(N[] extends Number):");
        Long[] balanceArray = new Long[] { 40000L, 250000L, 100000L };
        double avgBalance = Repository.average(balanceArray);
        System.out.printf("   Average Account Balance (Long[]): ₹%.2f%n", avgBalance);

        Double[] interestRates = new Double[] { 4.0, 0.0, 7.0 };
        double avgInterest = Repository.average(interestRates);
        System.out.printf("   Average Portfolio Interest Rate (Double[]): %.2f%%%n", avgInterest);
        System.out.println("--------------------------------------------------\n");
    }

    // Practical 14: Final Integration, Service Layer Orchestration & Packaging
    public static void demonstrateBankServiceIntegration() {
        System.out.println("--- [Practical 14] Full Service Layer Integration (BankService) ---");
        BankService service = new BankService();

        try {
            // 1. Open Accounts via BankService
            Account savingsAcc = service.openAccount("SAVINGS", "Hetvi Sangani", 30000, 1500);
            Account currentAcc = service.openAccount("CURRENT", "Quantum Exports", 75000, 25000);
            Account fdAcc = service.openAccount("FD", "Dinesh Kulkarni", 150000, 24);

            System.out.printf("1. Successfully opened 3 accounts via BankService:%n" +
                            "   -> Savings: %s (Balance: ₹%d)%n" +
                            "   -> Current: %s (Balance: ₹%d)%n" +
                            "   -> Fixed Deposit: %s (Balance: ₹%d)%n",
                    savingsAcc.getAccountNumber(), savingsAcc.getBalance(),
                    currentAcc.getAccountNumber(), currentAcc.getBalance(),
                    fdAcc.getAccountNumber(), fdAcc.getBalance());

            // 2. Perform Deposit & Withdrawal
            service.deposit(savingsAcc.getAccountNumber(), 5000);
            System.out.printf("\n2. Deposited ₹5000 into %s. New Balance: ₹%d%n",
                    savingsAcc.getAccountNumber(), savingsAcc.getBalance());

            service.withdraw(savingsAcc.getAccountNumber(), 2000);
            System.out.printf("   Withdrew ₹2000 from %s. New Balance: ₹%d%n",
                    savingsAcc.getAccountNumber(), savingsAcc.getBalance());

            // 3. Inter-Account Transfer
            System.out.println("\n3. Executing Inter-Account Transfer via SafeTransferService:");
            service.transfer(savingsAcc.getAccountNumber(), currentAcc.getAccountNumber(), 3000);
            System.out.printf("   Transferred ₹3000 from %s to %s.%n   %s Balance = ₹%d | %s Balance = ₹%d%n",
                    savingsAcc.getAccountNumber(), currentAcc.getAccountNumber(),
                    savingsAcc.getAccountNumber(), savingsAcc.getBalance(),
                    currentAcc.getAccountNumber(), currentAcc.getBalance());

            // 4. Formatted Statement Generation
            System.out.println("\n4. Statement Generation via BankService:");
            String statement = service.getStatement(savingsAcc.getAccountNumber());
            System.out.print(statement);

            // 5. Audit & Active Account Listing
            System.out.printf("5. Total accounts in BankService repository: %d%n", service.listAccounts().size());

        } catch (BankException e) {
            System.err.println("BankService operation failed: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------\n");
    }

    public static void run(Scanner scanner) {
        BankInfo header = new BankInfo("MiniBank India", "CHARUSAT Campus [Hetvi Sangani - 25CE105]");
        System.out.println(header);

        // Practical 14: Load saved accounts on start-up and save on exit
        BankService activeService = new BankService();
        java.nio.file.Path statePath = java.nio.file.Paths.get("target", "minibank_state.dat");
        try {
            activeService.loadAll(statePath);
        } catch (Exception ignored) {}

        demonstrateEncapsulation();
        demonstrateObjectMethods();
        demonstrateValidationAndParsing();
        demonstrateInheritanceAndPolymorphism();
        demonstrateInterfacesAndPackages();
        demonstrateAnnotationsAndReflection();
        demonstrateExceptionsAndSafeTransfers();
        demonstrateMultithreadingAndSynchronization();
        demonstrateConcurrencyAndThreadCoordination();
        demonstratePersistenceAndReporting();
        demonstrateCollectionsAndThreadSafeStorage();
        demonstrateGenericRepository();
        demonstrateBankServiceIntegration();

        boolean running = true;
        while (running) {
            displayMenu();
            if (!scanner.hasNextLine()) {
                break;
            }
            String inputLine = scanner.nextLine().trim();
            if (inputLine.isEmpty()) {
                continue;
            }

            int choice = parseMenuChoice(inputLine);
            if (choice == -1) {
                System.out.println("Invalid input: please enter a numeric choice.");
                continue;
            }

            MenuOption selected = MenuOption.fromCode(choice);
            if (selected == null) {
                System.out.println("Invalid choice: " + choice + ". Please select a valid number from the menu.");
                continue;
            }

            String response = handleChoice(selected);
            System.out.println(">>> " + response);

            if (selected == MenuOption.EXIT) {
                try {
                    activeService.saveAll(statePath);
                } catch (Exception ignored) {}
                running = false;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
    }
}
