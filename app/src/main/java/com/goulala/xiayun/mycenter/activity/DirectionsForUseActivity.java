package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.webview.WebViewClientBase;
import com.goulala.xiayun.common.webview.WebViewClientUtils;
import com.goulala.xiayun.mycenter.presenter.DirectionsForUsePresenter;
import com.goulala.xiayun.mycenter.view.IDirectionsForUseView;

import java.util.HashMap;
import java.util.Map;

/**
 * author      : Z_B
 * date       : 2019/1/22
 * function  :  说明的activity
 */
public class DirectionsForUseActivity extends BaseMvpActivity<DirectionsForUsePresenter> implements IDirectionsForUseView {

    private WebView webContent;
    private WebViewClientBase webViewClient = new WebViewClientBase();
    private int classType;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, DirectionsForUseActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected DirectionsForUsePresenter createPresenter() {
        return new DirectionsForUsePresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_directions_use_dialog_layout;
    }

    @Override
    public void bindViews(View contentView) {
        webContent = get(R.id.webContent);
        get(R.id.rl_close_dialog).setOnClickListener(this);
        switch (classType) {
            case ConstantValue.COUPON_DESCRIPTION_TYPE:
                getRulesText(ApiParam.GET_COUPONS_RULES_URL);
                break;
            case ConstantValue.COMMISSION_DESCRIPTION_TYPE:
                getRulesText(ApiParam.GET_WITHDRAWAL_INSTRUCTIONS_URL);
                break;
        }
    }

    /**
     * 获取规则
     */
    private void getRulesText(String paramUrl) {
        Map<String, String> rulesParam = new HashMap<>();
        rulesParam.put(ApiParam.BASE_METHOD_KEY, paramUrl);
        String rulesParamJson = JsonUtils.toJson(rulesParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getCouponsRules( userToken, rulesParamJson);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {
        if (view.getId() == R.id.rl_close_dialog) {
            finish();
        }
    }

    @Override
    public void getCouponsRules(String rules) {
        if (!TextUtils.isEmpty(rules)) {
            WebViewClientUtils.setUpWebView(webContent, webViewClient, rules, false);
        }
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }
}
