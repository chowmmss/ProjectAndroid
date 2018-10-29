package com.example.zhou.myfirst.beibei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhou.myfirst.R;

import java.io.InputStream;
import java.util.ArrayList;

public class HomeBeibei extends android.support.v4.app.Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.homefram,container,false);
    }

    public String res;
    private EditText todayPlan;
    private Button startButton;

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        todayPlan = (EditText)getView().findViewById(R.id.todayplan);
        todayPlan.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        startButton = (Button) getActivity().findViewById(R.id.start);

        //开始，获取今日计划
        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if((todayPlan.getText().toString()).equals("")){
                    Toast.makeText(getActivity(),"Input your plan",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i("onclick","have click");
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),MyBeibeiActivity.class);
                    intent.putExtra("today_plan",todayPlan.getText().toString() );
                    Log.i("onClick","send study plan");
                    startActivity(intent);
                }
            }
        });
    }
}