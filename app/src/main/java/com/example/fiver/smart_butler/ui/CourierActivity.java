package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  CourierActivity
 *  创建者:  YYC
 *  创建时间:  17/6/30 下午3:28
 *  描述:  TODO
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.adapter.CourierAdapter;
import com.example.fiver.smart_butler.entity.CourierData;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;
    private ListView mListView;
    private List<CourierData> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }
    private void initView(){
        et_name = (EditText)findViewById(R.id.et_name);
        et_number = (EditText)findViewById(R.id.et_number);
        btn_get_courier = (Button)findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_courier:
                /**
                 * 快递界面点击查询后
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.拿到输入的数据去请求数据(Json)
                 * 4.解析Json数据
                 * 5.listView适配器
                 * 6.实体类
                 * 7.设置数据
                 * 8.显示效果
                 */
                //1.获取输入
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                //拼接Url
                String url = "http://v.juhe.cn/exp/index?"+
                        "key="+ StaticClass.COURIER_KEY+"&com="+name+"&no="+number;
                //2.判断内容
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(number)){
                    //3.请求数据
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                            L.i("Json:"+t);
                            //4.解析Json
                            parsingJson(t);

                        }
                    });

                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //解析数据
    private void parsingJson(String t){
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                Collections.reverse(mList);
                mList.add(data);
            }
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    };
}
