package com.zaalima.vaultcore.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock-stock")
public class MockStockApiController {

    @GetMapping("/price/{symbol}")
    public ResponseEntity<Map<String, Object>> getPrice(@PathVariable String symbol) {

        double price = switch (symbol.toUpperCase()) {
            case "AAPL" -> 185.25;
            case "GOOGL" -> 142.60;
            case "TSLA" -> 255.40;
            default -> 100.00;
        };

        return ResponseEntity.ok(Map.of(
                "symbol", symbol,
                "price", price,
                "timestamp", System.currentTimeMillis()
        ));
    }
}

