package com.zaalima.vaultcore;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaalima.vaultcore.entity.Account;
import com.zaalima.vaultcore.repository.AccountRepository;
import com.zaalima.vaultcore.service.TransferService;

@SpringBootTest
class ConcurrencyTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountRepository accountRepository;

    private UUID fromAccountId;
    private UUID toAccountId;

    @BeforeEach
    void setup() {
        // Create FROM account with initial balance
        Account from = new Account();
        fromAccountId = UUID.randomUUID();
        from.setId(fromAccountId);
        from.setBalance(new BigDecimal("1000.00"));

        // Create TO account
        Account to = new Account();
        toAccountId = UUID.randomUUID();
        to.setId(toAccountId);
        to.setBalance(BigDecimal.ZERO);

        accountRepository.save(from);
        accountRepository.save(to);
    }

    @Test
    void test100ConcurrentWithdrawals() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(100);

        // Each thread tries to withdraw 10
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    transferService.transfer(
                            fromAccountId,
                            toAccountId,
                            new BigDecimal("10.00")
                    );
                } catch (Exception e) {
                    // Ignore failures (e.g., insufficient balance)
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        Account finalFromAccount =
                accountRepository.findById(fromAccountId).orElseThrow();

        //  FINAL ASSERTION (MOST IMPORTANT)
        assertTrue(
                finalFromAccount.getBalance().compareTo(BigDecimal.ZERO) >= 0,
                "Final balance should never be negative"
        );

        System.out.println(
                "Final balance after concurrency test: "
                        + finalFromAccount.getBalance()
        );
    }
}
