package com.example.fiver.smart_butler.entity;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.entity
 *  文件名:  ChatListData
 *  创建者:  YYC
 *  创建时间:  17/7/2 上午10:31
 *  描述:  对话列表的实体类
 */

public class ChatListData {
    //区分左边还是右边
    private int type;
    //内容文本
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
