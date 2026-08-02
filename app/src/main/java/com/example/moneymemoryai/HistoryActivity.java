    package com.example.moneymemoryai;

    import android.os.Bundle;
    import android.util.Log;
    import android.widget.ImageButton;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import androidx.recyclerview.widget.RecyclerView;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.room.Room;

    import java.util.ArrayList;
    import java.util.List;

    public class HistoryActivity extends AppCompatActivity {

        RecyclerView rvHistory;
        ImageButton btnBack;
        AppDatabase db;
        IncomeDao incomeDao;
        ExpenseDao expenseDao;

        ArrayList<HistoryItem> historyList;
        HistoryAdapter historyAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_history);

            btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> {
                finish();
            });


            rvHistory = findViewById(R.id.rvHistory);

            db = Room.databaseBuilder(
                    getApplicationContext(),
                    AppDatabase.class,
                    "MoneyMemoryDB"
            ).allowMainThreadQueries().build();

            incomeDao = db.incomeDao();
            expenseDao = db.expenseDao();

            historyList = new ArrayList<>();

            rvHistory.setLayoutManager(new LinearLayoutManager(this));

            historyAdapter = new HistoryAdapter(historyList);
            rvHistory.setAdapter(historyAdapter);

            loadTransactions();


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
        private void loadTransactions() {

            historyList.clear();


            ArrayList<Transaction> allTransactions = new ArrayList<>();


            // Get incomes
            List<Income> incomes = incomeDao.getAllIncome();

            for (Income income : incomes) {

                allTransactions.add(
                        Transaction.fromIncome(income)
                );

            }


            // Get expenses
            List<Expense> expenses = expenseDao.getAllExpense();

            for (Expense expense : expenses) {

                allTransactions.add(
                        Transaction.fromExpense(expense)
                );

            }


            // Sort newest first
            allTransactions.sort((t1, t2) ->
                    Long.compare(
                            t2.getTimestamp(),
                            t1.getTimestamp()
                    )
            );


            String lastDate = "";


            for (Transaction transaction : allTransactions) {


                String currentDate = transaction.getDate();


                if (!currentDate.equals(lastDate)) {

                    historyList.add(
                            new HistoryItem(getFriendlyDate(currentDate))
                    );

                    lastDate = currentDate;
                }


                historyList.add(
                        new HistoryItem(transaction)
                );

            }


            historyAdapter.notifyDataSetChanged();

        }

        private String getFriendlyDate(String date) {

            if (date.equals(getTodayDate())) {
                return "Today";
            }

            if (date.equals(getYesterdayDate())) {
                return "Yesterday";
            }

            return date;
        }

        private String getTodayDate() {

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat(
                            "d/M/yyyy",
                            java.util.Locale.getDefault()
                    );

            return sdf.format(
                    new java.util.Date()
            );
        }


        private String getYesterdayDate() {

            java.util.Calendar calendar =
                    java.util.Calendar.getInstance();

            calendar.add(
                    java.util.Calendar.DAY_OF_YEAR,
                    -1
            );


            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat(
                            "d/M/yyyy",
                            java.util.Locale.getDefault()
                    );

            return sdf.format(
                    calendar.getTime()
            );
        }
    }