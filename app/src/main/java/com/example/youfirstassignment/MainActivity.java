package com.example.youfirstassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youfirstassignment.sqlite.DataModel;
import com.example.youfirstassignment.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText edDate, edPointEarned, edPointsReedemed;
    final Calendar myCalendar = Calendar.getInstance();
    private Button btnSave;
    SQLiteOpenHelper db;
    String pointsEarned, pointsReedemed;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        db = new SQLiteOpenHelper(MainActivity.this);


    }


    void initView() {
        edDate = findViewById(R.id.edDate);
        edPointEarned = findViewById(R.id.edEarned);
        edPointsReedemed = findViewById(R.id.edRedeemed);
        btnSave = findViewById(R.id.btnSave);
        edDate.setText(getCurrentDate());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//saving data
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsEarned = edPointEarned.getText().toString();
                pointsReedemed = edPointsReedemed.getText().toString();
                String curDate = edDate.getText().toString();
                if (pointsEarned.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please add points earned", Toast.LENGTH_LONG).show();
                } else if (pointsReedemed.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please add points redeemed", Toast.LENGTH_LONG).show();
                } else {

                    // Inserting data
                    Log.d("Insert: ", "Inserting ..");

                    db.addData(new DataModel(curDate, pointsEarned, pointsReedemed));
                    // Reading all data
                    Log.d("Reading: ", "Reading all data..");
                    List<DataModel> contacts = db.getAllData();
                    edPointEarned.setText("");
                    edPointsReedemed.setText("");
                    Log.i("data", contacts.toString());
                    Toast.makeText(MainActivity.this, "Data Saved!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());


        String formattedDate = sdf.format(c.getTime());

        return formattedDate;
    }

//Update user selected date
    private void updateLabel() {
        edDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.details:
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}
