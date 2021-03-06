package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.mycenter.model.PaymentDetailsBean;
import com.goulala.xiayun.mycenter.presenter.MyCommissionPresenter;
import com.goulala.xiayun.mycenter.view.IMyCommissionView;

import java.util.HashMap;
import java.util.Map;


/**
 * 提现的activity
 */
public class TheWithdrawalActivity extends BaseMvpActivity<MyCommissionPresenter> implements IMyCommissionView {

    private EditText editBackCardNumber;
    private TextView tvBackName;
    private RelativeLayout rlSelectBack;
    private EditText editPleaseNameOfCardholder;
    private EditText editPleaseEditWithdrawalAmount;
    private TextView tvSingleWithdrawalAmount;
    private TextView tvDetermineTheWithdrawal;
    private static final int SELECT_BACK_CARD_TYPE = 111;
    private String bankCardName;
    private String userBalance; //账户余额

    public static void start(Context context) {
        Intent intent = new Intent(context, TheWithdrawalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected MyCommissionPresenter createPresenter() {
        return new MyCommissionPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_the_withdrawal;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.withdrawal));
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        editBackCardNumber = get(R.id.edit_back_card_number);
        tvBackName = get(R.id.tv_back_name);
        rlSelectBack = get(R.id.rl_select_back);
        rlSelectBack.setOnClickListener(this);
        editPleaseNameOfCardholder = get(R.id.edit_please_Name_of_cardholder);
        editPleaseEditWithdrawalAmount = get(R.id.edit_please_edit_Withdrawal_amount);
        tvSingleWithdrawalAmount = get(R.id.tv_Single_withdrawal_amount);
        tvDetermineTheWithdrawal = get(R.id.tv_Determine_the_withdrawal);
        tvDetermineTheWithdrawal.setOnClickListener(this);
        tvSingleWithdrawalAmount.setText(mContext.getString(R.string.Single_withdrawal_amount, "50", "50"));
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAccountBalance();
    }

    /**
     * 获取账户余额
     */
    private void getAccountBalance() {
        Map<String, String> balanceParam = new HashMap<>();
        balanceParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.ACCOUNT_BALANCE_URL);
        String balanceParamJson = JsonUtils.toJson(balanceParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getUserBalance(userToken, balanceParamJson);

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_select_back:
                //选择银行
                Intent intent = new Intent(mContext, SelectBankCardActivity.class);
                startActivityForResult(intent, SELECT_BACK_CARD_TYPE);
                break;
            case R.id.tv_Determine_the_withdrawal:
                determineTheWithdrawal();
                break;
        }
    }

    /**
     * 确定提现
     */
    private void determineTheWithdrawal() {
        String bankCardNumber = editBackCardNumber.getText().toString().trim();
        if (TextUtils.isEmpty(bankCardNumber)) {
            showToast(mContext.getString(R.string.Please_enter_the_bank_card_number));
            return;
        }
        if (TextUtils.isEmpty(bankCardName)) {
            showToast(mContext.getString(R.string.Please_select_your_cash_card));
            return;
        }
        String userNameOfCardholder = editPleaseNameOfCardholder.getText().toString().trim();
        if (TextUtils.isEmpty(userNameOfCardholder)) {
            showToast(mContext.getString(R.string.Please_enter_the_name_of_the_cardholder));
            return;
        }
        String withdrawalAmount = editPleaseEditWithdrawalAmount.getText().toString().trim();
        if (TextUtils.isEmpty(withdrawalAmount)) {
            showToast(mContext.getString(R.string.Please_enter_the_withdrawal_amount));
            return;
        }
        if (TextUtils.isEmpty(userBalance)) {
            showToast(mContext.getString(R.string.Shall_not_withdraw));
            return;
        }
        //判断提现的金额有没有超过账户余额
        if (Double.parseDouble(withdrawalAmount) > 0) {
            if (Double.parseDouble(withdrawalAmount) > Double.parseDouble(userBalance)) {
                showToast(mContext.getString(R.string.The_withdrawal_amount_is_greater_than_the_account_balance));
                return;
            }
            //金额必须是50的整数倍
            if (Double.parseDouble(withdrawalAmount) % 50 != 0) {
                showToast(mContext.getString(R.string.A_single_withdrawal_must_be_an_integral_multiple_of_50));
                return;
            }
            //提现金额不能小于50
            if (Double.parseDouble(withdrawalAmount) < 50) {
                showToast(mContext.getString(R.string.The_withdrawal_amount_is_less_than_50));
                return;
            }
            Map<String, String> withdrawalAmountParam = new HashMap<>();
            withdrawalAmountParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.WITHDRAWAL_AMOUNT_URL);
            withdrawalAmountParam.put(ApiParam.BANK_NAME, bankCardName);
            withdrawalAmountParam.put(ApiParam.BANK_CARD, bankCardNumber);
            withdrawalAmountParam.put(ApiParam.CARD_NAME, userNameOfCardholder);
            withdrawalAmountParam.put(ApiParam.MONEY, withdrawalAmount);
            String withdrawalAmountParamJson = JsonUtils.toJson(withdrawalAmountParam);
            if (!TextUtils.isEmpty(userToken)) {
                mvpPresenter.withdrawalMoney(userToken, withdrawalAmountParamJson);
            }
        } else {
            showToast(mContext.getString(R.string.The_withdrawal_amount_cannot_be_0));
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_BACK_CARD_TYPE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    bankCardName = data.getStringExtra(ApiParam.BANK_NAME);
                    int bankCardId = data.getIntExtra(ApiParam.BANK_ID, -1);
                    tvBackName.setText(bankCardName);
                }
            }
        }
    }

    @Override
    public void getAccountBalanceSuccess(AccountBalance balance) {
        if (balance != null) {
            userBalance = balance.getAmount();
        }
    }

    @Override
    public void paymentDetailsSuccess(PaymentDetailsBean paymentDetailsBean) {

    }

    @Override
    public void withdrawalMoneySuccess(String message) {
        showToast(message);
        finish();
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
