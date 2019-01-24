package com.goulala.xiayun.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.ipresenter.BindPhoneNumberPresenter;
import com.goulala.xiayun.common.iview.IBindPhoneNumberView;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.PhoneUtils;
import com.goulala.xiayun.common.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 绑定手机号码 和 验证手机号码登录 公用的界面
 */
public class BindPhoneNumberActivity extends BaseMvpActivity<BindPhoneNumberPresenter> implements IBindPhoneNumberView {

    private EditText editPleaseEditPhoneNumber;
    private EditText editPleaseEditVerificationCode;
    private TextView tvGetTheVerificationCode;
    private TextView tvTheBinding;
    private String phoneNumber, VerificationCode;
    private int classType;
    private String userUnionId, userOpenId, userName, userImageView, userGender;
    private Disposable mdDisposable;

    public static void start(Context context, int classType) {
        start(context, classType, "", "", "", "", "");
    }

    public static void start(Context context, int classType, String unionId, String userOpenId, String name, String imageView, String gender) {
        Intent intent = new Intent(context, BindPhoneNumberActivity.class);
        intent.putExtra(ConstantValue.INDEX, classType);
        intent.putExtra(ConstantValue.ID, unionId);
        intent.putExtra(ApiParam.OPENID, userOpenId);
        intent.putExtra(ConstantValue.USER_NAME, name);
        intent.putExtra(ConstantValue.USER_IMAGE_VIEW, imageView);
        intent.putExtra(ConstantValue.USER_GENDER, gender);
        context.startActivity(intent);
    }


    @Override
    protected BindPhoneNumberPresenter createPresenter() {
        return new BindPhoneNumberPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        classType = getIntent().getIntExtra(ConstantValue.INDEX, -1);
        userUnionId = getIntent().getStringExtra(ConstantValue.ID);
        userOpenId = getIntent().getStringExtra(ApiParam.OPENID);
        userName = getIntent().getStringExtra(ConstantValue.USER_NAME);
        userImageView = getIntent().getStringExtra(ConstantValue.USER_IMAGE_VIEW);
        userGender = getIntent().getStringExtra(ConstantValue.USER_GENDER);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_bind_phone_number;
    }

