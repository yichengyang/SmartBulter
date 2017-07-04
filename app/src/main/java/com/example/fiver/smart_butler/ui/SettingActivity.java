package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  SettingActivity
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午10:33
 *  描述:  设置
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.Service.SmsService;
import com.example.fiver.smart_butler.utils.ShareUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch sw_speck;
    private Switch sw_sms;
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

        sw_sms = (Switch)findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);

        boolean isSms = ShareUtils.getBoolean(this,"isSms",false);
        sw_sms.setChecked(isSms);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_speak:
                //切换相反
                sw_speck.setSelected(!sw_speck.isSelected());
                ShareUtils.putBoolean(this,"isSpeak",sw_speck.isChecked());

                break;
            case R.id.sw_sms:
                sw_sms.setSelected(!sw_sms.isSelected());
                ShareUtils.putBoolean(this,"isSms",sw_sms.isChecked());
                if (sw_sms.isChecked()){
                    startService(new Intent(this,SmsService.class));
                }else{
                    stopService(new Intent(this,SmsService.class));
                }
                break;
        }
    }
}
