package com.example.moneymemoryai;

// Object Imports to say that this is an entity/table
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "income")
public class Income {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String amount;
    private String source;
    private String details;
    private String date;

    public Income (String amount, String source, String details, String date) {
        this.amount = amount;
        this.source = source;
        this.details = details;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
