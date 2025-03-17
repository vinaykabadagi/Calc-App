package com.example.calc;

import android.app.Application;

/**
 * Application class to maintain global state
 */
public class CalculatorApplication extends Application {
    
    private static final int MAX_HISTORY_SIZE = 20;
    private final CalculatorHistory calculatorHistory = new CalculatorHistory(MAX_HISTORY_SIZE);
    private final Calculator calculator = new Calculator();
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    /**
     * Gets the calculator history
     * @return The calculator history
     */
    public CalculatorHistory getCalculatorHistory() {
        return calculatorHistory;
    }
    
    /**
     * Gets the calculator
     * @return The calculator
     */
    public Calculator getCalculator() {
        return calculator;
    }
} 