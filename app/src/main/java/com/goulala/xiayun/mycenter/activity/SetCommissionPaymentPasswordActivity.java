package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.utils.JsonUtils;
import com.goulala.view.LoadDialog;
import com.goulala.xiayun.Basemvp.BaseMVPActivity;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.ApiParam;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.mycenter.IPresenter.SetCommissionPaymentPasswordPresenter;
import com.goulala.xiayun.mycenter.contact.SetCommissionPaymentPasswordContact;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置佣金的支付密码 , 重置佣金支付密码 ,第二步，通过获取到的验证码来设置或者重置密码
 * 公用的activity
 */
public class SetCommissionPaymentPasswordActivity extends BaseMVPActivity<SetCommissionPaymentPasswordContact.presenter> implements SetCommissionPaymentPasswordContact.view {

    private TextView tvSetThePassword;
    private EditText editSetThePassword;
    private ImageView ivSetThePasswordImage;
    private RelativeLayout rlSetThePassword;
    private TextView tvInputAgain;
    private EditText editInputAgain;
    private ImageView ivInputAgainImage;
    private RelativeLayout rlInputAgain;
    private TextView tvForgetTheOriginalPassword;
    private TextView tvDetermine;
    private int thatClassType;
    private boolean isShowPassword = true;
    private boolean isShowPasswordAgain = true;

    public static void start(Context context, int classType) {
        Intent intent = new Intent(context, SetCommissionPaymentPasswordActivity.class);
        intent.putExtra(ConstantValue.TITLE, classType);
        context.startActivity(intent);
    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_set_commission_payment_password);
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        tvSetThePassword = get(R.id.tv_Set_the_password);
        editSetThePassword = get(R.id.edit_Set_the_password);
        ivSetThePasswordImage = get(R.id.iv_Set_the_password_image);
        rlSetThePassword = get(R.id.rl_Set_the_password);
        rlSetThePassword.setOnClickListener(this);
        tvInputAgain = get(R.id.tv_Input_again);
        editInputAgain = get(R.id.edit_Input_again);
        ivInputAgainImage = get(R.id.iv_Input_again_image);
        rlInputAgain = get(R.id.rl_Input_again);
        rlInputAgain.setOnClickListener(this);
        tvForgetTheOriginalPassword = get(R.id.tv_Forget_the_original_password);
        tvForgetTheOriginalPassword.setOnClickListener(this);
        tvDetermine = get(R.id.tv_determine);
        tvDetermine.setOnClickListener(this);

    }

    @Override
    protected void bindViews() {
        thatClassType = getIntent().getIntExtra(ConstantValue.TITLE, -1);
        if (ConstantValue.SET_THE_COMMISSION_PAYMENT_PASSWORD == thatClassType) {
            initTitle(mContext.getString(R.string.Set_the_commission_payment_password));
        } else {
            initTitle(mContext.getString(R.string.change_the_commission_payment_password));
        }

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_Set_the_password:
                //设置密码
                if (isShowPassword) {
                    // 显示密码
                    ivSetThePasswordImage.setImageResource(R.drawable.ic_eye_open);
                    editSetThePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editSetThePassword.setSelection(editSetThePassword.getText().toString().length());
                    isShowPassword = !isShowPassword;
                } else {
                    // 隐藏密码
                    ivSetThePasswordImage.setImageResource(R.drawable.ic_eye_close);
                    editSetThePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editSetThePassword.setSelection(editSetThePassword.getText().toString().length());
                    isShowPassword = !isShowPassword;
                }
                break;
            case R.id.rl_Input_again:
                //再次设置密码
                if (isShowPasswordAgain) {
                    // 显示密码
                    ivInputAgainImage.setImageResource(R.drawable.ic_eye_open);
                    editInputAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editInputAgain.setSelection(editInputAgain.getText().toString().length());
                    isShowPasswordAgain = !isShowPasswordAgain;
                } else {
                    // 隐藏密码
                    ivInputAgainImage.setImageResource(R.drawable.ic_eye_close);
                    editInputAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editInputAgain.setSelection(editInputAgain.getText().toString().length());
                    isShowPasswordAgain = !isShowPasswordAgain;
                }
                break;
            case R.id.tv_Forget_the_original_password:
                //忘记密码
                break;
            case R.id.tv_determine:
                String passwordOne = editSetThePassword.getText().toString().trim();
                String passwordTwo = editInputAgain.getText().toString().trim();
                if (TextUtils.isEmpty(passwordOne)) {
                    showToast(mContext.getString(R.string.Please_enter_password));
                    return;
                }
                if (TextUtils.isEmpty(passwordTwo)) {
                    showToast(mContext.getString(R.string.Please_enter_your_password_again));
                    return;
                }
                if (!TextUtils.equals(passwordOne, passwordTwo)) {
                    showToast(mContext.getString(R.string.The_passwords_do_not_match));
                    return;
                }
                determineResetPassword(passwordTwo);
                break;
        }
    }

    /**
     * 确定
     */
    private void determineResetPassword(String password) {
        Map<String, String> resetParam = new HashMap<>();
        resetParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.SET_OR_RESET_PASSWORD_URL);
        resetParam.put(ApiParam.PAYPASS, password);
        String resetParamJson = JsonUtils.toJson(resetParam);
        Logger.w("xy", resetParamJson + "userToken=" + userToken);
        presenter.resetPassword(mContext, userToken, resetParamJson);


    }

    @Override
    public SetCommissionPaymentPasswordContact.presenter createPresenter() {
        return new SetCommissionPaymentPasswordPresenter(this);
    }

    @Override
    public void resetPasswordSuccess(boolean isSuccess, String message) {
        showToast(message);
        if (isSuccess) {
            finish();
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
