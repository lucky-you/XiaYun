package com.goulala.xiayun.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : Z_B
 * @date : 2018/8/13
 * @function : 会员签到的dialog
 */
public class MemberSignInDialog extends Dialog implements View.OnClickListener {

    private RelativeLayout rlCloseDialog;
    private TextView tvCheckInSuccessfullyGetMoney;
    private TextView tvThreeDaysInRowPlusBonus;
    private TextView tvShareImmediately;
    private LinearLayout llShareWeChatReceiveTheAboveReward;
    private CircleImageView civGiftImageVIew;
    private Context mContext;
    private Window window;
    private Display display;

    public MemberSignInDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        View dialogView = View.inflate(context, R.layout.include_member_sign_dialog_layout, null);
        initViews(dialogView);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        window = getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setWidth((int) (display.getWidth() * 0.9));
        MemberSignInDialog.this.show();
    }

    private void initViews(View dialogView) {
        rlCloseDialog = dialogView.findViewById(R.id.rl_close_dialog);
        rlCloseDialog.setOnClickListener(this);
        tvCheckInSuccessfullyGetMoney = dialogView.findViewById(R.id.tv_Check_in_successfully_get_money);
        tvThreeDaysInRowPlusBonus = dialogView.findViewById(R.id.tv_Three_days_in_row_plus_bonus);
        tvShareImmediately = dialogView.findViewById(R.id.tv_Share_immediately);
        tvShareImmediately.setOnClickListener(this);
        llShareWeChatReceiveTheAboveReward = dialogView.findViewById(R.id.ll_Share_WeChat_receive_the_above_reward);
        civGiftImageVIew = dialogView.findViewById(R.id.civ_gift_imageVIew);

    }

    //设置宽度
    public MemberSignInDialog setWidth(int width) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        window.setAttributes(lp);
        return this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_close_dialog:
                MemberSignInDialog.this.dismiss();
                break;
            case R.id.tv_Share_immediately:
                break;
        }
    }
}
