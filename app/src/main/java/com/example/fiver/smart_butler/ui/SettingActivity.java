package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  SettingActivity
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午10:33
 *  描述:  设置
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.utils.ShareUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch sw_speck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        sw_speck = (Switch)findViewById(R.id.sw_speak);
        sw_speck.setOnClickListener(this);
        boolean isSpeak = ShareUtils.getBoolean(this,"isSpeak",false);
        sw_speck.setChecked(isSpeak);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_speak:
                //切换相反
                sw_speck.setSelected(!sw_speck.isSelected());
                ShareUtils.putBoolean(this,"isSpeak",sw_speck.isChecked());

                break;
        }
    }
}
