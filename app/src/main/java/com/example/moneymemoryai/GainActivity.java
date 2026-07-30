package com.example.moneymemoryai;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class GainActivity extends AppCompatActivity {

    // widgets
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    ImageButton btnBack;
    Button btnSelectDate;
    Button btnSaveIncome;
    EditText etAmount;
    EditText etDetails;
    Spinner spSource;

    // for database
    AppDatabase db;
    IncomeDao incomeDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gain);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "MoneyMemoryDB"
        ).allowMainThreadQueries().build();

        IncomeDao incomeDao = db.incomeDao();


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }});

        btnSelectDate = findViewById(R.id.btnSelectDate);
        calendar = Calendar.getInstance();

        btnSelectDate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(GainActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        btnSelectDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });

        etAmount = findViewById(R.id.etAmount);
        spSource = findViewById(R.id.spSource);
        etDetails = findViewById(R.id.etDetails);

        btnSaveIncome = findViewById(R.id.btnSaveIncome);
        btnSaveIncome.setOnClickListener(v -> {
            String amount = etAmount.getText().toString().trim();
            String details = etDetails.getText().toString().trim();
            if (amount.isEmpty()) {
                Toast.makeText(this, "Please enter an amount.", Toast.LENGTH_SHORT).show();
                return;
            } else if (spSource.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select a source.", Toast.LENGTH_SHORT).show();
                return;
            } else if (details.isEmpty()) {
                Toast.makeText(this, "Please enter details.", Toast.LENGTH_SHORT).show();
                return;
            } else if (btnSelectDate.getText().toString().trim().equals("Select Date")) {
                Toast.makeText(this, "Please select a date.", Toast.LENGTH_SHORT).show();
                return;
            }

            Income income = new Income(
                    amount,
                    spSource.getSelectedItem().toString(),
                    details,
                    btnSelectDate.getText().toString()
            );

            incomeDao.insert(income);

                Toast.makeText(this, "Income saved successfully!", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}