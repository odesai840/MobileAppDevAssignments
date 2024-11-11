// Assignment 13
// ContactDao.java
// Ohm Desai and Sullivan Crouse

package edu.uncc.assignment14;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.uncc.assignment14.models.Bill;

@Dao
public interface BillsDao {
    @Query("SELECT * FROM bills")
    List<Bill> getAll();

    @Query("DELETE FROM bills")
    void deleteAll();

    @Query("SELECT * FROM bills WHERE id LIKE :id LIMIT 1")
    Bill findById(long id);

    @Update
    void update(Bill bill);

    @Insert
    void insertAll(Bill... bills);

    @Delete
    void delete(Bill bill);
}
