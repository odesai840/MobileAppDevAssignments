// Assignment 06
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment06;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener, ProfileFragment.ProfileFragmentListener, EditUserFragment.EditUserFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, new MainFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void gotoCreateUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateUserFragment())
                .commit();
    }

    @Override
    public void gotoProfile(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, ProfileFragment.newInstance(user), "profile_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoEditUser(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, EditUserFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitToProfile(User user) {
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile_fragment");
        if(profileFragment != null){
            profileFragment.setmUser(user);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelEditUser() {
        getSupportFragmentManager().popBackStack();
    }

}