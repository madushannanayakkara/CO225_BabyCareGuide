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
    private static final String LASTSAVEDID = "last_saved_id";
    private static final String TABLE3 = "vaccines";
    private static final String TABLE4 = "notify";

    private static final String COLUMN_ID = "baby_id";
    private static final String COLUMN_FNAME = "first_name";
    private static final String COLUMN_LNAME = "last_name";
    private static final String COLUMN_BIRTHDATE = "birth_date";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_BIRTHWEIGHT = "birth_weight";
    private static final String COLUMN_WEIGHT = "current_weight";
    private static final String COLUMN_HEIGHT = "current_height";

    //Column names of the table3
    private static final String ID = "id";
    //private static final String AGE = "age";
    private static final String VACCINE = "vaccine";
    //private static final String REMARKS = "remarks";
    private static final String DONE = "done";
    private static final String USERID = "userId";
    private static final String LAST_VCC_NO = "lastVccNo";

    //Column names for table4
    private static final String TIMEPERIOD = "timePeriod";
  

    public DatabaseH(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query1 = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FNAME + " TEXT, " +
                COLUMN_LNAME + " TEXT, " +
                COLUMN_BIRTHDATE + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_WEIGHT + " TEXT, " +

                COLUMN_HEIGHT + " TEXT, " +
                COLUMN_BIRTHWEIGHT + " TEXT, " +

                "first_nameG" + " TEXT, " +
                "last_nameG" + " TEXT, " +
                "birth_dateG" + " TEXT, " +
                "NIC" + " TEXT, " +
                "address" + " TEXT, " +
                "noofchildren" + " INTEGER);";


        String query2 = "CREATE TABLE " + LASTSAVEDID +
                " (" + COLUMN_ID + " INTEGER);";

        //Query to add vaccination data
        String query3 = "CREATE TABLE " + TABLE3 + " ("
                +ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USERID + " Integer references " + TABLE_NAME + "(" + COLUMN_ID + "),"
                +VACCINE + " TEXT, "
                +DONE + " TEXT, "
                +LAST_VCC_NO + " INTEGER);" ;
      
        //Query to add notification settings data
        String query4 = "CREATE TABLE " + TABLE4 +
                " (" + TIMEPERIOD + " TEXT);";
      
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // drop if existing table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LASTSAVEDID);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE3);
        onCreate(db);
    }

    // Add new baby using register form
    void addBaby(String fname, String lname, String bdate, String gender, String weight, String height){
        SQLiteDatabase db = this.getWritableDatabase();                 // get writeable database
        ContentValues cv = new ContentValues();                         // create content values

        cv.put(COLUMN_FNAME, fname);
        cv.put(COLUMN_LNAME, lname);
        cv.put(COLUMN_BIRTHDATE, bdate);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_HEIGHT, height);

        long result = db.insert(TABLE_NAME, null, cv);      // insert data into database at relevant table

        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully !!", Toast.LENGTH_SHORT).show();

            cv.clear();
            cv.put(VACCINE, "");
            cv.put(USERID, findID(fname, lname));
            cv.put(DONE, "");
            cv.put(LAST_VCC_NO, 0);

            result = db.insert(TABLE3, null, cv);

            if (result == -1) {
                db.insert(TABLE3, null, cv);
            }
        }
    }

    public void addVaccineData(String name, String done, int vaccined,int userId, int lastVccNo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(VACCINE, name);
//        contentValues.put(USERID, userId);
        contentValues.put(DONE, done);
        contentValues.put(LAST_VCC_NO, lastVccNo);

        /* Save to the table*/

        long result = db.update(TABLE3, contentValues,  "userId = ?", new String[]{String.valueOf(userId)});

//            long result = db.insert(TABLE3, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully !!", Toast.LENGTH_SHORT).show();
        }
