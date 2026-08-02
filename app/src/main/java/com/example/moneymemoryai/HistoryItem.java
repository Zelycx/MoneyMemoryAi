package com.example.moneymemoryai;

public class HistoryItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_TRANSACTION = 1;

    private int type;

    private String headerTitle;
    private Transaction transaction;


    // Header constructor
    public HistoryItem(String headerTitle) {
        this.type = TYPE_HEADER;
        this.headerTitle = headerTitle;
    }


    // Transaction constructor
    public HistoryItem(Transaction transaction) {
        this.type = TYPE_TRANSACTION;
        this.transaction = transaction;
    }


    public int getType() {
        return type;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }


    public Transaction getTransaction() {
        return transaction;
    }

}
