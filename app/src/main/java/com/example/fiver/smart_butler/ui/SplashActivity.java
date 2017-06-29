package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  SplashActivity
 *  创建者:  YYC
 *  创建时间:  17/6/28 下午12:05
 *  描述:  闪屏页面
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fiver.smart_butler.MainActivity;
import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.utils.ShareUtils;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.example.fiver.smart_butler.utils.UtilTools;

public class SplashActivity extends AppCompatActivity{
    /**
     * 1.延迟2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏
     */

    private TextView tv_splash;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
            }
        }
    };
    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_First,true);
        if (isFirst){
            //是第一次运行
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_First,false);
            return true;
        }else{
            return false;
        }
    }
    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }
    private void initView(){
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash = (TextView)findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);
    }
}
