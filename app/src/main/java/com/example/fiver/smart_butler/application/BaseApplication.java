package com.example.fiver.smart_butler.application;

import android.app.Application;

import com.example.fiver.smart_butler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Created by yang on 17/6/27.
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bomb
        Bmob.initialize(this, StaticClass.BOMB_APP_ID);
    }
}
