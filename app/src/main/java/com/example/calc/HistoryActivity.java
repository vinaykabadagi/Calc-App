package com.example.calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display calculator history
 */
public class HistoryActivity extends AppCompatActivity {
    
    public static final String EXTRA_SELECTED_RESULT = "com.example.calc.SELECTED_RESULT";
    private ListView historyListView;
    private TextView emptyHistoryTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        // Set up the action bar with back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Calculation History");
        }
        
        historyListView = findViewById(R.id.history_list_view);
        emptyHistoryTextView = findViewById(R.id.empty_history_text);
        
        // Get history from CalculatorApplication
        CalculatorApplication app = (CalculatorApplication) getApplication();
        List<CalculatorHistory.HistoryItem> historyItems = app.getCalculatorHistory().getHistoryItems();
        
        if (historyItems.isEmpty()) {
            historyListView.setVisibility(View.GONE);
            emptyHistoryTextView.setVisibility(View.VISIBLE);
        } else {
            historyListView.setVisibility(View.VISIBLE);
            emptyHistoryTextView.setVisibility(View.GONE);
            
            // Create custom adapter for better display
            ArrayAdapter<CalculatorHistory.HistoryItem> adapter = new ArrayAdapter<CalculatorHistory.HistoryItem>(
                    this,
                    android.R.layout.simple_list_item_1,
                    historyItems) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setTextColor(getResources().getColor(android.R.color.white));
                    textView.setText(getItem(position).toString());
                    return view;
                }
            };
            
            historyListView.setAdapter(adapter);
            
            // Set click listener to return selected result to main activity
            historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    double selectedResult = historyItems.get(position).getResult();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_SELECTED_RESULT, selectedResult);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 