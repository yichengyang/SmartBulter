package com.example.fiver.smart_butler.entity;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.entity
 *  文件名:  MyUser
 *  创建者:  YYC
 *  创建时间:  17/6/29 上午9:40
 *  描述:  用户属性
 */


import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser{
    //父类BmobUser中已经有了name，password属性不需要再次定义
    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
