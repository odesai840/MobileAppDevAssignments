// Assignment 07
// User.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment07;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Parcelable, Serializable {
    public String name;
    public String email;
    public String age;
    public String country;
    public String DoB;

    public User() {
    }

    public User(String name, String email, String age, String country, String Dob) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.country = country;
        this.DoB = Dob;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        age = in.readString();
        country = in.readString();
        DoB = in.readString();
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
        parcel.writeString(this.age);
        parcel.writeString(this.country);
        parcel.writeString(this.DoB);
    }
}