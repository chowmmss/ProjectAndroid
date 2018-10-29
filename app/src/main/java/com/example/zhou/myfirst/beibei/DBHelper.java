package com.example.zhou.myfirst.beibei;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "word_db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库属性：id，English，meaning，repeat_number
        db.execSQL("CREATE TABLE IF NOT EXISTS word"+
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,en_meaning VARCHAR,cn_meaning VARCHAR,repeat_number INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("ALTER TABLE word ADD COLUMN other STRING");
    }
}
