package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.cache.DataCleanManager;
import com.goulala.callback.CancelOrDetermineClickListener;
import com.goulala.utils.JsonUtils;
import com.goulala.view.LoadDialog;
import com.goulala.xiayun.Basemvp.BaseMVPActivity;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.ApiParam;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.activity.WebDetailsActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.mycenter.IPresenter.SetUpThePresenter;
import com.goulala.xiayun.mycenter.contact.SetUpTheContact;
import com.goulala.xiayun.mycenter.utils.AlertDialogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置
 */
public class SetUpTheActivity extends BaseMVPActivity<SetUpTheContact.presenter> implements SetUpTheContact.view {

    private RelativeLayout rlAboutUs;
    private TextView tvClearTheCache;
    private TextView tvIsSetCommissionPaymentPassword;
    private TextView tvLogOut;
    private boolean isSetPassword; //是否设置佣金支付密码

    public static void start(Context context) {
        Intent intent = new Intent(context, SetUpTheActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_set_up_the);
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        rlAboutUs = get(R.id.rl_About_us);
        rlAboutUs.setOnClickListener(this);
        tvClearTheCache = get(R.id.tv_Clear_the_cache);
        tvClearTheCache.setOnClickListener(this);
        tvIsSetCommissionPaymentPassword = get(R.id.tv_is_set_Commission_payment_password);
        tvIsSetCommissionPaymentPassword.setOnClickListener(this);
        tvLogOut = get(R.id.tv_Log_out);
        tvLogOut.setOnClickListener(this);

    }

    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.Set_up_the));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        tvClearTheCache.setText(DataCleanManager.getTotalCacheSize(mContext));
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPassword();
    }

    /**
     * 检查是否设置了支付密码
     */
    private void checkPassword() {
        Map<String, String> checkParam = new HashMap<>();
        checkParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.DOES_PASSWORD_EXIST_URL);
        String checkParamJson = JsonUtils.toJson(checkParam);
        if (!TextUtils.isEmpty(userToken)) {
            presenter.checkPassword(mContext, userToken, checkParamJson);
        }
    }


    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_About_us:
                WebDetailsActivity.start(mContext, ConstantValue.CLASS_TYPE_OF_ABOUT_US);
                break;
            case R.id.tv_Clear_the_cache:
                DataCleanManager.clearAllCache(mContext);
                tvClearTheCache.setText("0KB");
                showToast(mContext.getString(R.string.Please_note_that_the_cache_was_successful));
                break;
            case R.id.tv_is_set_Commission_payment_password:
                if (isSetPassword) {
                    ResetCommissionPaymentPasswordActivity.start(mContext, ConstantValue.RESET_THE_COMMISSION_PAYMENT_PASSWORD);
                } else {
                    ResetCommissionPaymentPasswordActivity.start(mContext, ConstantValue.SET_THE_COMMISSION_PAYMENT_PASSWORD);
                }
                break;
            case R.id.tv_Log_out:
                showLogOutDialog();
                break;
        }
    }

    private void showLogOutDialog() {
        String showTitle = mContext.getString(R.string.Are_you_sure_you_want_to_exit_your_current_account);
        AlertDialogUtils.showCustomerDialog(mContext, showTitle, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                UserUtils.loginOut(mContext);
                LoginActivity.start(mContext);
                finish();
            }
        });

    }

    @Override
    public SetUpTheContact.presenter createPresenter() {
        return new SetUpThePresenter(this);
    }

    @Override
    public void isSettingPassword(boolean isSetPassword) {
        this.isSetPassword = isSetPassword;
        if (isSetPassword) {
            tvIsSetCommissionPaymentPassword.setText(mContext.getString(R.string.Has_been_set));
        } else {
            tvIsSetCommissionPaymentPassword.setText(mContext.getString(R.string.Has_been_not_set));
            tvIsSetCommissionPaymentPassword.setTextColor(mContext.getResources().getColor(R.color.color_e53d3d));
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
