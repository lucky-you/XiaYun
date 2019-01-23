package com.goulala.xiayun.common.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.ipresenter.LoginPresenter;
import com.goulala.xiayun.common.iview.ILoginView;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.RoundImageView;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by：Z_B on 2018/6/15 12:00
 * Effect：登录界面
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements ILoginView {


    private RoundImageView civGllLogo;
    private TextView tvWeChatAuthorizesLogin;
    private TextView tvMobilePhoneNumberLogin;
    private RelativeLayout rlLoginTitle;
    private LinearLayout llLoginRootLayout;
    private String userUnionId, userOpenId, userName, userImageView, userGender;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void bindViews(View contentView) {
        get(R.id.iv_back).setOnClickListener(this);
        civGllLogo = get(R.id.civ_gll_logo);
        tvWeChatAuthorizesLogin = get(R.id.tv_WeChat_authorizes_login);
        tvWeChatAuthorizesLogin.setOnClickListener(this);
        tvMobilePhoneNumberLogin = get(R.id.tv_Mobile_phone_number_login);
        tvMobilePhoneNumberLogin.setOnClickListener(this);
        rlLoginTitle = get(R.id.rl_login_title);
        llLoginRootLayout = get(R.id.ll_login_root_layout);
//        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_WeChat_authorizes_login:
                //获取微信授权后，绑定手机号码
//                useWXLogin();
                break;
            case R.id.tv_Mobile_phone_number_login:
                //直接使用手机号码登录
                BindPhoneNumberActivity.start(mContext, ConstantValue.THE_CLASS_OF_VERIFY_NUMBER_LOGIN_TYPE);
                finish();
                break;
        }
    }

    private void useWXLogin() {
        if (!WeiXinPayUtils.isExist(mContext)) {
            showToast(mContext.getString(R.string.No_WeChat_is_installed));
            return;
        }
//        ShareSDKLoginUtils.authorLogin(mContext, ShareSDKLoginUtils.WX_LOGIN, new OnLoginListener() {
//            @Override
//            public void authorizeSuccess(String unionId, String openId, String nickName, String headImageUrl, String userSex) {
//                if (!TextUtils.isEmpty(unionId)) {
//                    userUnionId = unionId;
//                    userOpenId = openId;
//                    userName = nickName;
//                    userImageView = headImageUrl;
//                    userGender = userSex;
//                    useWeChatLogin(unionId, openId);
//                }
//            }
//
//            @Override
//            public void getProfileError(String info) {
//                showToast(mContext.getString(R.string.Authorization_failure, info));
//            }
//        });

    }

    /**
     * 使用微信登录
     */
    private void useWeChatLogin(String unionid, String openId) {
        Map<String, String> wxLoginParam = new HashMap<>();
        wxLoginParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.USE_WCCHAT_LOGIN_URL);
        wxLoginParam.put(ApiParam.UNIONID, unionid);
        wxLoginParam.put(ApiParam.OPENID, openId);
        String wxLoginParamJson = JsonUtils.toJson(wxLoginParam);
        LogUtils.showLog(userToken, wxLoginParamJson);
        mvpPresenter.toLogin(userToken, wxLoginParamJson);

    }

    @Override
    public void LoginSuccess(UserInfo userInfo, String message) {
        dismissDialog();
        if (userInfo != null) {
            showToast(message);
            UserUtils.loginIn(userInfo);
            intentToActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    public void LoginFailed(int code, String message) {
        if (0 == code) {
            BindPhoneNumberActivity.start(mContext,
                    ConstantValue.THE_CLASS_OF_BIND_PHONE_NUMBER_TYPE,
                    userUnionId,
                    userOpenId,
                    userName,
                    userImageView,
                    userGender);
        }
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
    }


}
