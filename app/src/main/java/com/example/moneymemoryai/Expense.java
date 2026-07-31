package com.example.moneymemoryai;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "expense")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private double amount;
    private String category;
    private String item;
    private String store;
    private String notes;
    private String date;

    public Expense(double amount, String category, String item, String store, String notes, String date) {
        this.amount = amount;
        this.category = category;
        this.item = item;
        this.store = store;
        this.notes = notes;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
