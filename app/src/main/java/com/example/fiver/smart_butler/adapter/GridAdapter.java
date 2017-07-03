package com.example.fiver.smart_butler.adapter;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.adapter
 *  文件名:  GridAdapter
 *  创建者:  YYC
 *  创建时间:  17/7/3 下午12:06
 *  描述:  TODO
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.GirlData;
import com.example.fiver.smart_butler.utils.PicassoUtils;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private List<GirlData> mList;
    private GirlData data;
    private LayoutInflater inflater;
    //屏幕宽度
    private int width;
    private WindowManager wm;

    public GridAdapter(Context mContext,List<GirlData> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item,null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        //解析图片
        String url = data.getImgUrl();
        PicassoUtils.loadImageViewSize(mContext,url,width/2,500,viewHolder.imageView);

        return convertView;
    }
    class ViewHolder{
        private ImageView imageView;
    }
}
