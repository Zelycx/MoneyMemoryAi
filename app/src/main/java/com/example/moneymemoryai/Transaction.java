package com.example.moneymemoryai;

public class Transaction {

    private int id;
    private String type;
    private String title;
    private String date;
    private String details;

    private double amount;
    private boolean expanded = false;
    private long timestamp;


    public Transaction(
            int id,
            String type,
            double amount,
            String title,
            String date,
            String details,
            long timestamp
    ) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.title = title;
        this.date = date;
        this.details = details;
        this.timestamp = timestamp;
    }


    public static Transaction fromIncome(Income income) {
        return new Transaction(
                income.getId(),
                "Income",
                income.getAmount(),
                income.getSource(),
                income.getDate(),
                income.getDetails(),
                income.getTimestamp()
        );
    }


    public static Transaction fromExpense(Expense expense) {
        return new Transaction(
                expense.getId(),
                "Expense",
                expense.getAmount(),
                expense.getCategory(),
                expense.getDate(),
                "Item: " + expense.getItem() +
                        "\nStore: " + expense.getStore() +
                        "\nNotes: " + expense.getNotes(),
                expense.getTimestamp()
        );
    }


    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getDetails() {
        return details;
    }


    public String getType() {
        return type;
    }


    public String getDate() {
        return date;
    }


    public double getAmount() {
        return amount;
    }


    public boolean isExpanded() {
        return expanded;
    }


    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public long getTimestamp() {
        return timestamp;
    }


    public String getTime() {

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(
                        "h:mm a",
                        java.util.Locale.getDefault()
                );

        return sdf.format(
                new java.util.Date(timestamp)
        );
    }
}