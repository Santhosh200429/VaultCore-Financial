package com.zaalima.vaultcore.service;

import java.util.List;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zaalima.vaultcore.dto.PortfolioResponse;
import com.zaalima.vaultcore.dto.StockPriceResponse;

@Service
public class PortfolioService {

    private final StockClientService stockClient;

    public PortfolioService(StockClientService stockClient) {
        this.stockClient = stockClient;
    }

    
    
 // JWT-based (user identity already validated by Spring Security)
    public PortfolioResponse getPortfolio() {

        // 🔹 MOCK holdings (Week-3 requirement)
        List<PortfolioResponse.StockItem> stocks = List.of(
            buildStock("AAPL", 10),
            buildStock("GOOGL", 5)
        );

        return new PortfolioResponse(stocks);
    }
    
    private PortfolioResponse.StockItem buildStock(String symbol, int qty) {
        StockPriceResponse price = stockClient.getStockPrice(symbol);
        return new PortfolioResponse.StockItem(
                symbol,
                qty,
                price.getPrice(),
                price.getLatencyMs()
        );
    }




	
}
