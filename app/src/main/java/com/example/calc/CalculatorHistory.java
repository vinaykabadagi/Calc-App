package com.example.calc;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage calculator history
 */
public class CalculatorHistory {
    
    public static class HistoryItem {
        private final String expression;
        private final double result;
        private final long timestamp;
        
        public HistoryItem(String expression, double result) {
            this.expression = expression;
            this.result = result;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getExpression() {
            return expression;
        }
        
        public double getResult() {
            return result;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        @Override
        public String toString() {
            String formattedResult = result == (int)result ? String.valueOf((int)result) : String.valueOf(result);
            return expression + " = " + formattedResult;
        }
    }
    
    private final List<HistoryItem> historyItems;
    private final int maxHistorySize;
    
    public CalculatorHistory(int maxHistorySize) {
        this.historyItems = new ArrayList<>();
        this.maxHistorySize = maxHistorySize;
    }
    
    /**
     * Adds a new item to history
     * @param expression The calculation expression
     * @param result The calculation result
     */
    public void addHistoryItem(String expression, double result) {
        HistoryItem item = new HistoryItem(expression, result);
        historyItems.add(item);
        
        // Remove oldest item if history exceeds max size
        if (historyItems.size() > maxHistorySize) {
            historyItems.remove(0);
        }
    }
    
    /**
     * Gets all history items
     * @return List of history items
     */
    public List<HistoryItem> getHistoryItems() {
        return new ArrayList<>(historyItems);
    }
    
    /**
     * Gets the most recent history item
     * @return The most recent history item, or null if history is empty
     */
    public HistoryItem getLastHistoryItem() {
        if (historyItems.isEmpty()) {
            return null;
        }
        return historyItems.get(historyItems.size() - 1);
    }
    
    /**
     * Clears all history
     */
    public void clearHistory() {
        historyItems.clear();
    }
} 