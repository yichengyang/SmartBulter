package com.example.fiver.smart_butler.ui;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.ui
 *  文件名:  WebViewActivity
 *  创建者:  YYC
 *  创建时间:  17/7/2 下午11:27
 *  描述:  新闻详情
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.utils.L;

public class WebViewActivity extends BaseActivity{
    //进度
    private ProgressBar mProgressBar;
    //网页
    private WebView mWebView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview );
        initView();
    }

    private void initView() {
        mProgressBar = (ProgressBar)findViewById(R.id.mProgressBar);
        mWebView = (WebView)findViewById(R.id.mWevView);
        Intent intent = getIntent();
        String title=intent.getStringExtra("title");
        final String url  = intent.getStringExtra("url");
        L.i("url"+url);
        //设置标题
        getSupportActionBar().setTitle(title);

        //进行加载网页的逻辑
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //本地的接口
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);
        //设置本地加载
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });

    }
    class WebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
