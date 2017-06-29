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
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fiver.smart_butler.MainActivity;
import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.MyUser;
import com.example.fiver.smart_butler.utils.ShareUtils;
import com.example.fiver.smart_butler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register;
    private EditText login_et_name;
    private EditText login_et_password;
    private Button btn_login;
    private CheckBox keep_password;
    private TextView tv_forget;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        login_et_name = (EditText) findViewById(R.id.login_et_name);
        login_et_password = (EditText) findViewById(R.id.login_et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        tv_forget = (TextView)findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog = new CustomDialog(this,300,300,R.layout.dialog_loading,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anmi_style);
        //屏幕点击无效
        dialog.setCancelable(false);

        //设置选中状态
        boolean isChecked = ShareUtils.getBoolean(this, "keep_pass_state", false);
        keep_password.setChecked(isChecked);
        if (isChecked){
            login_et_name.setText(ShareUtils.getString(this,"name",""));
            login_et_password.setText(ShareUtils.getString(this,"password",""));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btn_login:
                //1.获取输入框的值
                String name = login_et_name.getText().toString().trim();
                String password = login_et_password.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //3.登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //保存状态
        ShareUtils.putBoolean(this, "keep_pass_state", keep_password.isChecked());
        super.onDestroy();

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住密码
            ShareUtils.putString(this, "name", login_et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", login_et_password.getText().toString().trim());

        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}
