package com.example.zhou.myfirst.beibei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.zhou.myfirst.ListActivity;
import com.example.zhou.myfirst.ListclickActivity;
import com.example.zhou.myfirst.R;

import java.io.InputStream;
import java.util.ArrayList;

public class MyBeibeiActivity extends Activity {

    private DBManager mgr;
    public String res;
    private int today_start_place;
    private int today_plan;
    private int end_update_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beibei);
        mgr = new DBManager(this);

        Intent intent = getIntent();
        String tmp = intent.getStringExtra("today_plan");
//        Log.i("getTodayplan","get study plan");
        today_plan = Integer.parseInt(tmp);

        //获取这次开始的位置
        SharedPreferences sharedPreferences = getSharedPreferences("mystart",Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        today_start_place = sharedPreferences.getInt("today_start_place",0);
        Log.i("onCreate","today_start_place: "+today_start_place);

        //全体查询,返回显示整个单词表
        Cursor c = mgr.queryTheCursor();
        startManagingCursor(c);
        CursorWrapper cursorWrapper = new CursorWrapper(c) {
            @Override
            public String getString(int columnIndex) {

                //在英文后加上中文
                if (getColumnName(columnIndex).equals("en_meaning")) {
                    String cn_meaning = getString(getColumnIndex("cn_meaning"));
                    return  super.getString(columnIndex)+"   "+cn_meaning;
                }
                return super.getString(columnIndex);
            }
        };

        //绑定语句,每个list显示两行:第一行是单词+释义,第二行是记忆次数.
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursorWrapper, new String[]{"en_meaning", "repeat_number"}, new int[]{android.R.id.text1, android.R.id.text2});
        ListView listView = (ListView) findViewById(R.id.wordlist);
        listView.setAdapter(adapter);

    }

    //添加单词
    public void add(View view) {
        ArrayList<Word> words = new ArrayList<Word>();
        String fileName = "word.txt"; //单词表名字  ,文件要放在asset文件夹下

        try{
            InputStream in = getResources().getAssets().open(fileName);
            //利用IO流得到一个res的String串,res里面的内容是整个单词表txt里面的内容
            int length = in.available();
            byte [] buffer = new byte[length];
            in.read(buffer);
            in.close();
            res = new String(buffer, "GBK");
            Log.i("mybeibei.add()","get filestream");
        }catch(Exception e){
            e.printStackTrace();
        }

        //利用String类里面的split方法将res这个大字符串分割成一个String数组,其中的一行就是一个单词
        String []tmp=res.split("\n");
        Log.i("tag","split......");
        for(int i=0;i<tmp.length;i++){
            //进行二次切割,分离出英语单词和中文释义
            Log.i("tag222222",tmp[i]);
            String []tmp_word=tmp[i].split("\t");
            Word word = new Word(tmp_word[0],tmp_word[1],0);
            words.add(word);
        }
        //存入数据库;
        mgr.add(words);
        Log.i("add","add to db");
    }

    //删除烂熟的单词
    public void delete(View view) {
        Word word = new Word();
        word.repeat_number = 1;
        mgr.deleteAdroidWord(word);

        today_start_place = 0;
        //保存下次开始背单词的位置
        SharedPreferences sharedPreferences = getSharedPreferences("mystart",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("today_start_place",end_update_place);
        editor.commit();
    }

    //开始背单词，记忆方式
    public void beibei(View view)
    {
        Cursor c = mgr.queryTheCursor();
        startManagingCursor(c);

        //获取单词的所有信息
        int k = 0;
        int []_id = new int[50];
        String []English = new String[50];
        String []Chinese = new String[50];
        int []RepeatedNum = new int[50];
        while(c.moveToNext()){
            _id[k] = c.getInt(c.getColumnIndex("_id"));
            English[k] = c.getString(c.getColumnIndex("en_meaning"));
            Chinese[k] = c.getString(c.getColumnIndex("cn_meaning"));
            RepeatedNum[k] = c.getInt(c.getColumnIndex("repeat_number"));
            k++;
        }

        //参数传递，单词、解释、次数、计划、开始位置
        Intent intent=new Intent(MyBeibeiActivity.this,SinglewordActivity.class);
        Bundle bundle=new Bundle();

        bundle.putIntArray("_id", _id);
        bundle.putStringArray("English", English);
        bundle.putStringArray("Chinese", Chinese);
        bundle.putIntArray("Repeat_num",RepeatedNum);
        Log.i("beibei","today_start_place==="+today_start_place);
        bundle.putInt("today_start_place", today_start_place);
        bundle.putInt("today_plan", today_plan);
        Log.i("beibei","send message .....");

        intent.putExtras(bundle);
        MyBeibeiActivity.this.startActivityForResult(intent, 1000);
    }


    //开始背单词，拼写方式
    public void spellbeibei(View view)
    {
        Cursor c = mgr.queryTheCursor();
        startManagingCursor(c);

        //获取单词的所有信息
        int k = 0;
        int []_id = new int[50];
        String []English = new String[50];
        String []Chinese = new String[50];
        int []RepeatedNum = new int[50];
        while(c.moveToNext()){
            _id[k] = c.getInt(c.getColumnIndex("_id"));
            English[k] = c.getString(c.getColumnIndex("en_meaning"));
            Chinese[k] = c.getString(c.getColumnIndex("cn_meaning"));
            RepeatedNum[k] = c.getInt(c.getColumnIndex("repeat_number"));
            k++;
        }

        //参数传递，单词、解释、次数、计划、开始位置
        Intent intent=new Intent(MyBeibeiActivity.this,SpellwordActivity.class);
        Bundle bundle=new Bundle();

        bundle.putIntArray("_id", _id);
        bundle.putStringArray("English", English);
        bundle.putStringArray("Chinese", Chinese);
        bundle.putIntArray("Repeat_num",RepeatedNum);
        Log.i("spellbeibei","today_start_place==="+today_start_place);
        bundle.putInt("today_start_place", today_start_place);
        bundle.putInt("today_plan", today_plan);
        Log.i("spellbeibei","send message .....");

        intent.putExtras(bundle);
        MyBeibeiActivity.this.startActivityForResult(intent, 1000);
    }

    //一次学习任务完成后，更新学习进度
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        if(requestCode==1000 && resultCode==1001) {

            Cursor c = mgr.queryTheCursor();
            startManagingCursor(c);

            //获取更新需要的参数--开始更新的位置、待更新的单词数目
            end_update_place = data.getIntExtra("next_start_place", today_plan+today_start_place);
            Log.i("onActivityResult","next_start_place=="+end_update_place);
            int start_update_place = today_start_place;
            int total_update = today_plan;
            Log.i("onActivityResult","start_update_place=="+start_update_place);

            //开始更新
            c.moveToPosition(start_update_place-1);
            int i = 1;
            while(c.moveToNext() && i<=today_plan){
                String EnglishStr = c.getString(c.getColumnIndex("en_meaning"));
                int RepeatNum = c.getInt(c.getColumnIndex("repeat_number"));
                mgr.updateRepeat_Number(EnglishStr,RepeatNum);
                i++;
            }
            //设置背完一轮就返回第一个
            if (end_update_place==18){
                end_update_place = 0;
            }

            //保存下次开始背单词的位置
            SharedPreferences sharedPreferences = getSharedPreferences("mystart",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("today_start_place",end_update_place);
            editor.commit();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
