package com.goulala.xiayun.home.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.utils.SizeUtils;
import com.goulala.xiayun.common.utils.StringUtils;

/**
 * author : Z_B
 * date : 2018/8/15
 * function : 分享获得佣金
 */
public class ShareCommissionDialog extends Dialog {
    private Context mContext;
    private TextView tvShareFeeMin, tvShareFeeMax;

    public ShareCommissionDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View popupView = View.inflate(context, R.layout.include_share_commission_layout, null);
        initViews(popupView);
        window.setContentView(popupView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = SizeUtils.dp2px(200);
        lp.windowAnimations = R.style.bottomInWindowAnim;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        ShareCommissionDialog.this.show();
    }

    private void initViews(View popupView) {
        tvShareFeeMin = popupView.findViewById(R.id.tv_share_fee_min);
        tvShareFeeMax = popupView.findViewById(R.id.tv_share_fee_max);
    }

    @SuppressLint("StringFormatInvalid")
    public void setShareFeePrice(double minShareFee, double maxShareFee) {
        String minShareFeeText = mContext.getString(R.string.Commission_differences_one, String.valueOf(minShareFee));
        String maxShareFeeText = mContext.getString(R.string.Commission_differences_two, String.valueOf(maxShareFee));

        SpannableString textStringOne = StringUtils.getTheCpecifiedTextColor(minShareFeeText, 15, minShareFeeText.length(), mContext.getResources().getColor(R.color.color_e53d3d));
        SpannableString textStringTwo = StringUtils.getTheCpecifiedTextColor(maxShareFeeText, 25, maxShareFeeText.length(), mContext.getResources().getColor(R.color.color_e53d3d));

        tvShareFeeMin.setText(textStringOne);
        tvShareFeeMax.setText(textStringTwo);
    }

}
