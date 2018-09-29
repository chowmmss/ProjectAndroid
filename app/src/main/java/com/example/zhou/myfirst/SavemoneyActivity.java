package com.example.zhou.myfirst;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SavemoneyActivity extends Activity {

    EditText usd_Rate;
    EditText jpy_Rate;
    EditText eup_Rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savemoney);
        Intent intent = getIntent();
        Log.i("传过来","lllll");
        float usd2 = intent.getFloatExtra("usd_rate_key",0.0f);
        float jpy2 = intent.getFloatExtra("jpy_rate_key",0.0f);
        float eup2 = intent.getFloatExtra("eup_rate_key",0.0f);

        usd_Rate = (EditText)findViewById(R.id.usd_Rate);
        jpy_Rate = (EditText)findViewById(R.id.jpy_Rate);
        eup_Rate = (EditText)findViewById(R.id.eup_Rate);
//显示数据到控件
        usd_Rate.setText(String.valueOf(usd2));
        jpy_Rate.setText(String.valueOf(jpy2));
        eup_Rate.setText(String.valueOf(eup2));
    }
    public void save(View view){
        float newUsd = Float.parseFloat(usd_Rate.getText().toString());
        float newJpy = Float.parseFloat(jpy_Rate.getText().toString());
        float newEup = Float.parseFloat(eup_Rate.getText().toString());

        Intent intent = getIntent();
        Bundle bdl = new Bundle();

        bdl.putFloat("key_usd",newUsd);
        bdl.putFloat("key_jpy",newJpy);
        bdl.putFloat("key_eup",newEup);

        intent.putExtras(bdl);
        setResult(2,intent);
//返回到调用页⾯
        finish();
        Log.i("返回","成功了吗？");
    }
}
