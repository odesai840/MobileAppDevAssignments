package edu.uncc.posts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uncc.posts.models.AuthResponse;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener{

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //need to do the check related to the pin code
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.my_app_token),"");
        String app_username = sharedPref.getString(getString(R.string.my_app_username),"");
        String app_password = sharedPref.getString(getString(R.string.my_app_password),"");

        if  (token.isEmpty() || app_username.isEmpty() || app_password.isEmpty()){
            Log.d("test", token);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new LoginFragment())
                    .commit();
        } else {
            Log.d("test", app_username);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new PostsFragment())
                    .commit();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted(AuthResponse authResponse) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String token = authResponse.getToken();
        String app_username = authResponse.getUser_fullname();
        String app_password = authResponse.getPassword();
        editor.putString(getString(R.string.my_app_username),app_username);
        editor.apply();
        editor.putString(getString(R.string.my_app_password),app_password);
        editor.apply();
        editor.putString(getString(R.string.my_app_token), token);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PostsFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void logout() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.my_app_username),"");
        editor.putString(getString(R.string.my_app_password),"");
        editor.putString(getString(R.string.my_app_token), "");
        editor.commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void createPost() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreatePostFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToPosts() {
        getSupportFragmentManager().popBackStack();
    }
}