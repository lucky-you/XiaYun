package com.goulala.xiayun.common.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.share.PlatformAuthorizeUserInfoManager;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.RoundImageView;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;


/**
 * Created by：Z_B on 2018/6/15 12:00
 * Effect：登录界面
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements ILoginView, PlatformActionListener {


    private RoundImageView civGllLogo;
    private TextView tvWeChatAuthorizesLogin;
    private TextView tvMobilePhoneNumberLogin;
    private RelativeLayout rlLoginTitle;
    private LinearLayout llLoginRootLayout;
    private String userUnionId, userOpenId, userName, userImageView, userGender;
    private PlatformAuthorizeUserInfoManager platformAuthorizeUserInfoManager;


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
        StatusBarUtil.setStatusBar(this, false, false);
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
        if (platformAuthorizeUserInfoManager == null) {
            platformAuthorizeUserInfoManager = new PlatformAuthorizeUserInfoManager(this);
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_WeChat_authorizes_login:
                //获取微信授权后，绑定手机号码、
                if (!WeiXinPayUtils.isExist(mContext)) {
                    showToast(mContext.getString(R.string.No_WeChat_is_installed));
                    return;
                }
                platformAuthorizeUserInfoManager.WeiXinAuthorize();

                break;
            case R.id.tv_Mobile_phone_number_login:
                //直接使用手机号码登录
                BindPhoneNumberActivity.start(mContext, ConstantValue.THE_CLASS_OF_VERIFY_NUMBER_LOGIN_TYPE);
                finish();
                break;
        }
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
        mvpPresenter.toUseWeChatLogin(userToken, wxLoginParamJson);
        showDialog("");
    }

    @Override
    public void LoginSuccess(UserInfo userInfo, String message) {
        dismissDialog(); //微信登录成功=--》已经绑定手机了
        if (userInfo != null) {
            showToast(message);
            UserUtils.loginIn(userInfo);
            intentToActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    public void LoginFailed(int code, String message) {
        dismissDialog();
        if (0 == code) { //登录失败--去绑定手机号码
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
        dismissDialog();
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
        dismissDialog();
    }


    @Override
    public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userImageView = platform.getDb().getUserIcon();
                userGender = platform.getDb().getUserGender();  //m为男
                userName = platform.getDb().getUserName();
                userOpenId = platform.getDb().get("openid");
                userUnionId = platform.getDb().get("unionid");
                Log.d("xy",
                        "userImageView=" + userImageView + "\n"
                                + "userGender=" + userGender + "\n"
                                + "userName=" + userName + "\n"
                                + "userOpenId=" + userOpenId + "\n"
                                + "userUnionId=" + userUnionId + "\n"
                );
                useWeChatLogin(userUnionId, userOpenId);
            }
        });
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showToast(mContext.getString(R.string.Authorization_failure, throwable.getMessage()));
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
