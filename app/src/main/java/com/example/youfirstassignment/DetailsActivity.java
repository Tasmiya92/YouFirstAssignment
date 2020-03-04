package com.example.youfirstassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youfirstassignment.sqlite.DataModel;
import com.example.youfirstassignment.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    private Button btnShowDetails;
    SQLiteOpenHelper db;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    EditText edStartDate, edEndDate;
    RecyclerView rcDetails;
    List<DataModel> mDataModelList = new ArrayList<>();
    RecyclerAdapter mRecyclerAdapter;
    String startDates = "";
    String endDates = "";
    private LinearLayout ll_header;
    private TextView textTotalEarned, textTotalRedeemed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        db = new SQLiteOpenHelper(DetailsActivity.this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    void initView() {
        edStartDate = findViewById(R.id.edStartDate);
        edEndDate = findViewById(R.id.edEndDate);
        edEndDate = findViewById(R.id.edEndDate);
        btnShowDetails = findViewById(R.id.btnShowDetails);
        rcDetails = findViewById(R.id.rcvDetails);
        ll_header = findViewById(R.id.ll_header);
        ll_header.setVisibility(View.GONE);
        textTotalEarned = findViewById(R.id.textTotalEarned);
        textTotalRedeemed = findViewById(R.id.textTotalRedeemed);

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

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        edStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DetailsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DetailsActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDates = edStartDate.getText().toString();
                endDates = edEndDate.getText().toString();
                if (startDates.isEmpty()) {
                    Toast.makeText(DetailsActivity.this, "Please select Star date", Toast.LENGTH_LONG).show();

                } else if (endDates.isEmpty()) {
                    Toast.makeText(DetailsActivity.this, "Please select End date", Toast.LENGTH_LONG).show();
                } else {
                    setDetailss();
                }
            }
        });
    }

//Getting Dashboard data
    public Cursor getDashboardCount(String Query) {
        try {
            return db.getDashboardQuery(Query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   //For setting details in RecyclerView
    void setDetailss() {
        String tempUnixTime = "";
        String pE = "";
        String pD = "";
        ll_header.setVisibility(View.VISIBLE);
        mDataModelList.clear();


        try {
            String query = "select * from data where Date BETWEEN '" + startDates + "' AND '" + endDates + "' ORDER BY Date ASC";
            Cursor c = getDashboardCount(query);
            int totalEarned = 0, totalRedeemed = 0;
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        tempUnixTime = c.getString(c.getColumnIndex("date"));
                        pE = c.getString(c.getColumnIndex("points_earned"));
                        pD = c.getString(c.getColumnIndex("points_redeemed"));
                        DataModel dataModel = new DataModel();
                        dataModel.setDate(tempUnixTime);
                        dataModel.setPointsEarned(pE);
                        dataModel.setPointsReedemed(pD);
                        mDataModelList.add(dataModel);



                        if(pE.equals("")){
                            pE = "0";
                        }
                        if(pD.equals("")){
                            pD = "0";
                        }
                            totalEarned = totalEarned + Integer.parseInt(pE);
                            textTotalEarned.setText(String.valueOf(totalEarned));
                            totalRedeemed = totalRedeemed + Integer.parseInt(pD);
                            textTotalRedeemed.setText(String.valueOf(totalRedeemed));
                        rcDetails.setLayoutManager(new GridLayoutManager(DetailsActivity.this, 1));
                        mRecyclerAdapter = new RecyclerAdapter(DetailsActivity.this, mDataModelList);
                        rcDetails.setAdapter(mRecyclerAdapter);
                    } while (c.moveToNext());
                    c.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//Updating user picked date
    private void updateLabel() {
        edStartDate.setText(sdf.format(myCalendar.getTime()));
    }
    //Updating user picked date
    private void updateLabel1() {
        edEndDate.setText(sdf.format(myCalendar.getTime()));
    }

}
