// Assignment1B
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment1b;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextTemperatureEntry;
    TextView textViewConversion;
    RadioGroup optionSelect;
    Boolean isFtoC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTemperatureEntry = findViewById(R.id.editTextTemperatureEntry);
        textViewConversion = findViewById(R.id.textViewConversion);
        optionSelect = findViewById(R.id.optionSelection);

        // F to C will be defualt value
        isFtoC = true;
        optionSelect.check(R.id.f_to_c_button);

        // Create base text view (from XML)
        String original_text = String.valueOf(textViewConversion.getText());


        // User selects conversion option
        optionSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                isFtoC = checkId == R.id.f_to_c_button;
            }
        });

        // User selects reset
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set edit text to null
                editTextTemperatureEntry.setText("");

                // Set text view to null
                textViewConversion.setText(original_text);

            }
        });

        // User selects calculate
        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // We already have our functions, just use right one
                if (isFtoC){
                    calculateCToF();
                } else {
                    calculateFToC();
                }

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