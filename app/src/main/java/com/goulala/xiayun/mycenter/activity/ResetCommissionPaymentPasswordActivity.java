package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.goulala.utils.CommonUtil;
import com.goulala.utils.JsonUtils;
import com.goulala.view.LoadDialog;
import com.goulala.xiayun.Basemvp.BaseMVPActivity;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.ApiParam;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.mycenter.IPresenter.ResetCommissionPaymentPasswordPresenter;
import com.goulala.xiayun.mycenter.contact.ResetCommissionPaymentPasswordContact;
import com.orhanobut.logger.Logger;

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
public class ResetCommissionPaymentPasswordActivity extends BaseMVPActivity<ResetCommissionPaymentPasswordContact.presenter> implements ResetCommissionPaymentPasswordContact.view {


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
    protected void loadViewLayout() {
        setContentView(R.layout.activity_reset_commission_payment_password);
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        tvThePhoneNumberIsBound = get(R.id.tv_The_phone_number_is_bound);
        editPleaseEditVerificationCode = get(R.id.edit_please_edit_verification_code);
        tvGetTheVerificationCode = get(R.id.tv_Get_the_verification_code);
        tvGetTheVerificationCode.setOnClickListener(this);
        tvValidation = get(R.id.tv_validation);
        tvValidation.setOnClickListener(this);


    }

    @Override
    protected void bindViews() {
        thatClassType = getIntent().getIntExtra(ConstantValue.TITLE, -1);
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
    protected void processLogic(Bundle savedInstanceState) {
        if (userInfo != null) {
            mobilNumber = userInfo.getMobile();
            tvThePhoneNumberIsBound.setText(CommonUtil.mobilNumber(mobilNumber));
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
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
        if (!TextUtils.isEmpty(userToken)) {
            presenter.getVerificationCode(mContext, thatClassType, ResetCommissionPaymentPasswordContact.GET_VERIFICATION_CODE, userToken, paramsJsonObject);
        }
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
        Logger.w("xy", validationCodeParamJson + "token=" + userToken);
        if (!TextUtils.isEmpty(userToken)) {
            presenter.validationCode(mContext, thatClassType, ResetCommissionPaymentPasswordContact.VALIDATION_CODE, userToken, validationCodeParamJson);
        }
    }


    @Override
    public ResetCommissionPaymentPasswordContact.presenter createPresenter() {
        return new ResetCommissionPaymentPasswordPresenter(this);
    }

    @Override
    public void getVerificationCodeSuccess(String code) {

    }

    @Override
    public void validationCodeSuccess(boolean isSuccess, String message) {
        if (isSuccess) {
            SetCommissionPaymentPasswordActivity.start(mContext, thatClassType);
            finish();
        } else {
            showToast(message);
        }
    }

    @Override
    public void showLoadingDialog(String message) {
        LoadDialog.show(mContext, message);
    }

    @Override
    public void dismissLoadingDialog() {
        LoadDialog.dismiss(mContext);

    }

    @Override
    public void onRequestFailure(String error) {
        showToast(error);

    }
}
