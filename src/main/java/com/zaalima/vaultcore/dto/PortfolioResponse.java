package com.zaalima.vaultcore.dto;


import java.math.BigDecimal;
import java.util.List;


public class PortfolioResponse {

	private List<StockItem> stocks;

    public PortfolioResponse(List<StockItem> stocks) {
        this.stocks = stocks;
    }

    public List<StockItem> getStocks() {
        return stocks;
    }

    // 🔴 MUST BE static
    public static class StockItem {

        private String symbol;
        private int quantity;
        private BigDecimal price;
        private long latencyMs;

        // 🔴 THIS CONSTRUCTOR MUST EXIST
        public StockItem(String symbol, int quantity, BigDecimal price, long latencyMs) {
            this.symbol = symbol;
            this.quantity = quantity;
            this.price = price;
            this.latencyMs = latencyMs;
        }

        public String getSymbol() {
            return symbol;
        }

        public int getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public long getLatencyMs() {
            return latencyMs;
        }
    }
}