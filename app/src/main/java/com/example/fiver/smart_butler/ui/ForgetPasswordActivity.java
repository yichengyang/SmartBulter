package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  ForgetPasswordActivity
 *  创建者:  YYC
 *  创建时间:  17/6/29 下午6:44
 *  描述:  TODO
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.MyUser;
import com.example.fiver.smart_butler.utils.UtilTools;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_email_forget;
    private Button btn_forgetPassword_forget;
    private EditText et_old;
    private EditText et_new;
    private EditText et_new_again;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        et_email_forget = (EditText) findViewById(R.id.et_email_forget);
        btn_forgetPassword_forget = (Button) findViewById(R.id.btn_forgetPassword_forget);
        btn_forgetPassword_forget.setOnClickListener(this);
        et_old = (EditText)findViewById(R.id.et_old);
        et_new = (EditText)findViewById(R.id.et_new);
        et_new_again = (EditText)findViewById(R.id.et_new_again);
        btn_update =(Button)findViewById(R.id.btn_updatePassword);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forgetPassword_forget:
                //1.获取输入框的邮箱
                final String email = et_email_forget.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(email)) {
                    //3.发送邮件
                    MyUser user = new MyUser();
                    user.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this, "邮件已发送到" + email, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "邮件发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_updatePassword:
                //1.获取输入框的值
                String oldPassword = et_old.getText().toString().trim();
                String newPassword = et_new.getText().toString().trim();
                String newPasswordAgain = et_new_again.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(oldPassword)&
                        !TextUtils.isEmpty(newPassword)&
                        !TextUtils.isEmpty(newPasswordAgain)){
                    //3.判断两次输入是否相同
                    if (newPassword.equals(newPasswordAgain)){
                    //4.重置密码
                        MyUser user  = new MyUser();
                        user.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"密码重置成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this,"密码重置失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this,"两次输入密码不同",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
