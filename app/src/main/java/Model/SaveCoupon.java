package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveCoupon extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Coupon.db";
    public static final String TABLE_NAME = "Coupon_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "COUPON_ID";
    public static final String COL_3 = "COUPON_NAME";
    public SaveCoupon(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,COUPON_ID TEXT,COUPON_NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String cop_id,String cop_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,cop_id);
        contentValues.put(COL_3,cop_name);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


   /* public boolean updateData(String id,String cop_id,String cop_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,cop_id);
        contentValues.put(COL_3,cop_name);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }*/

    public boolean updateData(String cop_id,String cop_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,cop_id);
        contentValues.put(COL_3,cop_name);
        db.update(TABLE_NAME, contentValues, "COUPON_ID = ?",new String[] { cop_id });
        return true;
    }

    public void deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("delete from "+ TABLE_NAME);
    }
}
