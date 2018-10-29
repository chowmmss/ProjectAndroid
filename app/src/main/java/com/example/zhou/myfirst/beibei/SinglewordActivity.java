package com.example.zhou.myfirst.beibei;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhou.myfirst.ExchangeActivity;
import com.example.zhou.myfirst.R;

public class SinglewordActivity extends Activity{

    private Button btnFront;
    private Button btnNext;
    private TextView EnText;
    private TextView CnText;
    private TextView CountText;
    private String []English=new String[50];
    private String []Chinese=new String[50];
    private int []repeat_num = new int[50];
    private int today_start_place;//开始背的位置
    private int today_plan;//要背的单词数目
    private int i;
    public int k;//当前单词的进度

    Handler handler = new Handler();
    Runnable nextword = new Runnable() {
        @Override
        public void run() {

            EnText.setText(English[k]);
            CnText.setText(Chinese[k]);
            CountText.setText(""+(k+1));
            btnNext = (Button)findViewById(R.id.nextword);

            //下一个
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    if(i>1){
                        handler.removeCallbacks(nextword);//销毁当前线程
                        handler.postDelayed(nextword, 1);//重新开始
                        i--;
                        Log.i("onClick next","today_plan 2 ...."+i);
                        k++;
                        Log.i("onClick next","k....."+k);
                    }
                    else{
                        handler.removeCallbacks(nextword);
                        Intent intent = new Intent();
                        Log.i("onClick next","today_plan...."+today_plan);
                        intent.putExtra("next_start_place", today_start_place+today_plan);
                        setResult(1001,intent);
                        finish();
                    }
                }
            });

            //上一个
            btnFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(i>=1 && i<today_plan){
                        Log.i("onClick front","k0000");
                        handler.removeCallbacks(nextword);
                        handler.postDelayed(nextword, 1);
                        k--;
                        i++;
                    }
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleword);

        //保持屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_singleword);

        //获取传递过来的参数
        Intent intent = getIntent();
        Log.i("SingleonCreate","get  message");
        English = intent.getStringArrayExtra("English");
        Chinese = intent.getStringArrayExtra("Chinese");
        repeat_num = intent.getIntArrayExtra("Repeat_num");
        today_plan = intent.getIntExtra("today_plan", 2);
        Log.i("SingleonCreate","today_plan"+today_plan);
        today_start_place = intent.getIntExtra("today_start_place", 0);

        //用于循环、判断条件
        k = today_start_place;
        i = today_plan;

        btnFront = (Button)findViewById(R.id.frontword);
        EnText = (TextView) findViewById(R.id.words);
        CnText = (TextView)findViewById(R.id.translation);
        CountText = (TextView)findViewById(R.id.countword);

        //开始线程
        handler.post(nextword);
    }
}
