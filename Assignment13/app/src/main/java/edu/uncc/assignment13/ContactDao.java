// Assignment 13
// ContactDao.java
// Ohm Desai and Sullivan Crouse

package edu.uncc.assignment13;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.uncc.assignment13.models.Contact;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Query("DELETE FROM contacts")
    void deleteAll();

    @Query("SELECT * FROM contacts WHERE id LIKE :id LIMIT 1")
    Contact findById(long id);

    @Update
    void update(Contact contact);

    @Insert
    void insertAll(Contact... contacts);

    @Delete
    void delete(Contact contact);
}
