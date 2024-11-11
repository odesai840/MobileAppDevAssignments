package edu.uncc.assignment14;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import edu.uncc.assignment14.models.Bill;
import edu.uncc.assignment14.models.Converters;

@Database(entities = {Bill.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class BillsDatabase extends RoomDatabase {
    public abstract BillsDao billsDao();
}
