package com.example.assignment08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, new MainFragment())
                .commit();
    }

    public void gotoCreateUser(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateUserFragment(), "create-user-fragment")
                .commit();
    }

    @Override
    public void gotoProfile(User user) {

    }

    @Override
    public void gotoCountry() {

    }

    @Override
    public void gotoDoB() {

    }
}