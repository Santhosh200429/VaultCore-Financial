package com.zaalima.vaultcore.controller;

import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zaalima.vaultcore.service.StatementService;

@RestController
@CrossOrigin( origins = "http://localhost:5173", allowedHeaders = "*",
    exposedHeaders = HttpHeaders.CONTENT_DISPOSITION
)
public class StatementController {

    private final StatementService stmtSvc;

    public StatementController(StatementService stmtSvc) {
        this.stmtSvc = stmtSvc;
    }

    @GetMapping( path = "/api/statements/monthly", produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> monthly(
            @RequestParam int month,
            @RequestParam int year,
            Principal principal,
            Authentication auth) {

        String username = principal != null ? principal.getName() : "UNKNOWN";

        byte[] pdf = stmtSvc.generateMonthlyStatement(username, month, year);

        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=statement_" + month + "_" + year + ".pdf"
            )
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }
}