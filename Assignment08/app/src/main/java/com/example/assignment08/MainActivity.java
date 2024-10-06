// Assignment 08
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment08;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener, SelectDoBFragment.DoBSelectionListener, SelectStateFragment.CountrySelectionListener, SelectEduLevelFragment.EduSelectionListener, SelectMaritalStatusFragment.MaritalSelectionListener {

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, ProfileFragment.newInstance(user))
                .commit();
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

    @Override
    public void cancelDoBSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedDoB(String DoB) {

        CreateUserFragment userFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if (userFragment != null){
            userFragment.setDoB(DoB);
            getSupportFragmentManager().popBackStack();
        }
    }

    public void cancelCountrySelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedCountry(String country) {
        CreateUserFragment userFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if (userFragment != null){
            userFragment.setCountry(country);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelEduSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedEdu(String edu) {
        CreateUserFragment userFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if (userFragment != null){
            userFragment.setEdu(edu);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelMaritalSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedMarital(String marital) {
        CreateUserFragment userFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if (userFragment != null){
            userFragment.setMarital(marital);
            getSupportFragmentManager().popBackStack();
        }
    }
}