//        if(vaccined == 0) {
//            long result = db.update(TABLE3, contentValues,  "userId = ?", new String[]{String.valueOf(userId)});
//
////            long result = db.insert(TABLE3, null, contentValues);
//
//            if (result == -1) {
//                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "Added successfully !!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else{
//            Toast.makeText(context, "This vaccine is already given", Toast.LENGTH_SHORT).show();
//        }

    }

    public int readLastVaccination(int id){
        String query = "SELECT " + LAST_VCC_NO + " FROM " + TABLE3 + " WHERE " + USERID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            if(cursor.getCount() == 0){
                return -1;
            } else {
                return cursor.getInt(0);
            }
        }
        return -2;
    }

    public void addNotifyData(String timePeriod, int userID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TIMEPERIOD, timePeriod);
        cv.put(USERID, userID);


        long result = db.insert(TABLE4, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully !!", Toast.LENGTH_SHORT).show();
        }


    }


    // updating details according to Details page window

    public Boolean updateuserdata (String first_name, String last_name, String birth_date, String current_weight, String current_height, String birth_weight,
                                   String first_nameG, String last_nameG, String birth_dateG, String nic, String address, int no_of_children){
        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("first_name", first_name);
        contentValues.put("last_name", last_name);
        contentValues.put("birth_date", birth_date);
        contentValues.put("current_weight", current_weight);
        contentValues.put("current_height", current_height);
        contentValues.put("birth_weight", birth_weight);

        contentValues.put("first_nameG", first_nameG);
        contentValues.put("last_nameG", last_nameG);
        contentValues.put("birth_dateG", birth_dateG);
        contentValues.put("NIC", nic);
        contentValues.put("address", address);
        contentValues.put("noofchildren", no_of_children);

        int IDwant = 0;
        if (readLastSavedID() >= 1) {
            IDwant = readLastSavedID();
            // cursor : like selecting the row , loaded to ths
            Cursor cursor = myDB.rawQuery("Select * from baby_Details where baby_id=?", new String[]{String.valueOf(IDwant)});
            // if cursor has some data
            if (cursor.getCount() > 0) {

                long result = myDB.update("baby_Details",  contentValues, "baby_id=?", new String[]{String.valueOf(IDwant)});
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    // get tha latest baby using readLastSavedID

    public Cursor readLastBabyData(){
        int IDwant = 0;
        if (readLastSavedID() >= 1) {
            IDwant = readLastSavedID();
            String query = "SELECT * FROM " + "baby_Details " + "WHERE " + "baby_id" + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();

            // cursor : retain all data in the db table,  selecting the row , loaded to this
            Cursor cursor = null;
            // check if db is not null
            if (db != null) {
                cursor = db.rawQuery(query, new String[]{String.valueOf(IDwant)});
            }

        return cursor;
        }else {
            return null;
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

    String readAccName(int id){
        String query = "SELECT " + COLUMN_FNAME + ", " + COLUMN_LNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            if(cursor.getCount() > 0){
                return cursor.getString(0) + " " + cursor.getString(1);
            }
        }
        return "Account";
    }


    public int readLastSavedID(){
        String query = "SELECT " + COLUMN_ID + " FROM " + LASTSAVEDID;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

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
        SQLiteDatabase db = this.getWritableDatabase();

        if (last_saved_id > 0){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID, id);

            long result = db.update(LASTSAVEDID, cv,  "baby_id = ?", new String[]{String.valueOf(readLastSavedID())});
            if (result == -1){
                Toast.makeText(context, "Error Occurred !!", Toast.LENGTH_SHORT).show();
            }
        }else if (last_saved_id == -1){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID, id);

            long result = db.insert(LASTSAVEDID, null, cv);
            if (result == -1){
                Toast.makeText(context, "Error Occurred !!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public int findID(String Fname, String Lname){
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_FNAME + " = \"" + Fname + "\" AND " + COLUMN_LNAME + " = \"" + Lname + "\"";
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            if(cursor.getCount() == 0){
                return -1;
            } else {
                return cursor.getInt(0);
            }
        }
        return -2;
    }


    public String getDate(int ID){
        String query = "SELECT " + COLUMN_BIRTHDATE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + ID ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor.getString(0);

    }

    void deleteRow(int id, int replacedid){
        SQLiteDatabase db = this.getWritableDatabase();

        if(replacedid > 0){
            long result = db.delete(LASTSAVEDID,  "baby_id = ?", new String[]{String.valueOf(id)});
            if(result != -1){
                result = db.delete(TABLE_NAME,  "baby_id = ?", new String[]{String.valueOf(id)});
                if(result != -1){
                    Toast.makeText(context, "Successfully Deleteed !!", Toast.LENGTH_SHORT);
                    updateLastSavedID(replacedid);
                } else {
                    Toast.makeText(context, "Faild to Delete !!", Toast.LENGTH_SHORT);
                    updateLastSavedID(id);
                }
            } else {
                Toast.makeText(context, "Faild to Delete !!", Toast.LENGTH_SHORT);
            }
        } else {
            if (replacedid == -1) {
                long result = db.delete(TABLE_NAME,  "baby_id = ?", new String[]{String.valueOf(id)});
                if(result == -1){
                    Toast.makeText(context, "Faild to Delete !!", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, "Successfully Deleteed !!", Toast.LENGTH_SHORT);
                }
            } else {
                String query1 = "DELETE FROM " + LASTSAVEDID;
                String query2 = "DELETE FROM sqlite_sequence WHERE name='" + LASTSAVEDID + "'";

                db.execSQL(query1);
                db.execSQL(query2);

                String query3 = "DELETE FROM " + TABLE_NAME;
                String query4 = "DELETE FROM sqlite_sequence WHERE name='" + TABLE_NAME + "'";

                db.execSQL(query3);
                db.execSQL(query4);

                Toast.makeText(context, "Successfully Deleteed !!", Toast.LENGTH_SHORT);
            }
        }

    }

}
