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

        ArrayList<Transaction> transactionList;
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

            transactionList = new ArrayList<>();

            rvHistory.setLayoutManager(new LinearLayoutManager(this));

            historyAdapter = new HistoryAdapter(transactionList);
            rvHistory.setAdapter(historyAdapter);

            loadTransactions();


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
        private void loadTransactions() {
            transactionList.clear();

            List<Income> incomes = incomeDao.getAllIncome();
            for (Income income : incomes) {
                transactionList.add(Transaction.fromIncome(income));
            }

            List<Expense> expenses = expenseDao.getAllExpense();
            for (Expense expense : expenses) {
                transactionList.add(Transaction.fromExpense(expense));
            }

            transactionList.sort((t1, t2) ->
                    Long.compare(t2.getTimestamp(), t1.getTimestamp()));

            historyAdapter.notifyDataSetChanged();
        }
    }