package com.goulala.xiayun.mycenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.goulala.xiayun.R;

/**
 * author      : Z_B
 * date       : 2018/9/12
 * function  : 操作结果的dialog ，用于操作成功或者失败的返回显示
 */
public class OperatingResultDialog extends Dialog {

    private Context mContext;
    private Window window;
    private Display display;
    private ImageView ivResultImageView;
    private TextView tvResultTitle;

    public OperatingResultDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        View dialogView = View.inflate(context, R.layout.include_operating_result_dialog_layout, null);
        ivResultImageView = dialogView.findViewById(R.id.iv_result_imageView);
        tvResultTitle = dialogView.findViewById(R.id.tv_result_title);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        window = getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setWidth((int) (display.getWidth() * 0.7));
        OperatingResultDialog.this.show();
    }


    //设置宽度
    public OperatingResultDialog setWidth(int width) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        window.setAttributes(lp);
        return this;
    }

    //设置结果的图片
    public void setResultImageView(int imageViewId) {
        if (imageViewId != 0) {
            ivResultImageView.setImageResource(imageViewId);
        }
    }

    //设置结果的内容
    public void setResultTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvResultTitle.setText(title);
        }
    }

}
