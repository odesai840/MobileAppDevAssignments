// Assignment 04
// Profile.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {

    final static public String USER_KEY = "USER";

    TextView textViewName;
    TextView textViewEmail;
    TextView textViewRole;
    Button updateButton;
    User user;

    ActivityResultLauncher<Intent> startEditUserForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getParcelableExtra(EditUser.USER_KEY) != null){
                user = result.getData().getParcelableExtra(EditUser.USER_KEY);
                textViewName.setText(user.name);
                textViewEmail.setText(user.email);
                textViewRole.setText(user.role);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewRole = findViewById(R.id.textViewRole);
        updateButton = findViewById(R.id.updateButton);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUser.USER_KEY)){
            user = getIntent().getParcelableExtra(CreateUser.USER_KEY);
            textViewName.setText(user.name);
            textViewEmail.setText(user.email);
            textViewRole.setText(user.role);
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, EditUser.class);
                if (user != null) {
                    intent.putExtra(USER_KEY, user);
                }
                startEditUserForResult.launch(intent);
            }
        });
    }
}