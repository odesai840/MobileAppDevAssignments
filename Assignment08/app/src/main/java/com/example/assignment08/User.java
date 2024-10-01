// Assignment 08
// User.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment08;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    public String name;
    public String email;
    public String phone;
    public String state;
    public String DoB;
    public String marital;
    public String edu;

    public User() {
    }

    public User(String name, String email, String phone, String state, String Dob,
                String marital, String edu) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.DoB = Dob;
        this.marital = marital;
        this.edu = edu;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        state = in.readString();
        DoB = in.readString();
        marital = in.readString();
        edu = in.readString();
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
        parcel.writeString(this.phone);
        parcel.writeString(this.state);
        parcel.writeString(this.DoB);
        parcel.writeString(this.marital);
        parcel.writeString(this.edu);
    }
}