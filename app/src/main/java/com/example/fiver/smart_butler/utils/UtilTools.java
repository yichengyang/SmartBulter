package com.example.fiver.smart_butler.utils;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.utils
 *  文件名:  UtilTools
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:08
 *  描述:  工具的统一类
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilTools {
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
        //tv_splash.setTypeface(fontType);

    }
    //保存图片到ShareUtils
    public static  void putImageToShare(Context context,ImageView imageView){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //1.将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //2.利用Base64将字节数组输出流转化成String
        byte[] byArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byArray,Base64.DEFAULT));
        //3.将String保存在shareUtils
        ShareUtils.putString(context,"image_title",imgString);
    }
    //读取图片
    public static  void getImageToShare(Context context,ImageView imageView){
        //1.拿到String
        String imgString = ShareUtils.getString(context,"image_title","");
        if (!imgString.equals("")){
            byte[] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);

        }
    }
}
