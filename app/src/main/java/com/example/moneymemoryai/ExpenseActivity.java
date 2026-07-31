package com.example.moneymemoryai;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class ExpenseActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnSelectDate, btnSaveExpense;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    EditText etAmount;

    Spinner spCategory;
    EditText etItem;
    EditText etStore;
    EditText etNotes;

    AppDatabase db;
    ExpenseDao expenseDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "MoneyMemoryDB"
        ).allowMainThreadQueries().build();

        expenseDao = db.expenseDao();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnSelectDate = findViewById(R.id.btnSelectDate);
        calendar = Calendar.getInstance();
        btnSelectDate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(ExpenseActivity.this,
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
        spCategory = findViewById(R.id.spCategory);
        etItem = findViewById(R.id.etItem);
        etStore = findViewById(R.id.etStore);
        etNotes = findViewById(R.id.etNotes);

        btnSaveExpense = findViewById(R.id.btnSaveExpense);
        btnSaveExpense.setOnClickListener(v -> {

            String amountText = etAmount.getText().toString().trim();

            if (amountText.isEmpty()) {
                Toast.makeText(this, "Please enter an amount.", Toast.LENGTH_SHORT).show();
                return;
            }else if(spCategory.getSelectedItemPosition() == 0){
                Toast.makeText(this, "Please select a category.", Toast.LENGTH_SHORT).show();
                return;
            }else if(etItem.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter an item.", Toast.LENGTH_SHORT).show();
                return;
            }else if(etStore.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter a store.", Toast.LENGTH_SHORT).show();
                return;
            }else if(btnSelectDate.getText().toString().equals("Select Date")){
                Toast.makeText(this, "Please select a date.", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            Expense expense = new Expense(
                    amount,
                    spCategory.getSelectedItem().toString(),
                    etItem.getText().toString(),
                    etStore.getText().toString(),
                    etNotes.getText().toString(),
                    btnSelectDate.getText().toString()
            );

            expenseDao.insert(expense);

            Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show();
            etAmount.setText("");
            spCategory.setSelection(0);
            etItem.setText("");
            etStore.setText("");
            etNotes.setText("");
            btnSelectDate.setText("Select Date");
            etAmount.requestFocus();


        });







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}