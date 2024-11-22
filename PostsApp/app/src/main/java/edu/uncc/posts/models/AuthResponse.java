package edu.uncc.posts.models;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    String user_id;
    String user_fullname;
    String token;
    String email;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public String getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

/*

    {
    "status": "ok",
    "token": "wcUJRckNsODlsbkgyZUJS",
    "user_id": 1,
    "user_fullname": "Alice Smith"
}
     */
}
