package com.example.zhou.myfirst;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeActivity extends Activity implements Runnable{

    private float usdRate;
    private float jpyRate;
    private float eupRate;
    EditText money;
    TextView answer;
    String mon;
    float ans = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        money =  (EditText)findViewById(R.id.money);
        answer = (TextView)findViewById(R.id.answer);
        Button eup = (Button)findViewById(R.id.toEUP);
        Button usd = (Button)findViewById(R.id.toUSD);
        Button jpy = (Button)findViewById(R.id.toJPY);
        Button config = (Button)findViewById(R.id.opencfg);

        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);

        usdRate = sharedPreferences.getFloat("usd_rate",0.0f);
        jpyRate = sharedPreferences.getFloat("jpy_rate",0.0f);
        eupRate = sharedPreferences.getFloat("eup_rate",0.0f);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str = (String) msg.obj;
                    answer.setText(str);
                }
                super.handleMessage(msg);
            }
        };

    }
    public void tousd(View view){
        mon = money.getText().toString();
        if(mon.length()>0){
            ans = Float.parseFloat(mon);
            answer.setText(String.valueOf(ans*usdRate));
        }
        else{
            Toast.makeText(this,"ERROR!!!!",Toast.LENGTH_SHORT).show();
        }
    }
    public void tojpy(View view){
        mon = money.getText().toString();
        if(mon.length()>0){
            ans = Float.parseFloat(mon);
            answer.setText(String.valueOf(ans*jpyRate));
        }
        else{
            Toast.makeText(this,"ERROR!!!!",Toast.LENGTH_SHORT).show();
        }
    }
    public void toeup(View view){
        mon = money.getText().toString();
        if(mon.length()>0){
            ans = Float.parseFloat(mon);
            answer.setText(String.valueOf(ans*eupRate));
//            Intent intent = new Intent(this,SavemoneyActivity.class);
//            startActivity(intent);
//            finish();
        }
        else{
            Toast.makeText(this,"ERROR!!!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void openconfig(View view){
        Intent config = new Intent(this,SavemoneyActivity.class);
        config.putExtra("usd_rate_key",usdRate);
        config.putExtra("jpy_rate_key",jpyRate);
        config.putExtra("eup_rate_key",eupRate);
        Log.i("保存数据成功","了吗");
        startActivityForResult(config,1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            Log.i("改了汇率","了吗");
            usdRate = bundle.getFloat("key_usd",0.1f);
            jpyRate = bundle.getFloat("key_jpy",0.1f);
            eupRate = bundle.getFloat("key_eup",0.1f);

            SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("usd_rate",usdRate);
            editor.putFloat("jpy_rate",jpyRate);
            editor.putFloat("eup_rate",eupRate);
            editor.commit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void run(){
        for(int i = 1;i<3;i++){
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
//        获取信息
        Message msg = handler.obtainMessage(5);
        msg.obj="Hello from run()";
        handler.sendMessage(msg);
        URL url = null;
        try{
            url = new URL("www.usd-cny.com/icbc.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private String inputStream2String(InputStream inputStream)throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"utf-8");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}