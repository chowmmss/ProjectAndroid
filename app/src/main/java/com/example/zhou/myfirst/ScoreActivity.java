package com.example.zhou.myfirst;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreA,scoreB;
    Button btn,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreA = (TextView)findViewById(R.id.teamAscore);
        scoreB = (TextView)findViewById(R.id.teamBscore);
        btn = (Button)findViewById(R.id.btn);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_4 = (Button)findViewById(R.id.btn_4);
        btn_5 = (Button)findViewById(R.id.btn_5);
        btn_6 = (Button)findViewById(R.id.btn_6);
    }
    public void setBtn_1(View btn_1){
        showScoreA(3);
    }
    public void setBtn_2(View btn_2){
        showScoreA(2);
    }
    public void setBtn_3(View btn_3){
        showScoreA(1);
    }
    public void setBtn_4(View btn_4){
        showScoreB(3);
    }
    public void setBtn_5(View btn_5){
        showScoreB(2);
    }
    public void setBtn_6(View btn_6){
        showScoreB(1);
    }
    public void setBtn(View btn){
        resSet();
    }
    public void showScoreA(int a){
        String oldscoreA = (String)scoreA.getText();
        scoreA.setText(Integer.parseInt(oldscoreA)+a);
    }
    public void showScoreB(int b){
        String oldscoreB = (String)scoreB.getText();
        scoreB.setText(Integer.parseInt(oldscoreB)+b);
    }
    public void resSet(){
        scoreA.setText(0);
        scoreB.setText(0);
    }
}
