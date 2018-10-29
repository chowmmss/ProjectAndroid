package com.example.zhou.myfirst.beibei;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhou.myfirst.R;

public class SpellwordActivity extends Activity {

    private Button btnFront;
    private Button btnNext;
    private EditText SpellText;
    private TextView CnText;
    private TextView CountText;
    private String []English=new String[50];
    private String []Chinese=new String[50];
    private String spell;
    private int []repeat_num = new int[50];
    private int today_start_place;//开始背的位置
    private int today_plan;//要背的单词数目
    private int i;
    public int k;//当前单词的进度

    Handler handler = new Handler();
    Runnable nextspellword = new Runnable() {
        @Override
        public void run() {

            CnText.setText(Chinese[k]);
            CountText.setText(""+(k+1));
            btnNext = (Button)findViewById(R.id.funcnextword);

            //下一个
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    spell = SpellText.getText().toString();
                    Log.i("seplltext","====="+spell);

                    //不是最后一个单词，且拼写正确
                    if(i>1 && spell.equals(English[k])){
                        Log.i("if","right,nextword");
                        handler.removeCallbacks(nextspellword);//销毁当前线程
                        handler.postDelayed(nextspellword, 1);//重新开始
                        i--;
                        Log.i("spellonClick next","i ...."+i);
                        k++;
                        Log.i("spellonClick next","k....."+k);
                    }
                    //除了最后一个单词，拼写错误时
                    else if(i>=1 && spell.equals(English[k])==false){
                        Log.i("else if","wrong,thisword");
                        handler.removeCallbacks(nextspellword);
                        handler.postDelayed(nextspellword, 1);
                    }
                    //最后一个单词拼错了
//                    else if(i<=1 && spell.equals(English[k])==false){
//                        Log.i("else if","wrong,thisword");
//                        handler.removeCallbacks(nextspellword);
//                        handler.postDelayed(nextspellword, 1);
//                        i++;
//                    }
                    //最后一个单词，且拼写正确
                    else if(i<=1 && spell.equals(English[k])){
                        Log.i("else","right,back");
                        handler.removeCallbacks(nextspellword);
                        Intent intent = new Intent();
                        Log.i("spellonClick next","today_plan...."+today_plan);
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
                        handler.removeCallbacks(nextspellword);
                        handler.postDelayed(nextspellword, 1);
                        k--;
                        i++;
                    }
                }
            });

            ((TextView)SpellText).setText("");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spellword);

        //保持屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_spellword);

        //获取传递过来的参数
        Intent intent = getIntent();
        Log.i("SpellonCreate","get  message");
        English = intent.getStringArrayExtra("English");
        Chinese = intent.getStringArrayExtra("Chinese");
        repeat_num = intent.getIntArrayExtra("Repeat_num");
        today_plan = intent.getIntExtra("today_plan", 2);
        Log.i("SpellonCreate","today_plan "+today_plan);
        today_start_place = intent.getIntExtra("today_start_place", 0);
        Log.i("SpellonCreate","today_start_place "+ today_start_place);
        for(int j=today_start_place;j<16;j++){
            Log.i("SpellonCreate","english"+English[j]);
        }

        //用于循环、判断条件
        k = today_start_place;
        i = today_plan;

        btnFront = (Button)findViewById(R.id.funcfrontword);
        CnText = (TextView)findViewById(R.id.functranslation);
        CountText = (TextView)findViewById(R.id.funccountword);
        SpellText = (EditText)findViewById(R.id.funcspell);

        //开始线程
        handler.post(nextspellword);

    }
}
