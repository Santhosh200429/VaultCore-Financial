package com.zaalima.vaultcore.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zaalima.vaultcore.entity.Account;
import com.zaalima.vaultcore.entity.LedgerEntry;
import com.zaalima.vaultcore.repository.AccountRepository;
import com.zaalima.vaultcore.repository.LedgerRepository;



@Service
public class TransferService {
	private final AccountRepository accountRepo;
    private final LedgerRepository ledgerRepo;

    public TransferService(AccountRepository accountRepo,
                           LedgerRepository ledgerRepo) {
        this.accountRepo = accountRepo;
        this.ledgerRepo = ledgerRepo;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(UUID fromId, UUID toId, BigDecimal amount) {

        Account from = accountRepo.findById(fromId).orElseThrow();
        Account to = accountRepo.findById(toId).orElseThrow();

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        from.debit(amount);
        to.credit(amount);

        accountRepo.save(from);
        accountRepo.save(to);

        ledgerRepo.save(new LedgerEntry(
                UUID.randomUUID(), fromId, amount, "DEBIT", Instant.now()));

        ledgerRepo.save(new LedgerEntry(
                UUID.randomUUID(), toId, amount, "CREDIT", Instant.now()));
    }
}
