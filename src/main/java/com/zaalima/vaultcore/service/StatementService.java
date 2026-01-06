package com.zaalima.vaultcore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zaalima.vaultcore.statements.StatementTransaction;
import com.zaalima.vaultcore.statements.pdf.PdfService;

@Service
public class StatementService {
    private final PdfService pdfService;

    public StatementService(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    public byte[] generateMonthlyStatement(String username, int month, int year) {
        // TODO: integrate with ledger/repository to fetch immutable transactions for the account
        // For now return a minimal PDF with placeholders

        // Example safe account holder details - in real app fetch from user service
        String accountHolder = username != null ? username : "Unknown User";
        double opening = 1000.00;
        double closing = 1200.00;

        List<StatementTransaction> txns = List.of(
                new StatementTransaction("2025-12-01T10:00:00Z", "REF123", "CREDIT", 500.00),
                new StatementTransaction("2025-12-15T08:30:00Z", "REF124", "DEBIT", 300.00)
        );

        return pdfService.renderMonthlyStatement(accountHolder, month, year, opening, txns, closing);
    }
}
