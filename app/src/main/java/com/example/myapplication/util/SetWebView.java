package com.example.myapplication.util;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SetWebView {
    public static void setWebView(WebView webView,String url){

        webView.getSettings().setSupportZoom(true);                        //设置可以支持缩放

        webView.getSettings().setBuiltInZoomControls(true);                //设置出现缩放工具

        webView.getSettings().setUseWideViewPort(true);                    //扩大比例的缩放

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                                                                            //自适应屏幕,也就是不能左右滑动

        webView.getSettings().setLoadWithOverviewMode(true);                //加载完全缩小的WebView

        webView.getSettings().setJavaScriptEnabled(true);                  //支持JavaScript

        //webView.getSettings().setAppCacheEnabled(true);                    //开启应用缓存

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);      //设置缓存模式

        webView.getSettings().setDomStorageEnabled(true);                  //开启 DOM storage API 功能

        webView.setWebViewClient(new WebViewClient(){       //防止访问网页时跳转到外部页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {   //防止跳转到外部浏览器
                return false;
            }
        });
        webView.loadUrl(url);
    }
}
