package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  PhoneActivity
 *  创建者:  YYC
 *  创建时间:  17/7/1 上午10:20
 *  描述:  TODO
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_number;
    private ImageView iv_company;
    private TextView tv_result;
    private Button btn_1, btn_2, btn_3, btn_DEL, btn_4, btn_5, btn_6,
            btn_0, btn_7, btn_8, btn_9, btn_query;

    //输入数字布局
    private  LinearLayout panel_number;
    //标记位
     private boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    private void initView() {
        et_number = (EditText) findViewById(R.id.et_number);
        iv_company = (ImageView) findViewById(R.id.iv_company);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_DEL = (Button) findViewById(R.id.btn_DEL);
        btn_DEL.setOnClickListener(this);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        panel_number = (LinearLayout)findViewById(R.id.panel_number);

        //DEL长按事件
        btn_DEL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_number.setText("");
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        /**
         * 逻辑
         * 1.获取输入框内容
         * 2.判断是否为空
         * 3.网络请求
         * 4.解析Json
         * 5.结果显示
         * －－－－－
         * 键盘逻辑
         */

        //获取输入框内容
        String str = et_number.getText().toString();
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:

                if (flag){
                    flag = false;
                    str = "";
                    et_number.setText("");

                }
                et_number.setText(str + ((Button) v).getText());
                //移动光标
                et_number.setSelection(str.length() + 1);

                break;
            case R.id.btn_DEL:
                if (!TextUtils.isEmpty(str) & str.length() > 0) {
                    et_number.setText(str.substring(0, str.length() - 1));
                    et_number.setSelection(str.length() -1);
                }
                break;
            //查询
            case R.id.btn_query:
                //if (!TextUtils.isEmpty(str)) {
                    getPhone(str);

                break;

        }

    }
    //获取归属地
    private void getPhone(String str) {
        String url = "http://apis.juhe.cn/mobile/get?"+"phone="+str+"&key="+ StaticClass.PHONE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(PhoneActivity.this,"结果"+t,Toast.LENGTH_SHORT).show();
                //L.i("phone"+t);
                ParsingJson(t);
                //panel_number.setVisibility(View.GONE);
            }
        });
    }
    //解析数据
    private void ParsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            L.i("company"+company);
            String card = jsonResult.getString("card");
            L.i("card"+card);

            tv_result.setText("归属地:"+province+city+"\n"+"区号:"+areacode+"\n"+"邮编:"+zip+"\n"+"运营商:"+company+"\n");
            //tv_result.append("区号:"+areacode+"\n");
            //tv_result.append("邮编:"+zip+"\n");
            //tv_result.append("运营商:"+company+"\n");
            //tv_result.append("类型:"+card);

            switch (company){

                case "移动":
                    iv_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    iv_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
