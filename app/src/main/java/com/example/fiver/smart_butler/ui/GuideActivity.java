package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  GuideActivity
 *  创建者:  YYC
 *  创建时间:  17/6/28 下午12:35
 *  描述:  TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fiver.smart_butler.MainActivity;
import com.example.fiver.smart_butler.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;

    //小圆点
    private ImageView point1;
    private ImageView point2;
    private ImageView point3;

    //跳过按钮
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    //初始化View
    private void initView(){
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        point1 = (ImageView)findViewById(R.id.point1);
        point2 = (ImageView)findViewById(R.id.point2);
        point3 = (ImageView)findViewById(R.id.point3);
        //设置默认图片
        setPointImg(true,false,false);

        mViewPager = (ViewPager)findViewById(R.id.mViewpager_guide);

        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_two,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

       //设置滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    setPointImg(true,false,false);
                    iv_back.setVisibility(View.VISIBLE);
                }
                if (position == 1){
                    setPointImg(false,true,false);
                    iv_back.setVisibility(View.VISIBLE);
                }
                if (position==2){
                    setPointImg(false,false,true);
                    iv_back.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
    //设置小圆点的选中效果
    private void setPointImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
        if (isCheck1){
            point1.setImageResource(R.drawable.point_on);
        }else{
            point1.setImageResource(R.drawable.point_off);
        }
        if (isCheck2){
            point2.setImageResource(R.drawable.point_on);
        }else{
            point2.setImageResource(R.drawable.point_off);
        }
        if (isCheck3){
            point3.setImageResource(R.drawable.point_on);
        }else{
            point3.setImageResource(R.drawable.point_off);
        }
    }
}
