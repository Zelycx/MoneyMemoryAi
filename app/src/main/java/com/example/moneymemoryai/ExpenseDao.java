package com.example.moneymemoryai;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);

    @Query("SELECT SUM(amount) FROM expense")
    Double getTotalExpense();

    @Query("SELECT * FROM expense")
    List<Expense> getAllExpense();
}
