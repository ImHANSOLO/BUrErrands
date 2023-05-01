package com.example.myapplication.util;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class SetProgressBar {                    //单独建立一个加载进度条的类和方法

    public static void setProgressBar(WebView webView,ProgressBar progressBar){

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);           //加载完成后消失
                }else {
                    progressBar.setVisibility(View.VISIBLE);        //未加载完成则可见
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }
}
