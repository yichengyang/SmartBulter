package com.example.fiver.smart_butler.fragment;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.fragment
 *  文件名:  WeChatFragment
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:35
 *  描述:  TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.adapter.WeChatAdapter;
import com.example.fiver.smart_butler.entity.WeChatData;
import com.example.fiver.smart_butler.ui.WebViewActivity;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeChatFragment extends Fragment {
   private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,null);
        findView(view);
        return view;
    }
    //初始化view
    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        //解析接口http://v.juhe.cn/weixin/query?key=您申请的KEY
        //http://v.juhe.cn/weixin/query?key=313184c165d2e734ffcb1b59fdbc19b3
        String url = "http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_KEY+"&ps=40";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t,Toast.LENGTH_SHORT).show();
                //L.i("微信精选"+t);
                parsingJson(t);
            }
        });
        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                //intent两种方法传值
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));
                startActivity(intent);
            }
        });
    }
    private void parsingJson(String t ){
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                WeChatData data = new WeChatData();
                String title = json.getString("title");
                String url = json.getString("url");

                data.setTitle(title);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));
                mList.add(data);

                mListTitle.add(title);
                mListUrl.add(url);
            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
