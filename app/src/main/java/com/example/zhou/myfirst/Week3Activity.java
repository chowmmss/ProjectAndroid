package com.example.zhou.myfirst;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Week3Activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week3);
        Button btn = (Button)findViewById(R.id.compute);

    }
    public void compute(View view){
        DecimalFormat res = new DecimalFormat("0.00");
        EditText height = (EditText)findViewById(R.id.height);
        EditText weight = (EditText)findViewById(R.id.weight);
        double height2 = Double.parseDouble(height.getText().toString());
        double weight2 = Double.parseDouble(weight.getText().toString());
        if(height2>2.5||height2<=0){
            Toast.makeText(this,"Something Wrong With Your Height",Toast.LENGTH_SHORT).show();
        }
        else if(weight2<=0||weight2>200){
            Toast.makeText(this,"Something Wrong With Your Weight",Toast.LENGTH_SHORT).show();
        }
        else{
            double BMI = weight2/(height2*height2);
            TextView result = (TextView)findViewById(R.id.result);
            result.setText("Your BMI is"+ res.format(BMI));
        }
    }
}
