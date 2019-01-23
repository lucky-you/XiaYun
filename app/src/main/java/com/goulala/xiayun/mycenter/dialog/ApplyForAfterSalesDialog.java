package com.goulala.xiayun.mycenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.callback.OnRefundGoodOrMoneyClickListener;

/**
 * @author : Z_B
 * @date : 2018/8/28
 * @function : 申请售后的dialog
 */
public class ApplyForAfterSalesDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnRefundGoodOrMoneyClickListener onRefundGoodOrMoneyClickListener;

    public void setOnRefundGoodOrMoneyClickListener(OnRefundGoodOrMoneyClickListener onRefundGoodOrMoneyClickListener) {
        this.onRefundGoodOrMoneyClickListener = onRefundGoodOrMoneyClickListener;
    }

    public ApplyForAfterSalesDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View popupView = View.inflate(context, R.layout.include_apply_for_after_sales_dialog_layout, null);
        initViews(popupView);
        window.setContentView(popupView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.bottomInWindowAnim;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        ApplyForAfterSalesDialog.this.show();
    }

    private void initViews(View popupView) {
        popupView.findViewById(R.id.rl_Only_refund_money).setOnClickListener(this);
        popupView.findViewById(R.id.rl_Only_refund_good).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_Only_refund_money:
                if (onRefundGoodOrMoneyClickListener != null) {
                    onRefundGoodOrMoneyClickListener.onRefundMoneyClick();
                }
                break;
            case R.id.rl_Only_refund_good:
                if (onRefundGoodOrMoneyClickListener != null) {
                    onRefundGoodOrMoneyClickListener.onRefundGoodClick();
                }
                break;
        }
    }
}
