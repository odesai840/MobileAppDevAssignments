// Assignment 04
// CreateUser.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateUser extends AppCompatActivity {

    final static public String USER_KEY = "USER";

    EditText editTextName;
    EditText editTextEmail;
    RadioGroup radioGroupRoles;
    Button nextButton;
    String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupRoles = findViewById(R.id.radioGroupRoles);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createUserWithInfo();
                if (user != null) {
                    Intent intent = new Intent(CreateUser.this, Profile.class);
                    intent.putExtra(USER_KEY, user);
                    startActivity(intent);
                    finish();
                }
            }
        });

        radioGroupRoles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.studentRadio) {
                    selectedRole = "Student";
                } else if (i == R.id.employeeRadio) {
                    selectedRole = "Employee";
                } else if (i == R.id.otherRadio) {
                    selectedRole = "Other";
                }
            }
        });
    }

    private User createUserWithInfo(){
        String userName = editTextName.getText().toString();
        String userEmail = editTextEmail.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(CreateUser.this, "Name is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (userEmail.isEmpty()) {
            Toast.makeText(CreateUser.this, "Email is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (selectedRole == null || selectedRole.isEmpty()) {
            Toast.makeText(CreateUser.this, "Role is required", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new User(userName, userEmail, selectedRole);
    }
}