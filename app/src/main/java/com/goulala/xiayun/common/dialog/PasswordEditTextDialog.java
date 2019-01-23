package com.goulala.xiayun.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.callback.CancelPaymentClickListener;
import com.goulala.xiayun.common.utils.ToastUtils;
import com.goulala.xiayun.common.view.PasswordEditText;


/**
 * Created by ZhouBin on 2017/8/2.
 * Effect: 密码输入的dialog(使用系统的软键盘)
 */

public class PasswordEditTextDialog extends Dialog {
    TextView tvDialogTitle;
    PasswordEditText passwordEditText;
    TextView tvDialogCenter;
    TextView tvDialogDetermine;
    private Context mContext;
    private Window window;
    private Display display;
    private PasswordEditText.PasswordFullListener passwordFullListener;
    private CancelPaymentClickListener cancelPaymentClickListener;


    public PasswordEditTextDialog(Context context) {
        super(context);
        mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        View passwordView = View.inflate(context, R.layout.include_edittext_password_dialog_view, null);
        initViews(passwordView);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(passwordView);
        window = getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setWidth((int) (display.getWidth() * 0.9));
        PasswordEditTextDialog.this.setCancelable(false);
        PasswordEditTextDialog.this.show();

    }

    public void setPasswordFullListener(PasswordEditText.PasswordFullListener passwordFullListener) {
        this.passwordFullListener = passwordFullListener;
    }

    public void setCancelPaymentClickListener(CancelPaymentClickListener cancelPaymentClickListener) {
        this.cancelPaymentClickListener = cancelPaymentClickListener;
    }

    //初始化控件
    private void initViews(View passwordView) {
        tvDialogTitle = passwordView.findViewById(R.id.tv_dialog_title);
        passwordEditText = passwordView.findViewById(R.id.password_edit_text);
        tvDialogCenter = passwordView.findViewById(R.id.tv_center);
        tvDialogDetermine = passwordView.findViewById(R.id.tv_determine);
        tvDialogCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消了密码支付
                if (cancelPaymentClickListener != null) {
                    cancelPaymentClickListener.cancelPayment();
                }
                PasswordEditTextDialog.this.dismiss();

            }
        });

        tvDialogDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(password)) {
                    if (password.length() >= 6) {
                        if (passwordFullListener != null) {
                            passwordFullListener.passwordFull(password);
                        }
                        PasswordEditTextDialog.this.dismiss();
                    }
                } else {
                    ToastUtils.showToast(mContext.getString(R.string.Please_edit_the_password));
                }
            }
        });

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvDialogTitle.setText(title);
        }
    }

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    public PasswordEditTextDialog setWidth(int width) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        window.setAttributes(lp);
        return this;
    }


}
