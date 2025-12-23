
package com.zaalima.vaultcore.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ledger_entries")
@Immutable
public class LedgerEntry {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String entryType;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    
 // REQUIRED BY JPA
    protected LedgerEntry() {}

    //  CONSTRUCTOR
    public LedgerEntry(UUID id, UUID accountId,
                       BigDecimal amount, String entryType,
                       Instant createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.entryType = entryType;
        this.createdAt = createdAt;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
    
    
    
}
