package com.example.fiver.smart_butler.adapter;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.adapter
 *  文件名:  WeChatAdapter
 *  创建者:  YYC
 *  创建时间:  17/7/2 下午1:31
 *  描述:  微信精选
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.WeChatData;
import com.example.fiver.smart_butler.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeChatAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;
    private int width,height;
    private WindowManager wm;

    public WeChatAdapter(Context mContext, List<WeChatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        //加载图片
        //Picasso.with(mContext).load(data.getImgUrl()).into(viewHolder.iv_image);
        //PicassoUtils.loadImageView(mContext,data.getImgUrl(),viewHolder.iv_image);

        PicassoUtils.loadImageViewSize(mContext,data.getImgUrl(),width/3,200,viewHolder.iv_image);
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_source;
    }
}
