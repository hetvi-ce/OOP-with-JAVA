import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.Repository;
import service.AccountWorker;
import service.BankService;
import service.SafeTransferService;
import util.AnnotationValidator;
import util.Validator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Unit and Concurrency Test Suite for MiniBank.
 * Student: Hetvi Sangani (25CE105)
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 14
 */
public class MiniBankTest {

    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService();
    }

    @Test
    @DisplayName("Deposit increases account balance correctly")
    void testDeposit_IncreasesBalance() throws BankException {
        Account account = bankService.openAccount("SAVINGS", "Hetvi Sangani", 5000, 1000);
        assertEquals(5000, account.getBalance());

        bankService.deposit(account.getAccountNumber(), 2500);
        assertEquals(7500, account.getBalance());
    }

    @Test
    @DisplayName("Withdrawal beyond allowed limit throws InsufficientFundsException with correct shortfall")
    void testWithdraw_InsufficientFunds_ThrowsException() throws BankException {
        Account account = bankService.openAccount("SAVINGS", "Priya Mehra", 3000, 1000);

        InsufficientFundsException ex = assertThrows(InsufficientFundsException.class, () -> {
            bankService.withdraw(account.getAccountNumber(), 2500); // 3000 - 2500 = 500 < 1000 minBalance
        });

        assertEquals(500, ex.getShortfall());
        assertEquals(3000, account.getBalance(), "Balance must remain intact after failed withdrawal");
    }

    @Test
    @DisplayName("Invalid negative/zero amounts throw InvalidAmountException")
    void testInvalidAmountException() throws BankException {
        Account account = bankService.openAccount("CURRENT", "Alpha Corp", 10000, 5000);

        assertThrows(InvalidAmountException.class, () -> {
            bankService.deposit(account.getAccountNumber(), -500);
        });

        assertThrows(InvalidAmountException.class, () -> {
            bankService.withdraw(account.getAccountNumber(), 0);
        });
    }

    @Test
    @DisplayName("Concurrent deposits using 10 threads maintain exact thread safety")
    void testConcurrentDeposits_MaintainThreadSafety() throws InterruptedException {
        Account account = new SavingsAccount("Concurrent Test User", 0, 0);
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new AccountWorker(account, 1, 1000, true), "TestWorker-" + i);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        assertEquals(10000, account.getBalance(),
                "10 threads depositing ₹1,000 concurrently must yield exact balance ₹10,000");
    }

    @Test
    @DisplayName("Repository performs reflective @Id discovery and CRUD lifecycle")
    void testRepository_ReflectiveIdAndCrud() {
        Repository<Account> repo = new Repository<>();
        Account a1 = new SavingsAccount("Hetvi Sangani", 10000, 1000);
        Account a2 = new CurrentAccount("Bob", 20000, 5000);

        repo.save(a1);
        repo.save(a2);
        assertEquals(2, repo.count());

        Account found = repo.findById(a1.getAccountNumber());
        assertNotNull(found);
        assertEquals("Hetvi Sangani", found.getOwnerName());

        List<Account> all = repo.findAll();
        assertEquals(2, all.size());

        boolean deleted = repo.delete(a1.getAccountNumber());
        assertTrue(deleted);
        assertEquals(1, repo.count());
        assertNull(repo.findById(a1.getAccountNumber()));
    }

    @Test
    @DisplayName("Bounded generic average() computes numerical averages correctly")
    void testBoundedGenericAverage() {
        Long[] longs = { 1000L, 2000L, 3000L, 4000L };
        assertEquals(2500.0, Repository.average(longs), 0.001);

        Double[] doubles = { 10.5, 20.5 };
        assertEquals(15.5, Repository.average(doubles), 0.001);

        assertEquals(0.0, Repository.average(new Integer[0]), 0.001);
    }

    @Test
    @DisplayName("Validation regexes and annotation constraints operate as expected")
    void testValidationConstraints() {
        assertTrue(Validator.isValidMobile("9876543210"));
        assertFalse(Validator.isValidMobile("12345"));

        assertTrue(Validator.isValidEmail("user@bank.com"));
        assertFalse(Validator.isValidEmail("bad-email"));

        assertTrue(Validator.isValidPan("ABCDE1234F"));
        assertFalse(Validator.isValidPan("12345ABCDE"));

        assertTrue(Validator.isValidIfsc("SBIN0001234"));
        assertFalse(Validator.isValidIfsc("sbin123"));

        Account validAccount = new SavingsAccount("Hetvi Sangani", 12000, 1000);
        String[] cleanViolations = AnnotationValidator.validate(validAccount);
        assertEquals(0, cleanViolations.length);
    }

    @Test
    @DisplayName("Safe inter-account transfer avoids deadlock and preserves total balance")
    void testSafeTransfer_AtomicAndDeadlockFree() throws InterruptedException {
        Account accA = new SavingsAccount("User A", 50000, 1000);
        Account accB = new SavingsAccount("User B", 50000, 1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    SafeTransferService.safeTransfer(accA, accB, 100);
                } catch (BankException ignored) {}
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    SafeTransferService.safeTransfer(accB, accA, 100);
                } catch (BankException ignored) {}
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(100000, accA.getBalance() + accB.getBalance(),
                "Total funds across accounts must remain strictly conserved");
    }
}
