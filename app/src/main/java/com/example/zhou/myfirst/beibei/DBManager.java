package com.example.zhou.myfirst.beibei;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper=new DBHelper(context);
        db=helper.getWritableDatabase();
    }

    //添加单词
    public void add(List<Word> words){
        db.beginTransaction();
        try{
            for (Word word : words){
                db.execSQL("INSERT INTO word VALUES(null,?,?,?)",new Object[]{word.en_meaning,word.cn_meaning,word.repeat_number});
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
    }

    //更新记忆次数
    public void updateRepeat_Number(String English,int OldRepeatingNumber){
        ContentValues cv = new ContentValues();
        int NewRepeatingNumber = OldRepeatingNumber+1;
        cv.put("repeat_number",NewRepeatingNumber);
        db.update("word", cv, "en_meaning=?", new String[]{English});
    }

    //删除烂熟的单词
    public void deleteAdroidWord(Word word) {
        db.delete("word", "repeat_number >=?", new String[]{String.valueOf(word.repeat_number)});
    }

    //查询所有单词,返回一个完整的单词表
    public List<Word> query(){
        ArrayList<Word> words = new ArrayList<Word>();
        Cursor csr = queryTheCursor();
        while (csr.moveToNext()){
            Word word = new Word();
            word._id = csr.getInt(csr.getColumnIndex("_id"));
            word.en_meaning = csr.getString(csr.getColumnIndex("en_meaning"));
            word.cn_meaning = csr.getString(csr.getColumnIndex("cn_meaning"));
            word.repeat_number = csr.getInt(csr.getColumnIndex("repeat_number"));
        }
        return words;
    }
    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT*FROM word", null);
        return c;
    }
    //关闭数据库
    public void closeDB(){
        db.close();
    }
}
