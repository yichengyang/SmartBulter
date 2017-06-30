package com.example.fiver.smart_butler.fragment;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.fragment
 *  文件名:  UserFragment
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:35
 *  描述:  TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.MyUser;
import com.example.fiver.smart_butler.ui.LoginActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private Button btn_update_ok;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);
        btn_update_ok = (Button)view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        //默认是不可点击的
        setEnabled(false);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex() ? "男" : "女");
        et_age.setText(userInfo.getAge()+"");
        et_desc.setText(userInfo.getDesc());
    }

    //控制焦点
    private void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

            case R.id.edit_user:
                btn_update_ok.setVisibility(View.VISIBLE);
                setEnabled(true);
                break;
            case R.id.btn_update_ok:
                //拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                //判断是否为空
                if(!TextUtils.isEmpty(username)&
                        !TextUtils.isEmpty(age)&
                        !TextUtils.isEmpty(sex)){
                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")){
                        user.setSex(true);
                    }else{
                        user.setSex(false);
                    }
                    if (!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else{
                        user.setDesc("这个人很懒，什么都没留下！！");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"更新成功",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{

                }
                break;
        }
    }
}
