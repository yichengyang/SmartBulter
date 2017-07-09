package com.example.fiver.smart_butler.view;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.view
 *  文件名:  DispatchLinearLayout
 *  创建者:  YYC
 *  创建时间:  17/7/5 下午12:30
 *  描述:  TODO
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

public class DispatchLinearLayout extends LinearLayout {
    private DispatchKeyEventListener dispatchKeyEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return dispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }

    public static interface DispatchKeyEventListener {
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (dispatchKeyEventListener!=null){
            return dispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
