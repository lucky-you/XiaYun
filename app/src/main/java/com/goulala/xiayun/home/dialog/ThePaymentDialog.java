package com.goulala.xiayun.home.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.utils.StringUtils;
import com.goulala.xiayun.common.utils.ToastUtils;

/**
 * author      : Z_B
 * date       : 2018/9/3
 * function  :  支付的dialog
 */
public class ThePaymentDialog extends Dialog implements View.OnClickListener {

    private RelativeLayout rlCloseDialog;
    private TextView tvTotalMoney;
    private RadioButton rbCommissionDeduction;
    private RadioButton rbTheAliPayPay;
    private RadioButton rbTheWeChatPay;
    private TextView tvConfirmThePayment;
    private Context mContext;

    public OnPayResultListener onPayResultListener;
    public static final int PAY_OF_BALANCE_TYPE = 1;
    public static final int PAY_OF_ALIPAY_TYPE = 2;
    public static final int PAY_OF_WECHAT_TYPE = 3;
    private int payType = 0;

    public ThePaymentDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View popupView = View.inflate(context, R.layout.include_the_payment_dialog_layout, null);
        initViews(popupView);
        window.setContentView(popupView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.bottomInWindowAnim;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        ThePaymentDialog.this.setCancelable(false);
        ThePaymentDialog.this.show();

    }

    private void initViews(View popupView) {
        rlCloseDialog = popupView.findViewById(R.id.rl_close_dialog);
        tvTotalMoney = popupView.findViewById(R.id.tv_total_money);
        rbCommissionDeduction = popupView.findViewById(R.id.rb_Commission_deduction);
        rbTheAliPayPay = popupView.findViewById(R.id.rb_the_alipay_pay);
        rbTheWeChatPay = popupView.findViewById(R.id.rb_the_WeChat_pay);
        tvConfirmThePayment = popupView.findViewById(R.id.tv_Confirm_the_payment);
        rlCloseDialog.setOnClickListener(this);
        rbCommissionDeduction.setOnClickListener(this);
        rbTheAliPayPay.setOnClickListener(this);
        rbTheWeChatPay.setOnClickListener(this);
        tvConfirmThePayment.setOnClickListener(this);


    }

    public void setTvTotalMoney(String totalMoney) {
        if (!TextUtils.isEmpty(totalMoney)) {
            tvTotalMoney.setText(mContext.getString(R.string.the_price, totalMoney));
        }
    }

    /**
     * 显示用余额
     */
    @SuppressLint("StringFormatInvalid")
    public void setBalancePayMoney(String money) {
        if (!TextUtils.isEmpty(money)) {
            String showText = mContext.getString(R.string.Commission_deduction, money);
            SpannableString textPrice = StringUtils.getTheCpecifiedTextColor(showText, 4, showText.length(), mContext.getResources().getColor(R.color.color_6f6f6f_black));
            rbCommissionDeduction.setText(textPrice);
        }
    }

    /**
     * 余额不足
     */
    public void setLackOfBalance() {
        String showText = mContext.getString(R.string.Commission_deduction_lack_of_balance);
        SpannableString textPrice = StringUtils.getTheCpecifiedTextColor(showText, 4, showText.length(), mContext.getResources().getColor(R.color.color_e53d3d));
        rbCommissionDeduction.setText(textPrice);
        rbCommissionDeduction.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_close_dialog:
                if (onPayResultListener != null) {
                    onPayResultListener.closePayDialog();
                }
                break;
            case R.id.rb_Commission_deduction:
                if (rbCommissionDeduction.isChecked()) {
                    payType = PAY_OF_BALANCE_TYPE;
                    tvConfirmThePayment.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                }
                break;
            case R.id.rb_the_alipay_pay:
                if (rbTheAliPayPay.isChecked()) {
                    payType = PAY_OF_ALIPAY_TYPE;
                    tvConfirmThePayment.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                }
                break;
            case R.id.rb_the_WeChat_pay:
                if (rbTheWeChatPay.isChecked()) {
                    payType = PAY_OF_WECHAT_TYPE;
                    tvConfirmThePayment.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                }
                break;
            case R.id.tv_Confirm_the_payment:
                if (payType != 0) {
                    if (onPayResultListener != null) {
                        onPayResultListener.chooseTypeToPay(payType);
                    }
                } else {
                    ToastUtils.showToast(mContext.getString(R.string.Please_select_payment_method));
                }
                break;
        }
    }

    public interface OnPayResultListener {

        /**
         * 选择支付方式
         */
        void chooseTypeToPay(int type);

        /**
         * 关闭了支付的窗口
         */
        void closePayDialog();
    }

    public void setOnPayResultListener(OnPayResultListener onPayResultListener) {
        this.onPayResultListener = onPayResultListener;
    }


}
