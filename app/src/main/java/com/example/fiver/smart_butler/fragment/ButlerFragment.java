package com.example.fiver.smart_butler.fragment;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.fragment
 *  文件名:  ButlerFragment
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:35
 *  描述:  TODO
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.adapter.ChatListAdapter;
import com.example.fiver.smart_butler.entity.ChatListData;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ButlerFragment extends Fragment implements View.OnClickListener {
    private ListView mChatListView;
    //private Button btn_left,btn_right;
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;
    private EditText et_text;
    private Button btn_send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
//        btn_left = (Button)view.findViewById(R.id.btn_left);
//        btn_left.setOnClickListener(this);
//        btn_right = (Button)view.findViewById(R.id.btn_right);
//        btn_right.setOnClickListener(this);
        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);


        //设置适配器

        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);
        addLeftText("你好，我是小优");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                /**
                 * 逻辑
                 * 1.获取输入框内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.清空输入框
                 * 5.添加输入的内容到right item
                 * 6.发送机器人请求并返回内容
                 * 7.拿到机器人的返回值后到left item
                 */

                String text = et_text.getText().toString();
                if (!TextUtils.isEmpty(text)){
                    if (text.length()<30){
                        et_text.setText("");
                        addRightText(text);
                        //发送机器人请求并返回内容
                        String url = "http://op.juhe.cn/robot/index?info="+text+"&key="+ StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.i(t);
                                ParsingText(t);
                            }
                        });

                    }else{
                        Toast.makeText(getActivity(),"输入长度不能超过30",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //添加左边文本
    private void addLeftText(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        adapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightText(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        adapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }
    //解析Json数据
    public void ParsingText(String t){
        try {
            JSONObject jsonObject = new JSONObject(t);
            //String text = jsonObject.getString("text");
            //L.i(text);
            JSONObject json = jsonObject.getJSONObject("result");
            String text = json.getString("text");
            addLeftText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
