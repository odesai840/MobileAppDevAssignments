package com.example.assignment05;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewName;
    TextView textViewEmail;
    TextView textViewAge;
    TextView textViewCountry;
    TextView textViewDoB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewName = findViewById(R.id.name);
        textViewEmail = findViewById(R.id.email);
        textViewAge = findViewById(R.id.age);
        textViewCountry = findViewById(R.id.country);
        textViewDoB = findViewById(R.id.dob);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUserActivity.USER_KEY)){
            User user = getIntent().getParcelableExtra(CreateUserActivity.USER_KEY);
            textViewName.setText(user.name);
            textViewEmail.setText(user.email);
            textViewAge.setText(user.age);
            textViewCountry.setText(user.country);
            textViewDoB.setText(user.DoB);
        }
    }
}