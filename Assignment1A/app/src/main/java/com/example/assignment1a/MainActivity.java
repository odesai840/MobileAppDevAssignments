// Assignment1A
// MainActivity.java
// Ohm Desai

package com.example.assignment1a;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextTemperatureEntry;
    TextView textViewConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTemperatureEntry = findViewById(R.id.editTextTemperatureEntry);
        textViewConversion = findViewById(R.id.textViewConversion);

        findViewById(R.id.buttonCToF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCToF();
            }
        });

        findViewById(R.id.buttonFToC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateFToC();
            }
        });

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTemperatureEntry.setText("");
                textViewConversion.setText("");
            }
        });
    }

    private void calculateCToF(){
        String enteredTemp = editTextTemperatureEntry.getText().toString();
        try{
            double temp = Double.valueOf(enteredTemp);
            double convertedTemp = (temp * (9.0/5.0)) + 32;
            textViewConversion.setText(String.valueOf(convertedTemp) + " F");
        } catch(NumberFormatException exception){
            Toast.makeText(MainActivity.this, "Enter a valid number !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateFToC(){
        String enteredTemp = editTextTemperatureEntry.getText().toString();
        try{
            double temp = Double.valueOf(enteredTemp);
            double convertedTemp = (temp - 32.0) * (5.0/9.0);
            textViewConversion.setText(String.valueOf(convertedTemp) + " C");
        } catch(NumberFormatException exception){
            Toast.makeText(MainActivity.this, "Enter a valid number !!", Toast.LENGTH_SHORT).show();
        }
    }
}