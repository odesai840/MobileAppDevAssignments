// Assignment 13
// AppDatabase.java
// Ohm Desai and Sullivan Crouse

package edu.uncc.assignment13;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.uncc.assignment13.models.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
