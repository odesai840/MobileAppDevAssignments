package com.example.assignment05;

import android.content.Intent;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateUserActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    Button countryButton;
    Button dobButton;
    Button submitButton;
    TextView textViewCountry;
    TextView textViewDoB;
    String selectedCountry;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        editTextName = findViewById(R.id.enterName);
        editTextEmail = findViewById(R.id.enterEmail);
        editTextAge = findViewById(R.id.enterAge);
        countryButton = findViewById(R.id.countryButton);
        dobButton = findViewById(R.id.DoBButton);
        submitButton = findViewById(R.id.submitButton);
        textViewCountry = findViewById(R.id.countrySelected);
        textViewDoB = findViewById(R.id.DoBSelected);

        ActivityResultLauncher<Intent> startDobActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getStringExtra(SelectDoBActivity.DATE_KEY) != null) {
                    String selectedDate = result.getData().getStringExtra(SelectDoBActivity.DATE_KEY);
                    textViewDoB.setText(selectedDate);
                }
            }
        });

        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);
                builder.setTitle("Choose a Country");
                builder.setSingleChoiceItems(Data.countries, -1, (dialog, which) -> {
                    selectedCountry = Data.countries[which];
                });
                builder.setPositiveButton("OK", (dialog, which) -> {
                    textViewCountry.setText(selectedCountry);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        });

        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateUserActivity.this, SelectDoBActivity.class);
                startDobActivityForResult.launch(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isValid = validateInputs();
                if (isValid){
                    Toast.makeText(CreateUserActivity.this, "All inputs are valid!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateInputs(){
        String userName = editTextName.getText().toString();
        String userEmail = editTextEmail.getText().toString();
        String userAge = editTextAge.getText().toString();
        String userCountry = textViewCountry.getText().toString();
        String userDoB = textViewDoB.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(CreateUserActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userEmail.isEmpty()) {
            Toast.makeText(CreateUserActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userAge.isEmpty()) {
            Toast.makeText(CreateUserActivity.this, "Age is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userCountry.equals("N/A")){
            Toast.makeText(CreateUserActivity.this, "Country is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userDoB.equals("N/A")){
            Toast.makeText(CreateUserActivity.this, "DoB is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}