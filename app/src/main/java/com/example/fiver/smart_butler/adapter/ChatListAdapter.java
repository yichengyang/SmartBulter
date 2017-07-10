package com.example.fiver.smart_butler.adapter;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.adapter
 *  文件名:  ChatListAdapter
 *  创建者:  YYC
 *  创建时间:  17/7/2 上午10:29
 *  描述:  聊天适配器
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.ChatListData;
import com.example.fiver.smart_butler.ui.BaseActivity;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {
    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;
    //需要的参数
    private Context mContext;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> mList;

    public ChatListAdapter(Context mContext,List<ChatListData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前要显示的的type
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.tv_left_text = (TextView)convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.tv_right_text = (TextView)convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(viewHolderRightText);
                    break;
            }
        }else {
                switch (type){
                    case  VALUE_LEFT_TEXT:
                        viewHolderLeftText = (ViewHolderLeftText)convertView.getTag();
                        break;
                    case VALUE_RIGHT_TEXT:
                        viewHolderRightText = (ViewHolderRightText) convertView.getTag();
                        break;
                }
        }
        //赋值
        data = mList.get(position);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }

        return convertView;
    }
    //根据数据源position来显示要反回的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }
    //返回所有的layout数据

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    //左边的文本
    class ViewHolderLeftText{
        private TextView tv_left_text;
    }
    //右边的文本
    class ViewHolderRightText{
        private TextView tv_right_text;
    }
}
