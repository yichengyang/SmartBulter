package com.example.fiver.smart_butler.utils;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.utils
 *  文件名:  UtilTools
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:08
 *  描述:  工具的统一类
 */

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class UtilTools {
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
        //tv_splash.setTypeface(fontType);

    }
}
