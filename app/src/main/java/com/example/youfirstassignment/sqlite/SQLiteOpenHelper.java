package com.example.youfirstassignment.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataManager";
    private static final String TABLE_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_POINTS_EARNED = "points_earned";
    private static final String KEY_POINTS_REDEEMED = "points_redeemed";


    public SQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " TEXT,"
                + KEY_POINTS_EARNED + " INTEGER,"
                + KEY_POINTS_REDEEMED + " INTEGER" + ")";
        db.execSQL(CREATE_DATA_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        // Create tables again
        onCreate(db);

    }

    // code to add the new data
    public void addData(DataModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, data.getDate());
        values.put(KEY_POINTS_EARNED, data.getPointsEarned());
        values.put(KEY_POINTS_REDEEMED, data.getPointsReedemed());



        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single data
    DataModel getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID,
                        KEY_POINTS_EARNED, KEY_POINTS_REDEEMED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DataModel data = new DataModel(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        return data;
    }

    // code to get all data in a list view
    public List<DataModel> getAllData() {
        List<DataModel> dataModelList = new ArrayList<DataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataModel data = new DataModel();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setDate(cursor.getString(1));
                data.setPointsEarned(cursor.getString(2));
                data.setPointsReedemed(cursor.getString(2));
                // Adding contact to list
                dataModelList.add(data);
            } while (cursor.moveToNext());
        }

        // return data list
        return dataModelList;
    }

    // code to update the single data
    public int updateData(DataModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, data.getDate());
        values.put(KEY_POINTS_EARNED, data.getPointsEarned());
        values.put(KEY_POINTS_REDEEMED, data.getPointsReedemed());

        // updating row
        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getId()) });
    }

    // Deleting single data
    public void deleteData(DataModel data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getId()) });
        db.close();
    }

    // Getting data Count
    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

//Get Query for setting recyclerview
    public Cursor getDashboardQuery(String Query){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(Query,null);
    }


}
