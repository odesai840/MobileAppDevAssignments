// Assignment 07
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment07;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener, SelectDoBFragment.DoBSelectionListener, SelectCountryFragment.CountrySelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, new MainFragment())
                .commit();
    }

    public void gotoCreateUser(User user){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateUserFragment(), "create-user-fragment")
                .commit();
    }

    @Override
    public void gotoProfile(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, ProfileFragment.newInstance(user))
                .commit();
    }

    @Override
    public void gotoCountry() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectCountryFragment())
                .addToBackStack(null)
                .commit();
    }

    /*
        @Override
        public void gotoCountry(User user) {
            //getSupportFragmentManager().beginTransaction()

             //       .commit()
        }
    */
    @Override
    public void gotoDoB() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectDoBFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelDoBSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedDoB(String DoB) {

        CreateUserFragment userFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        userFragment.setDoB(DoB);
        if (userFragment != null){
            getSupportFragmentManager().popBackStack();
        }
    }


    @Override
    public void cancelCountrySelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedCountry(String country) {
        CreateUserFragment userFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        userFragment.setCountry(country);
        if (userFragment != null){
            getSupportFragmentManager().popBackStack();
        }
    }

}