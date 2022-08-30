package com.example.apipizza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DB = "user.db";

    public dbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB, null, 1);
    }

    public dbHelper(@Nullable Context context){
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(userDb.CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public ArrayList<User> getUser(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<User> userList = new ArrayList<>();

        Cursor cursor = db.query(
                userDb.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()){
            User user =new User();

            user.setId(cursor.getInt((int)cursor.getColumnIndex(userDb.ID)));
            user.setUsername(cursor.getString((int)cursor.getColumnIndex(userDb.USERNAME)));
            user.setEmail(cursor.getString((int)cursor.getColumnIndex(userDb.EMAIL)));
            user.setPassword(cursor.getString((int)cursor.getColumnIndex(userDb.PASSWORD)));

            userList.add(user);
        }

        return userList;
    }


    public boolean addUser (User user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(userDb.USERNAME,user.getUsername());
        values.put(userDb.EMAIL,user.getEmail());
        values.put(userDb.PASSWORD,user.getPassword());


        return db.insert(userDb.TABLE_NAME,null,values) > 0;
    }

    public boolean deleteUser(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(userDb.TABLE_NAME,userDb.ID +" = ?",new String[]{String.valueOf(id)}) > 0;
    }



    public static class userDb {
        public static final String TABLE_NAME = "User";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";


        public static final String  CREATE = "CREATE TABLE " +TABLE_NAME+
                "(" +ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " TEXT NOT NULL,"+
                EMAIL+"	INTEGER NOT NULL," +
                PASSWORD  +"	INTEGER NOT NULL DEFAULT 1 )";
    }
}
