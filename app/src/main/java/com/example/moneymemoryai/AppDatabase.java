package com.example.moneymemoryai;

// imports for database
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {Income.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract IncomeDao incomeDao();

}