    @Override
    public void bindViews(View contentView) {
        editPleaseEditPhoneNumber = get(R.id.edit_please_edit_phone_number);
        editPleaseEditVerificationCode = get(R.id.edit_please_edit_verification_code);
        tvGetTheVerificationCode = get(R.id.tv_Get_the_verification_code);
        tvGetTheVerificationCode.setOnClickListener(this);
        tvTheBinding = get(R.id.tv_The_binding);
        tvTheBinding.setOnClickListener(this);

        if (classType == ConstantValue.THE_CLASS_OF_BIND_PHONE_NUMBER_TYPE) {
            //绑定手机号码
            initTitle(mContext.getString(R.string.Bind_mobile_phone_number));
            tvTheBinding.setText(mContext.getString(R.string.The_binding));
        } else {
            //验证手机号码登录
            initTitle(mContext.getString(R.string.Verify_phone_number_login));
            tvTheBinding.setText(mContext.getString(R.string.the_login));
        }

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_Get_the_verification_code:
                //获取验证码
                phoneNumber = editPleaseEditPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast(mContext.getString(R.string.please_edit_phone_number));
                    return;
                }
                if (!PhoneUtils.checkPhone(phoneNumber, true)) {
                    return;
                }
                getVerificationCode();
                countdownTime();
                break;
            case R.id.tv_The_binding:
                if (classType == ConstantValue.THE_CLASS_OF_BIND_PHONE_NUMBER_TYPE) {
                    //绑定手机号码
                    startLoginOrBindPhone(ApiParam.USE_PHONE_TO_LOGIN_VALUE, ConstantValue.THE_CLASS_OF_BIND_PHONE_NUMBER_TYPE);
                } else {
                    //直接登录
                    startLoginOrBindPhone(ApiParam.USE_PHONE_TO_LOGIN_VALUE, ConstantValue.THE_CLASS_OF_VERIFY_NUMBER_LOGIN_TYPE);
                }
                break;
        }
    }

    /**
     * 倒计时
     */
    private void countdownTime() {
        final int count = 60;//倒计时60秒
        mdDisposable = Flowable.intervalRange(0, count, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvGetTheVerificationCode.setEnabled(false);
                        String showText = (count - aLong) + "s";
                        tvGetTheVerificationCode.setText(showText);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //倒计时完毕置为可点击状态
                        tvGetTheVerificationCode.setEnabled(true);
                        tvGetTheVerificationCode.setText(mContext.getString(R.string.Once_again));
                    }
                })
                .subscribe();
    }


    /**
     * 获取验证码
     */
    private void getVerificationCode() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_VERIFICATION_CODE_VALUE);
        params.put(ApiParam.MOBILE_KEY, phoneNumber);
        params.put(ApiParam.EVENT_KEY, ApiParam.MOBILE_LOGIN_VALUE);
        String paramsJsonObject = JsonUtils.toJson(params);
        mvpPresenter.getCode(userToken, paramsJsonObject);
    }

    /**
     * 绑定手机号码/或者直接登录
     */
    private void startLoginOrBindPhone(String requestUrl, int requestType) {
        phoneNumber = editPleaseEditPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(mContext.getString(R.string.please_edit_phone_number));
            return;
        }
        VerificationCode = editPleaseEditVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(VerificationCode)) {
            showToast(mContext.getString(R.string.please_edit_verification_code));
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put(ApiParam.BASE_METHOD_KEY, requestUrl);
        param.put(ApiParam.MOBILE_KEY, phoneNumber);
        param.put(ApiParam.CAPTCHA_KEY, VerificationCode);
        String paramsJsonObject = JsonUtils.toJson(param);
        mvpPresenter.startLoginOrBindPhoneNumber(requestType, userToken, paramsJsonObject);

    }

    @Override
    public void getCodeSuccess(String message) {
        showToast(message);
    }

    @Override
    public void loginOrBindPhoneNumberSuccess(int requestType, UserInfo userInfo, String message) {
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getToken())) {
            switch (requestType) {
                case ConstantValue.THE_CLASS_OF_BIND_PHONE_NUMBER_TYPE:
                    //使用微信登录的，需要绑定微信账号
                    WeChatAccountBinding(userInfo.getToken());
                    break;

                case ConstantValue.THE_CLASS_OF_VERIFY_NUMBER_LOGIN_TYPE:
                    //直接登录
//                    UserUtils.loginIn(userInfo);
//                    intentToActivity(MainActivity.class);
//                    finish();
//                    break;
                case ConstantValue.THE_TYPE_OF_BIND_WECHAT:
                    UserUtils.loginIn(userInfo);
                    //微信绑定成功
                    intentToActivity(MainActivity.class);
                    finish();
                    break;

            }
        }
    }

    /**
     * 微信账号绑定
     */
    private void WeChatAccountBinding(String userToken) {
        Map<String, String> wxAccountBindParam = new HashMap<>();
        wxAccountBindParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.WECHAT_ACCOUNT_BINDING_URL);
        wxAccountBindParam.put(ApiParam.UNIONID, userUnionId);
        wxAccountBindParam.put(ApiParam.OPENID, userOpenId);
        wxAccountBindParam.put(ApiParam.NICK_NAME, userName);
        wxAccountBindParam.put(ApiParam.HEAD_IMAGE_URL, userImageView);
        wxAccountBindParam.put(ApiParam.SEX, userGender);
        String wxAccountBindParamJson = JsonUtils.toJson(wxAccountBindParam);
        mvpPresenter.startLoginOrBindPhoneNumber(ConstantValue.THE_TYPE_OF_BIND_WECHAT, userToken, wxAccountBindParamJson);

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
    protected void onDestroy() {
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
        super.onDestroy();
    }
}
