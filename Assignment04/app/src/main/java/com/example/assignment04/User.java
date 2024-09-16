// Assignment 04
// User.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment04;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    public String name;
    public String email;
    public String role;

    public User() {
    }

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        role = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.email);
        parcel.writeString(this.role);
    }
}
