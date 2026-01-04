package com.zaalima.vaultcore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaalima.vaultcore.dto.TransferRequest;
import com.zaalima.vaultcore.service.TransferService;

@RestController
@RequestMapping("/api/transfer")
@CrossOrigin(origins = "http://localhost:5173")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        transferService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }
}

