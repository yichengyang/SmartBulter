package com.example.fiver.smart_butler.utils;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.utils
 *  文件名:  L
 *  创建者:  YYC
 *  创建时间:  17/6/28 上午10:19
 *  描述:  Log封装类
 */

import android.support.design.widget.TabLayout;
import android.util.Log;

public class L {
    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "SmartButler";
    //五个等级
    public static void d(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if (DEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if (DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if (DEBUG){
            Log.e(TAG,text);
        }
    }

}
