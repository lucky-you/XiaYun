package com.goulala.xiayun.common.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;


/**
 * @author : Z_B
 * @date : 2018/8/17
 * @function : 自定义的dialog
 */
public class CustomDialog extends Dialog {
    private Context mContext;
    private Window window;
    private Display display;
    //控件
    private TextView tvContent;
    private Button btnLeft;
    private Button btnRight;
    private CancelOrDetermineClickListener cancelOrDetermineClickListener;

    public CustomDialog(Context context) {
        super(context, R.style.customDialogStyle);
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        mContext = context;
        View dialogView = View.inflate(context, R.layout.include_custom_dialog_layout, null);
        initViews(dialogView);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        window = getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setWidth((int) (display.getWidth() * 0.85));
        CustomDialog.this.show();
    }

    private void initViews(View dialogView) {
        tvContent = dialogView.findViewById(R.id.tvContent);
        btnLeft = dialogView.findViewById(R.id.btnLeft);
        btnRight = dialogView.findViewById(R.id.btnRight);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelOrDetermineClickListener != null) {
                    cancelOrDetermineClickListener.cancelClick();
                }
                CustomDialog.this.dismiss();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelOrDetermineClickListener != null) {
                    cancelOrDetermineClickListener.determineClick(view);
                }
                CustomDialog.this.dismiss();
            }
        });
    }

    /**
     * 设置提示的信息
     */
    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
    }

    /**
     * 设置左侧的文本
     */
    public void setLeftCancel(String leftCancel) {
        if (!TextUtils.isEmpty(leftCancel)) {
            btnLeft.setText(leftCancel);
        }
    }

    /**
     * 设置右侧的文本
     */
    public void setRightSure(String rightSure) {
        if (!TextUtils.isEmpty(rightSure)) {
            btnRight.setText(rightSure);
        }
    }

    public void setCancelOrDetermineClickListener(CancelOrDetermineClickListener cancelOrDetermineClickListener) {
        this.cancelOrDetermineClickListener = cancelOrDetermineClickListener;
    }


    /**
     * 设置宽度
     */
    public CustomDialog setWidth(int width) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        window.setAttributes(lp);
        return this;
    }
}
