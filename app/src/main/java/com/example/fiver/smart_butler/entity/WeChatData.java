package com.example.fiver.smart_butler.entity;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.entity
 *  文件名:  WeChatData
 *  创建者:  YYC
 *  创建时间:  17/7/2 下午1:32
 *  描述: 微信精选的实体类
 */

public class WeChatData {
    //标题
    private String title;
    //出处
    private String source;
    //图片的url
    private String imgUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
