package com.example.babydevelopmenttrackingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DatabaseH extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "babyDevTracking.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "baby_Details";
    private static final String COLUMN_ID = "baby_id";
    private static final String COLUMN_FNAME = "first_name";
    private static final String COLUMN_LNAME = "last_name";
    private static final String COLUMN_BIRTHDATE = "birth_date";

    private static final String COLUMN_WEIGHT = "current_weight";
    private static final String COLUMN_HEIGHT = "current_height";

    private static final String LASTSAVEDID = "last_saved_id";

    public DatabaseH(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FNAME + " TEXT, " +
                COLUMN_LNAME + " TEXT, " +
                COLUMN_BIRTHDATE + " TEXT, " +
                COLUMN_WEIGHT + " INTEGER, " +
                COLUMN_HEIGHT + " INTEGER);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + LASTSAVEDID +
                " (" + COLUMN_ID + " INTEGER);";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LASTSAVEDID);
        onCreate(db);
    }

    void addBaby(String fname, String lname, String bdate, int weight, int height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FNAME, fname);
        cv.put(COLUMN_LNAME, lname);
        cv.put(COLUMN_BIRTHDATE, bdate);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_HEIGHT, height);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully !!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAccounts(){
        String query = "SELECT " + COLUMN_FNAME + ", " + COLUMN_LNAME + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    int readLastSavedID(){
        String query = "SELECT * FROM " + LASTSAVEDID;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.getCount() == 0){
                return -1;
            } else {
                return cursor.getInt(0);
            }
        }
        return -2;
    }

    void updateLastSavedID(int id){
        int last_saved_id = readLastSavedID();
        String query = null;

        if (last_saved_id > 0){
            query = "UPDATE " + LASTSAVEDID + " SET " + COLUMN_ID + " = " + id + " WHERE " + COLUMN_ID + " = " + readLastSavedID();
        }else if(last_saved_id == -1){
            query = "INSERT INTO " + LASTSAVEDID + " VALUES (" + id + ");";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(query, null);

    }

    int findID(String Fname, String Lname){
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_FNAME + " = " + Fname + " && " + COLUMN_LNAME + " = " + Lname;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.getCount() == 0){
                return -1;
            } else {
                return cursor.getInt(0);
            }
        }
        return -2;
    }

    //testing
    void testing(){
        System.out.println("check");
    };

}
