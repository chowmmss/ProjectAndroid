package com.example.zhou.myfirst.beibei;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zhou.myfirst.R;

public class Main2Activity extends android.support.v4.app.FragmentActivity {

//    private TextView welcomeText;
//    private EditText todayPlan;
//    private Button startButton;

    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtFunc,rbtSeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.frag_home);
        mFragments[1] = fragmentManager.findFragmentById(R.id.frag_func);
        mFragments[2] = fragmentManager.findFragmentById(R.id.frag_setting);

        fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();

        radioGroup = (RadioGroup)findViewById(R.id.btnGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("tag","checkID="+checkedId);
                fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);

                switch (checkedId){
                    case R.id.rdoHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;
                    case R.id.rdoFunc:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    case R.id.rdoSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
