package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  LoginActivity
 *  创建者:  YYC
 *  创建时间:  17/6/29 上午8:30
 *  描述:  登录
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.fiver.smart_butler.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;

        }
    }
}
