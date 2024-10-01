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
    public void gotoMarital() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectMaritalStatusFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoEdu() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectEduLevelFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoCountry() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectStateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoDoB() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectDoBFragment())
                .addToBackStack(null)
                .commit();
    }
}