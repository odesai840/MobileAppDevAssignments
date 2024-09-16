// Assignment 04
// EditUser.java
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

public class EditUser extends AppCompatActivity {

    final static public String USER_KEY = "editedUser";

    EditText editTextName;
    EditText editTextEmail;
    RadioGroup radioGroupRoles;
    Button cancelButton;
    Button submitButton;
    String selectedRole;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupRoles = findViewById(R.id.radioGroupRoles);
        cancelButton = findViewById(R.id.cancelButton);
        submitButton = findViewById(R.id.submitButton);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUser.USER_KEY)){
            user = getIntent().getParcelableExtra(CreateUser.USER_KEY);
            editTextName.setText(user.name);
            editTextEmail.setText(user.email);
            selectedRole = user.role;
            if (selectedRole.equals("Student")){
                radioGroupRoles.check(R.id.studentRadio);
            } else if(selectedRole.equals("Employee")){
                radioGroupRoles.check(R.id.employeeRadio);
            } else if (selectedRole.equals("Other")) {
                radioGroupRoles.check(R.id.otherRadio);
            }
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createUserWithInfo();
                if (user != null) {
                    Intent intent = new Intent();
                    intent.putExtra(USER_KEY, user);
                    setResult(RESULT_OK, intent);
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
            Toast.makeText(EditUser.this, "Name is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (userEmail.isEmpty()) {
            Toast.makeText(EditUser.this, "Email is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (selectedRole == null || selectedRole.isEmpty()) {
            Toast.makeText(EditUser.this, "Role is required", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new User(userName, userEmail, selectedRole);
    }
}