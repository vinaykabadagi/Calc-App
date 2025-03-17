package com.example.calc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_HISTORY = 1001;
    
    // UI Elements
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;
    private Button b_equal, b_multi, b_divide, b_add, b_sub, b_clear, b_dot;
    private Button b_mc, b_mr, b_m_plus, b_m_minus, b_history;
    private TextView t1, t2;
    
    // Calculator instance
    private Calculator calculator;
    private CalculatorHistory calculatorHistory;
    
    // Current expression being built
    private StringBuilder currentExpression = new StringBuilder();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get calculator and history from application
        CalculatorApplication app = (CalculatorApplication) getApplication();
        calculator = app.getCalculator();
        calculatorHistory = app.getCalculatorHistory();
        
        // Initialize UI elements
        initializeViews();
        setupButtonListeners();
    }
    
    private void initializeViews() {
        // Number buttons
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);
        b0 = findViewById(R.id.buttond0);
        
        // Operation buttons
        b_equal = findViewById(R.id.buttone);
        b_multi = findViewById(R.id.buttonm);
        b_divide = findViewById(R.id.buttond);
        b_add = findViewById(R.id.buttona);
        b_sub = findViewById(R.id.buttons);
        b_clear = findViewById(R.id.buttonc);
        b_dot = findViewById(R.id.buttonde);
        
        // Memory buttons
        b_mc = findViewById(R.id.button_mc);
        b_mr = findViewById(R.id.button_mr);
        b_m_plus = findViewById(R.id.button_m_plus);
        b_m_minus = findViewById(R.id.button_m_minus);
        b_history = findViewById(R.id.button_history);
        
        // Text views
        t1 = findViewById(R.id.input);
        t2 = findViewById(R.id.output);
    }
    
    private void setupButtonListeners() {
        // Set up number button listeners
        setupNumberButtonListener(b0, "0");
        setupNumberButtonListener(b1, "1");
        setupNumberButtonListener(b2, "2");
        setupNumberButtonListener(b3, "3");
        setupNumberButtonListener(b4, "4");
        setupNumberButtonListener(b5, "5");
        setupNumberButtonListener(b6, "6");
        setupNumberButtonListener(b7, "7");
        setupNumberButtonListener(b8, "8");
        setupNumberButtonListener(b9, "9");
        
        // Set up decimal point button listener
        b_dot.setOnClickListener(v -> {
            performHapticFeedback();
            if (!t1.getText().toString().contains(".")) {
                if (t1.getText().toString().isEmpty()) {
                    t1.setText("0.");
                } else {
                    t1.setText(t1.getText().toString() + ".");
                }
            }
            adjustTextSize();
        });
        
        // Set up operation button listeners
        setupOperationButtonListener(b_add, Calculator.ADDITION, "+");
        setupOperationButtonListener(b_sub, Calculator.SUBTRACTION, "-");
        setupOperationButtonListener(b_multi, Calculator.MULTIPLICATION, "×");
        setupOperationButtonListener(b_divide, Calculator.DIVISION, "÷");
        
        // Set up equals button listener
        b_equal.setOnClickListener(v -> {
            performHapticFeedback();
            if (!t1.getText().toString().isEmpty()) {
                try {
                    double inputValue = Double.parseDouble(t1.getText().toString());
                    
                    // Complete the expression with the final input value
                    String finalExpression = currentExpression.toString() + t1.getText().toString();
                    
                    // Perform calculation
                    double result = calculator.performOperation(inputValue);
                    
                    // Check for errors
                    if (calculator.hasError()) {
                        t2.setText(calculator.getErrorMessage());
                    } else {
                        // Format result
                        String resultText = calculator.isResultInteger() ? 
                                String.valueOf((int) result) : String.valueOf(result);
                        
                        // Add to history with the complete expression
                        calculatorHistory.addHistoryItem(finalExpression, result);
                        
                        // Display result
                        t2.setText(resultText);
                        currentExpression = new StringBuilder();
                    }
                    
                    t1.setText("");
                } catch (NumberFormatException e) {
                    t2.setText("Error");
                }
            } else {
                t2.setText("Error");
            }
            adjustTextSize();
        });
        
        // Set up clear button listener
        b_clear.setOnClickListener(v -> {
            performHapticFeedback();
            if (!t1.getText().toString().isEmpty()) {
                String text = t1.getText().toString();
                t1.setText(text.substring(0, text.length() - 1));
            } else {
                calculator.clear();
                t1.setText("");
                t2.setText("");
                currentExpression = new StringBuilder();
            }
            adjustTextSize();
        });
        
        // Long press on clear to reset everything
        b_clear.setOnLongClickListener(v -> {
            performHapticFeedback();
            calculator.clear();
            t1.setText("");
            t2.setText("");
            currentExpression = new StringBuilder();
            adjustTextSize();
            return true;
        });
        
        // Memory button listeners
        b_mc.setOnClickListener(v -> {
            performHapticFeedback();
            calculator.memoryClear();
            Toast.makeText(this, "Memory cleared", Toast.LENGTH_SHORT).show();
        });
        
        b_mr.setOnClickListener(v -> {
            performHapticFeedback();
            double memoryValue = calculator.memoryRecall();
            t1.setText(calculator.isResultInteger() ? 
                    String.valueOf((int) memoryValue) : String.valueOf(memoryValue));
            adjustTextSize();
        });
        
        b_m_plus.setOnClickListener(v -> {
            performHapticFeedback();
            if (!t1.getText().toString().isEmpty()) {
                try {
                    double inputValue = Double.parseDouble(t1.getText().toString());
                    calculator.performOperation(inputValue);
                    calculator.memoryAdd();
                    Toast.makeText(this, "Added to memory", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    t2.setText("Error");
                }
            }
        });
        
        b_m_minus.setOnClickListener(v -> {
            performHapticFeedback();
            if (!t1.getText().toString().isEmpty()) {
                try {
                    double inputValue = Double.parseDouble(t1.getText().toString());
                    calculator.performOperation(inputValue);
                    calculator.memorySubtract();
                    Toast.makeText(this, "Subtracted from memory", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    t2.setText("Error");
                }
            }
        });
        
        // History button listener
        b_history.setOnClickListener(v -> {
            performHapticFeedback();
            Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(historyIntent, REQUEST_HISTORY);
        });
    }
    
    private void setupNumberButtonListener(Button button, String number) {
        button.setOnClickListener(v -> {
            performHapticFeedback();
            if (t2.getText().toString().equals("Error")) {
                t2.setText("");
            }
            t1.setText(t1.getText().toString() + number);
            adjustTextSize();
        });
    }
    
    private void setupOperationButtonListener(Button button, char operation, String symbol) {
        button.setOnClickListener(v -> {
            performHapticFeedback();
            if (!t1.getText().toString().isEmpty()) {
                try {
                    double inputValue = Double.parseDouble(t1.getText().toString());
                    
                    // Add current input and operation to expression
                    currentExpression.append(t1.getText().toString()).append(" ").append(symbol).append(" ");
                    
                    // Perform calculation
                    double result = calculator.performOperation(inputValue);
                    
                    // Set next operation
                    calculator.setOperation(operation);
                    
                    // Check for errors
                    if (calculator.hasError()) {
                        t2.setText(calculator.getErrorMessage());
                    } else {
                        // Format result
                        String resultText = calculator.isResultInteger() ? 
                                String.valueOf((int) result) : String.valueOf(result);
                        
                        // Display intermediate result
                        t2.setText(resultText + symbol);
                    }
                    
                    t1.setText("");
                } catch (NumberFormatException e) {
                    t2.setText("Error");
                }
            } else {
                t2.setText("Error");
            }
            adjustTextSize();
        });
    }
    
    private void adjustTextSize() {
        // Adjust input text size
        if (t1.getText().toString().length() > 8) {
            t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        } else {
            t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        }
        
        // Adjust output text size
        if (t2.getText().toString().length() > 12) {
            t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        } else {
            t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }
    }
    
    private void performHapticFeedback() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_HISTORY && resultCode == RESULT_OK && data != null) {
            double selectedResult = data.getDoubleExtra(HistoryActivity.EXTRA_SELECTED_RESULT, 0);
            t1.setText(calculator.isResultInteger() ? 
                    String.valueOf((int) selectedResult) : String.valueOf(selectedResult));
            adjustTextSize();
        }
    }
}
