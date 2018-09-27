package com.example.zhou.myfirst;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView scoreA,scoreB;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreA = (TextView)findViewById(R.id.teamAscore);
        scoreB = (TextView)findViewById(R.id.teamBscore);
    }
    public void setBtn_1(View btn){
        showScoreA(3);
    }
    public void setBtn_2(View btn){
        showScoreA(2);
    }
    public void setBtn_3(View btn){
        showScoreA(1);
    }
    public void setBtn_4(View btn){
        showScoreB(3);
    }
    public void setBtn_5(View btn){
        showScoreB(2);
    }
    public void setBtn_6(View btn){
        showScoreB(1);
    }
    public void setBtn(View btn){
        resSet();
    }
    public void showScoreA(int a){
        String oldscoreA = (String)scoreA.getText();
        int scorea = Integer.parseInt(oldscoreA)+a;
        scoreA.setText(String.valueOf(scorea));
    }
    public void showScoreB(int b){
        Log.i("kkkkk","aaaaa");
        String oldscoreB = (String)scoreB.getText();
        int scoreb = Integer.parseInt(oldscoreB)+b;
        scoreB.setText(String.valueOf(scoreb));
    }
    public void resSet(){
        scoreA.setText(String.valueOf(0));
        scoreB.setText(String.valueOf(0));
    }
}
