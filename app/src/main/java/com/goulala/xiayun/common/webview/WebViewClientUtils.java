package com.goulala.xiayun.common.webview;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * author      : Z_B
 * date       : 2018/10/19
 * function  : 加载webView
 */
public class WebViewClientUtils {

    public static void setUpWebView(WebView mWebView, WebViewClientBase webViewClient, String contentUrl, boolean isUrl) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.setWebViewClient(webViewClient);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        if (isUrl) {
            mWebView.loadUrl(contentUrl);//url地址
        } else {
            //HTML
            mWebView.loadDataWithBaseURL(null, contentUrl, "text/html", "utf-8", null);
        }
    }
}
