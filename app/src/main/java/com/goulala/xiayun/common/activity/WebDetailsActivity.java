package com.goulala.xiayun.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.ipresenter.WebDetailsPresenter;
import com.goulala.xiayun.common.iview.IWebDetailsView;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.common.webview.WebViewClientBase;
import com.goulala.xiayun.common.webview.WebViewClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * web详情的activity
 */
public class WebDetailsActivity extends BaseMvpActivity<WebDetailsPresenter> implements IWebDetailsView {

    private WebView webContent;
    private String url, title;
    private WebViewClientBase webViewClient = new WebViewClientBase();
    private int classType;

    public static void start(Context context, int classType) {
        start(context, "", "", classType);
    }

    public static void start(Context context, String title, String url, int classType) {
        Intent intent = new Intent(context, WebDetailsActivity.class);
        intent.putExtra(ConstantValue.TITLE, title);
        intent.putExtra(ConstantValue.URL, url);
        intent.putExtra(ConstantValue.TYPE, classType);
        context.startActivity(intent);
    }


    @Override
    protected WebDetailsPresenter createPresenter() {
        return new WebDetailsPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        title = getIntent().getStringExtra(ConstantValue.TITLE);
        url = getIntent().getStringExtra(ConstantValue.URL);
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_web_details;
    }

    @Override
    public void bindViews(View contentView) {
//        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        webContent = get(R.id.webContent);

        switch (classType) {
            case ConstantValue.CLASS_TYPE_OF_ABOUT_US:
                //关于我们
                initTitle(mContext.getString(R.string.About_us));
                getRulesUrl(ApiParam.ABOUT_US_URL);
                break;
            case ConstantValue.RULES_OF_THE_PLATFORM_URL:
                //平台规则说明
                initTitle(mContext.getString(R.string.Description_of_platform_rules));
                getRulesUrl(ApiParam.RULES_OF_THE_PLATFORM_URL);
                break;
            case ConstantValue.COMMON_PROBLEMS_URL:
                //常用说明
                initTitle(mContext.getString(R.string.Frequently_asked_questions));
                getRulesUrl(ApiParam.COMMON_PROBLEMS_URL);
                break;
            case ConstantValue.MEMBER_SERVICE_AGREEMENT_URL:
                //会员服务协议
                initTitle(mContext.getString(R.string.Membership_service_agreement));
                getRulesUrl(ApiParam.MEMBER_SERVICE_AGREEMENT_URL);
                break;

            case ConstantValue.ADVER_DETAILS_TYPE:
                if (!TextUtils.isEmpty(title)) {
                    initTitle(title);
                }
                if (!TextUtils.isEmpty(url)) {
                    WebViewClientUtils.setUpWebView(webContent, webViewClient, url, true);
                }
                break;

            default:
                //其他详情
                if (!TextUtils.isEmpty(title)) {
                    initTitle(title);
                }
                if (!TextUtils.isEmpty(url)) {
                    WebViewClientUtils.setUpWebView(webContent, webViewClient, url, true);
                }
                break;

        }
    }

    /**
     * 获取url
     */
    private void getRulesUrl(String paramUrl) {
        Map<String, String> webViewParam = new HashMap<>();
        webViewParam.put(ApiParam.BASE_METHOD_KEY, paramUrl);
        String webViewParamJson = JsonUtils.toJson(webViewParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getWebViewDetails(userToken, webViewParamJson);
        showDialog("");
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void webViewDetails(String webUrl) {
        if (!TextUtils.isEmpty(webUrl)) {
            WebViewClientUtils.setUpWebView(webContent, webViewClient, webUrl, false);
        }
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
        dismissDialog();
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
        dismissDialog();
    }
}
