package com.zaalima.vaultcore.statements;

public class StatementTransaction {
    public String timestamp;
    public String reference;
    public String type; // DEBIT/CREDIT
    public double amount;

    public StatementTransaction(String timestamp, String reference, String type, double amount) {
        this.timestamp = timestamp;
        this.reference = reference;
        this.type = type;
        this.amount = amount;
    }
}
