package com.example.moneymemoryai;

// Object Imports
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Activity Imports
import android.content.Intent;
import android.widget.TextView;

// Library Imports
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class HomeActivity extends AppCompatActivity {

    // widgets
    Button btnGain;
    TextView tvBalance;

    // database imports
    AppDatabase db;
    IncomeDao incomeDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "MoneyMemoryDB"
        ).allowMainThreadQueries().build();

        incomeDao = db.incomeDao();



        tvBalance = findViewById(R.id.tvBalance);
        updateBalance();

        btnGain = findViewById(R.id.btnGain); // the initialization of gain button
        // bottom are the onclick listeners for the button
        btnGain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to gain activity
                Intent intent = new Intent(HomeActivity.this, GainActivity.class);
                // start the activity
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBalance();
    }

    private void updateBalance() {
        Double totalIncome = incomeDao.getTotalIncome();

        if (totalIncome == null) {
            totalIncome = 0.0;
        }

        tvBalance.setText("₱" + totalIncome);
    }
}