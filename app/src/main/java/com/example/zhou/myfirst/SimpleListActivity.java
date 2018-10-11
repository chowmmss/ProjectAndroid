package com.example.zhou.myfirst;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleListActivity extends ListActivity implements Runnable{

    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    private int msgwhat = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_simple_list);
//        initListView();
//        this.setListAdapter(listItemAdapter);
        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==msgwhat){
                    List<HashMap<String,String>> retList = (List<HashMap<String, String>>)msg.obj;
                    SimpleAdapter adapter = new SimpleAdapter(SimpleListActivity.this,retList,
                            R.layout.onelist,
                            new String[] {"ItemTitle","ItemDetail"},
                            new int[] {R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }

        };
    }
//    private void initListView(){
//        listItems = new ArrayList<HashMap<String, String>>();
//        for(int i = 0;i<10;i++){
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("ItemTitle","Rate:"+i);
//            map.put("ItemDetail","detail:"+i);
//            listItems.add(map);
//        }
//        listItemAdapter = new SimpleAdapter(SimpleListActivity.this,listItems,
//                R.layout.onelist,
//                new String[]{"ItemTitle","ItemDetail"},
//                new int[]{R.id.itemTitle,R.id.itemDetail});
//    }
    public void run(){
        Log.i("thread","run,,,,,,");
        boolean marker = false;
        List<HashMap<String,String>> rateList = new ArrayList<HashMap<String, String>>();

        try{
            Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Elements tbs = doc.getElementsByTag("table");
            Element table = tbs.get(0);
            Elements tds = table.getElementsByTag("td");
            for(int i = 0;i<tds.size();i+=6){
                Element td = tds.get(i);
                Element td2 = tds.get(i+5);
                String tdStr = td.text();
                String pStr = td2.text();

                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",tdStr);
                map.put("ItemDetail",pStr);
                rateList.add(map);
                Log.i("tdsssss",tdStr+"====="+pStr);
            }
            marker = true;

        }catch (MalformedURLException e){
            Log.i("www",e.toString());
            e.printStackTrace();
        }catch(IOException e){
            Log.i("www",e.toString());
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage();
        msg.what = msgwhat;
        if(marker){
            msg.arg1 = 1;
        }
        else{
            msg.arg1 = 0 ;
        }
        msg.obj = rateList;
        handler.sendMessage(msg);

        Log.i("send","message");
    }

}
