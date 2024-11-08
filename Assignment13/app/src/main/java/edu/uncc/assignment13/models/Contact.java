// Assignment 13
// Contact.java
// Ohm Desai and Sullivan Crouse

package edu.uncc.assignment13.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo()
    public String name;
    @ColumnInfo()
    public String phone;
    @ColumnInfo()
    public String email;
    @ColumnInfo()
    public String phoneType;
    @ColumnInfo()
    public String group;

    public Contact(String name, String phone, String email, String phoneType, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.phoneType = phoneType;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
