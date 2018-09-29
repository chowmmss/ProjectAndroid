package com.example.zhou.myfirst;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ExchangeActivity extends Activity {
    private float usdRate = 0.1f;
    private float jpyRate = 0.2f;
    private float eupRate = 0.3f;

    EditText money;
    TextView answer;
    String mon;
    float ans = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        money =  (EditText)findViewById(R.id.money);
        answer = (TextView)findViewById(R.id.answer);
        Button eup = (Button)findViewById(R.id.toEUP);
        Button usd = (Button)findViewById(R.id.toUSD);
        Button jpy = (Button)findViewById(R.id.toJPY);
        Button config = (Button)findViewById(R.id.opencfg);
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}