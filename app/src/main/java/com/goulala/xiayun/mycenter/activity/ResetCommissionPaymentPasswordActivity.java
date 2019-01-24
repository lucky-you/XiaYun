package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.PhoneUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.mycenter.presenter.ResetCommissionPaymentPresenter;
import com.goulala.xiayun.mycenter.view.IResetCommissionPaymentView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 设置或者重置佣金支付密码，第一步，通过手机号码获取验证码
 * 公用的activity
 */
public class ResetCommissionPaymentPasswordActivity extends BaseMvpActivity<ResetCommissionPaymentPresenter> implements IResetCommissionPaymentView {


    private TextView tvThePhoneNumberIsBound;
    private EditText editPleaseEditVerificationCode;
    private TextView tvGetTheVerificationCode;
    private TextView tvValidation;
    private int thatClassType;
    private String mobilNumber, validationCodeNumber;
    private Disposable mdDisposable;

    public static void start(Context context, int classType) {
        Intent intent = new Intent(context, ResetCommissionPaymentPasswordActivity.class);
        intent.putExtra(ConstantValue.TITLE, classType);
        context.startActivity(intent);
    }

    @Override
    protected ResetCommissionPaymentPresenter createPresenter() {
        return new ResetCommissionPaymentPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        thatClassType = getIntent().getIntExtra(ConstantValue.TITLE, -1);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_reset_commission_payment_password;
    }

    @Override
    public void bindViews(View contentView) {
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        tvThePhoneNumberIsBound = get(R.id.tv_The_phone_number_is_bound);
        editPleaseEditVerificationCode = get(R.id.edit_please_edit_verification_code);
        tvGetTheVerificationCode = get(R.id.tv_Get_the_verification_code);
        tvGetTheVerificationCode.setOnClickListener(this);
        tvValidation = get(R.id.tv_validation);
        tvValidation.setOnClickListener(this);
        switch (thatClassType) {
            case ConstantValue.SET_THE_COMMISSION_PAYMENT_PASSWORD:
                initTitle(mContext.getString(R.string.Set_the_commission_payment_password));
                break;
            case ConstantValue.RESET_THE_COMMISSION_PAYMENT_PASSWORD:
                initTitle(mContext.getString(R.string.change_the_commission_payment_password));
                break;
        }
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        if (userInfo != null) {
            mobilNumber = userInfo.getMobile();
            tvThePhoneNumberIsBound.setText(PhoneUtils.mobilNumber(mobilNumber));
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_Get_the_verification_code:
                getVerificationCode();
                countdownTime();
                break;
            case R.id.tv_validation:
                validationCode();
                break;
        }
    }

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
        if (TextUtils.isEmpty(mobilNumber)) {
            showToast(mContext.getString(R.string.please_edit_phone_number));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_VERIFICATION_CODE_VALUE);
        params.put(ApiParam.MOBILE_KEY, mobilNumber);
        params.put(ApiParam.EVENT_KEY, ApiParam.PAYPASS);
        String paramsJsonObject = JsonUtils.toJson(params);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.validationCodeOrResetPassword(IResetCommissionPaymentView.GET_VERIFICATION_CODE, userToken, paramsJsonObject);
        showDialog("");
    }

    /**
     * 验证手机验证码
     */
    private void validationCode() {
        validationCodeNumber = editPleaseEditVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(validationCodeNumber)) {
            showToast(mContext.getString(R.string.please_edit_verification_code));
            return;
        }
        Map<String, String> validationCodeParam = new HashMap<>();
        validationCodeParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.VERIFICATION_CODE_URL);
        validationCodeParam.put(ApiParam.MOBILE_KEY, mobilNumber);
        validationCodeParam.put(ApiParam.EVENT_KEY, ApiParam.PAYPASS);
        validationCodeParam.put(ApiParam.CAPTCHA, validationCodeNumber);
        String validationCodeParamJson = JsonUtils.toJson(validationCodeParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.validationCodeOrResetPassword(IResetCommissionPaymentView.VALIDATION_CODE, userToken, validationCodeParamJson);
        showDialog("");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }

    @Override
    public void validationCodeOrResetPasswordSuccess(int requestType, boolean isSuccess, String message) {
        dismissDialog();
        showToast(message);
        switch (requestType) {
            case IResetCommissionPaymentView.GET_VERIFICATION_CODE: //获取验证码
                finish();
                break;
            case IResetCommissionPaymentView.VALIDATION_CODE: //验证验证码
                if (isSuccess) {
                    SetCommissionPaymentPasswordActivity.start(mContext, thatClassType);
                    finish();
                }
                break;

        }

    }

    @Override
    public void onNewWorkException(String message) {
        dismissDialog();
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        dismissDialog();
        showToast(message);
    }


}
