package com.example.fiver.smart_butler.fragment;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.fragment
 *  文件名:  GirlFragment
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:35
 *  描述:  TODO
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.adapter.GridAdapter;
import com.example.fiver.smart_butler.entity.GirlData;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.PicassoUtils;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.example.fiver.smart_butler.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.example.fiver.smart_butler.R.style.Theme_dialog;

public class GirlFragment extends Fragment{
    private GridView mGridView;
    private List<GirlData> mList = new ArrayList<>();
    private GridAdapter adapter;
    //提示框
    private CustomDialog dialog;
    //预览图片(PhotoView)
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();

    private PhotoViewAttacher mAttacher;



    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     *
     */


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = (GridView)view.findViewById(R.id.mGridView);
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.dialog_girl,Theme_dialog, Gravity.CENTER,R.style.pop_anmi_style);
        iv_img = (ImageView) dialog.findViewById(R.id.iv_img);
        String welfare = null;
        try {
            //Gank升級 需要转码
            //Gank升級 需要转码
            //Gank升級 需要转码
            //Gank升級 需要转码
            //Gank升級 需要转码
            welfare = URLEncoder.encode("福利", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //解析
        RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t,Toast.LENGTH_SHORT).show();
                //L.i("美女如云"+t);
                parsingJson(t);
            }
        });
        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 解析图片
                PicassoUtils.loadImageView(getActivity(),mListUrl.get(position),iv_img);
                mAttacher = new PhotoViewAttacher(iv_img);
                mAttacher.update();
                //显示dialog
                dialog.show();

            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);
                GirlData data = new GirlData();
                data.setImgUrl(url);
                L.i(url);
                mList.add(data);
            }
            adapter = new GridAdapter(getActivity(),mList);
            mGridView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
