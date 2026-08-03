package com.example.moneymemoryai;

// Room Imports
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// tells that this is a room database
@Dao
public interface IncomeDao {

    @Insert
    void insert(Income income);

    @Query("SELECT SUM(amount) FROM income")
    Double getTotalIncome();

    @Query("SELECT * FROM income")
    List<Income> getAllIncome();

    @Query("DELETE FROM income WHERE id = :id")
    void deleteById(int id);
}
