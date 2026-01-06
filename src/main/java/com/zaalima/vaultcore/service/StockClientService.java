package com.zaalima.vaultcore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.zaalima.vaultcore.dto.StockPriceResponse;


@Service
public class StockClientService {

    private final WebClient webClient;

    public StockClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public StockPriceResponse getStockPrice(String symbol) {
        long start = System.currentTimeMillis();

        StockPriceResponse response = webClient.get()
                .uri("/mock-stock/price/{symbol}", symbol)
                .retrieve()
                .bodyToMono(StockPriceResponse.class)
                .block();

        long latency = System.currentTimeMillis() - start;
        response.setLatencyMs(latency);

        return response;
    }
}

