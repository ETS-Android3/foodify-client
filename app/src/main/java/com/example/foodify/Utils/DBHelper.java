package com.example.foodify.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodify.Adapter.FoodAdapter;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "CartData.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table CartDetails(itemId INTEGER UNIQUE ,quantity INTEGER,id INTEGER primary key autoincrement not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists CartDetails");

    }
    public Boolean insertData(int id,int quantity)
    {
            long result=-1;
            SQLiteDatabase DB=this.getWritableDatabase();
            Cursor cursor=DB.rawQuery("select * from CartDetails where itemId=?",new String[]{Integer.toString(id)});


            ContentValues contentValues = new ContentValues();
            contentValues.put("itemId", id);
            contentValues.put("quantity", quantity);
        if(cursor.getCount()==0) {
             result = DB.insert("CartDetails", null, contentValues);
        }
        else
        {
            DB.update("CartDetails",contentValues,"itemId=?",new String[]{Integer.toString(id)});
        }
        if(result==-1)
            return false;
        else
            return true;
    }
    public void deleteCart()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        DB.rawQuery("delete from CartDetails",null);
    }
    public Cursor getData()
    {
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from CartDetails",null);
        return cursor;
    }
}
