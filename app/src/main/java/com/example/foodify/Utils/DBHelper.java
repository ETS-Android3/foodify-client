package com.example.foodify.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "CartData.db", null, 1);
    }
    private String tableName = "cart";

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("drop table if exists cart");
        DB.execSQL("create table cart (itemId INTEGER UNIQUE ,quantity INTEGER,id INTEGER primary key autoincrement not null, image varchar, name varchar, description varchar, price integer,calories integer)");
        DB.execSQL("drop table if exists userDetails");
        DB.execSQL("create table userDetails(id INTEGER primary key autoincrement not null,accesstoken varchar,userId INTEGER UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists cart");

        DB.execSQL("create table cart (itemId INTEGER UNIQUE ,quantity INTEGER,id INTEGER primary key autoincrement not null, image varchar, name varchar, description varchar, price integer,calories integer)");
        DB.execSQL("drop table if exists userDetails");
        DB.execSQL("create table userDetails(id INTEGER primary key autoincrement not null,accesstoken varchar,userId INTEGER UNIQUE)");
    }


    public void insertData(int id, int quantity, String image, String name, String description, int price,int calories) {
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("itemId", id);
        contentValues.put("quantity", quantity);
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("price", price);
        contentValues.put("image", image);
        contentValues.put("calories", calories);
        long result = DB.insert("cart", null, contentValues);
    }

    public void updateItem(int itemId, int quantity,int price,int calories){
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemId", itemId);
        contentValues.put("quantity",quantity);
        contentValues.put("price",price);
        contentValues.put("calories",calories);
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.update("cart", contentValues, "itemId=?", new String[]{Integer.toString(itemId)});
    }

    public Boolean getExistingItem(int itemId){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from cart where itemId=?", new String[]{Integer.toString(itemId)});

        if(cursor.getCount() == 0) return false;
        else return true;
    }

    public void deleteCart() {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("cart",null,null);
    }

    public Cursor getCartData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from cart", null);
        return cursor;
    }
    public Cursor getExistingItemData(int itemId){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from cart where itemId=?", new String[]{Integer.toString(itemId)});
       return cursor;
    }


    private void createCartTable(SQLiteDatabase DB) {
        DB.execSQL("create table cart(itemId INTEGER UNIQUE ,quantity INTEGER,id INTEGER primary key autoincrement not null, image varchar, name varchar, description varchar, price integer)");
    }
    private void createUserDetail(SQLiteDatabase DB)
    {
        DB.execSQL("create table userDetails(id INTEGER primary key autoincrement not null,accesstoken varchar,userId INTEGER UNIQUE)");
    }
    public void insertDetails(int id,String accesstoken,int userId)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("accesstoken",accesstoken);
        contentValues.put("userId",userId);
        DB.insert("userDetails",null,contentValues);
    }
    public Cursor getUserData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from userDetails", null);
        return cursor;
    }
    public void deleteUser()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("userDetails",null,null);
    }
    public void getQuantity(int id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor items=DB.rawQuery("select * from cart where itemId=?",new String[]{Integer.toString(id)});



    }


}
