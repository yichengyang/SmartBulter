package com.example.fiver.smart_butler.view;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.view
 *  文件名:  CustomDialog
 *  创建者:  YYC
 *  创建时间:  17/6/29 下午8:06
 *  描述:  TODO
 */

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.fiver.smart_butler.R;

public class CustomDialog extends Dialog{
    //定义模版
    public CustomDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);

    }
    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim){
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }
    //实例化的构造函数
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.pop_anmi_style);
    }
}
