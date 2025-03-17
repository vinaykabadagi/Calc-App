package com.example.calc;

/**
 * Calculator class to handle all calculation logic separate from UI
 */
public class Calculator {
    // Operation constants
    public static final char ADDITION = '+';
    public static final char SUBTRACTION = '-';
    public static final char MULTIPLICATION = '*';
    public static final char DIVISION = '/';
    public static final char EQUAL = '=';
    
    private double memory = 0; // For memory functions
    private double currentValue = 0;
    private double previousValue = Double.NaN;
    private char currentOperation = EQUAL;
    private boolean isError = false;
    private String errorMessage = "";
    
    /**
     * Performs calculation based on the current operation
     * @param inputValue The new input value
     * @return The result of the calculation
     */
    public double performOperation(double inputValue) {
        isError = false;
        errorMessage = "";
        
        if (Double.isNaN(previousValue)) {
            previousValue = inputValue;
            return previousValue;
        }
        
        try {
            switch (currentOperation) {
                case ADDITION:
                    currentValue = previousValue + inputValue;
                    break;
                case SUBTRACTION:
                    currentValue = previousValue - inputValue;
                    break;
                case MULTIPLICATION:
                    currentValue = previousValue * inputValue;
                    break;
                case DIVISION:
                    if (inputValue == 0) {
                        isError = true;
                        errorMessage = "Cannot divide by zero";
                        return Double.NaN;
                    }
                    currentValue = previousValue / inputValue;
                    break;
                case EQUAL:
                    currentValue = inputValue;
                    break;
            }
            
            previousValue = currentValue;
            return currentValue;
        } catch (Exception e) {
            isError = true;
            errorMessage = "Calculation error";
            return Double.NaN;
        }
    }
    
    /**
     * Sets the current operation
     * @param operation The operation to set
     */
    public void setOperation(char operation) {
        currentOperation = operation;
    }
    
    /**
     * Clears the calculator state
     */
    public void clear() {
        previousValue = Double.NaN;
        currentValue = 0;
        currentOperation = EQUAL;
        isError = false;
        errorMessage = "";
    }
    
    /**
     * Checks if the result is an integer
     * @return true if the result is an integer, false otherwise
     */
    public boolean isResultInteger() {
        return previousValue == (int) previousValue;
    }
    
    /**
     * Checks if there is an error
     * @return true if there is an error, false otherwise
     */
    public boolean hasError() {
        return isError;
    }
    
    /**
     * Gets the error message
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Stores the current value in memory
     */
    public void memoryStore() {
        memory = previousValue;
    }
    
    /**
     * Adds the current value to memory
     */
    public void memoryAdd() {
        memory += previousValue;
    }
    
    /**
     * Subtracts the current value from memory
     */
    public void memorySubtract() {
        memory -= previousValue;
    }
    
    /**
     * Recalls the value from memory
     * @return The value stored in memory
     */
    public double memoryRecall() {
        return memory;
    }
    
    /**
     * Clears the memory
     */
    public void memoryClear() {
        memory = 0;
    }
} 