package com.goulala.xiayun.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;


/**
 * author      : Z_B
 * date       : 2018/11/7
 * function  : 自定义的进度加载动画
 */
public class MProgressDialog extends Dialog {

    private Context context = null;
    private MProgressDialog progressDialog = null;

    public MProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public MProgressDialog createLoadingDialog(String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.include_progress_dialog_layout, null);
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        ImageView spaceshipImage = view.findViewById(R.id.img);
        TextView tipTextView = view.findViewById(R.id.tipTextView);
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.progress_dialog_anim);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);
        progressDialog = new MProgressDialog(context, R.style.myProgressDialog);// 创建自定义样式dialog
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);// 不可以用“返回键”取消
        progressDialog.setContentView(layout, new LinearLayout.LayoutParams(
                dip2px(context, 120), dip2px(context, 110)));
//        progressDialog.setContentView(layout, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
        return progressDialog;

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
