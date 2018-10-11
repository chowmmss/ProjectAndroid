package com.example.zhou.myfirst;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends android.app.ListActivity implements Runnable{

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list);
//        List<String> list1 = new ArrayList<String>();
//        for(int i=1;i<100;i++){
//            list1.add("item"+i);
//        }
//        String[] list_data = {"one","two","three","four"};
//        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_data);
//        setListAdapter(adapter);

        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    Log.i("yet","sendmessage");
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(ListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                    Log.i("ttttttt","222222");
                }
                super.handleMessage(msg);
            }
        };
    }

    public void run(){
        Log.i("thread","run,,,,,,");
        List<String> rateList = new ArrayList<String>();
        try{
            Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();

            Elements tbs = doc.getElementsByTag("table");
            Element table = tbs.get(0);
            Elements tds = table.getElementsByTag("td");
//            Elements tbs = doc.getElementsByClass("tableDataTable");
//            Element table = tbs.get(0);
//            Elements tds = table.getElementsByTag("td");
            for(int i = 0;i<tds.size();i+=6){
            Element td = tds.get(i);
            Element td2 = tds.get(i+5);

            String tdStr = td.text();
            String pStr = td2.text();
            rateList.add(tdStr + "==>" + pStr);
            Log.i("td",tdStr+"==>"+pStr);
        }

    }catch (MalformedURLException e){
            Log.i("www",e.toString());
            e.printStackTrace();
        }catch(IOException e){
            Log.i("www",e.toString());
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = rateList;
        handler.sendMessage(msg);
        Log.i("thread","sendmessage");
    }
}
