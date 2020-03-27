package com.jaayu.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReminderDataFromServerDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ReminderData.db";
    public static final String TABLE_NAME = "reminder_table";
    public static final String TABLE_TIME_NAME = "time_table";
    public static final String COL_1 = "medicine_name";
    public static final String COL_2 = "user_id";
    public static final String COL_3 = "repeat_ed";
    public static final String COL_4 = "sun";
    public static final String COL_5 = "mon";
    public static final String COL_6 = "tue";
    public static final String COL_7 = "wed";
    public static final String COL_8 = "thu";
    public static final String COL_9 = "fri";
    public static final String COL_10 = "sat";
    public static final String COL_11 = "month_date";
    public static final String COL_12 = "start_date";
    public static final String COL_13 = "end_date";
    public static final String COL_14 = "duration";
    public static final String COL_TIME = "set_time";



    public ReminderDataFromServerDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,medicine_name TEXT," +
                "user_id TEXT,repeat_ed TEXT,sun TEXT,mon TEXT,tue TEXT,wed TEXT,thu TEXT,fri TEXT,sat TEXT,month_date TEXT," +
                "start_date TEXT,end_date TEXT,duration TEXT)");
        sqLiteDatabase.execSQL("create table " + TABLE_TIME_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,set_time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TIME_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String medicine_name,String user_id,String repeat,String sunday,String monday,String tuesday,String wednesday,
                              String thirseday,String friday,String saturday,String monthdate,String startdate,String enddate,String due) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,medicine_name);
        contentValues.put(COL_2,user_id);
        contentValues.put(COL_3,repeat);
        contentValues.put(COL_4,sunday);
        contentValues.put(COL_5,monday);
        contentValues.put(COL_6,tuesday);
        contentValues.put(COL_7,wednesday);
        contentValues.put(COL_8,thirseday);
        contentValues.put(COL_9,friday);
        contentValues.put(COL_10,saturday);
        contentValues.put(COL_11,monthdate);
        contentValues.put(COL_12,startdate);
        contentValues.put(COL_13,enddate);
        contentValues.put(COL_14,due);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertTime(String setTheTime) {
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL_TIME,setTheTime);

        long result2 = db2.insert(TABLE_TIME_NAME,null ,contentValues2);
        if(result2 == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public Cursor getAllTME() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_TIME_NAME,null);
        return res;
    }
}
