package com.goulala.xiayun.common.dialog;

import android.content.Context;

import com.goulala.xiayun.common.callback.CancelPaymentClickListener;
import com.goulala.xiayun.common.view.PasswordEditText;


/**
 * Created by ZhouBin on 2017/8/25.
 * Effect: 密码输入框的工具类
 */

public final class PasswordDialogUtils {

    public static PasswordDialogUtils alerDialogUtils;

    public PasswordDialogUtils() {
    }

    public static synchronized PasswordDialogUtils getInstance() {
        if (alerDialogUtils == null) {
            alerDialogUtils = new PasswordDialogUtils();
        }
        return alerDialogUtils;
    }


    /**
     * 全部都显示
     *
     * @param context              上下文
     * @param Title                标题
     * @param passwordFullListener 密码输入框的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, String Title,
                                                        final CancelPaymentClickListener cancelPaymentClickListener,
                                                        final PasswordEditText.PasswordFullListener passwordFullListener) {
        final PasswordEditTextDialog passwordEditTextDialog = new PasswordEditTextDialog(context);
        passwordEditTextDialog.setTitle(Title);
        passwordEditTextDialog.setCancelPaymentClickListener(new CancelPaymentClickListener() {
            @Override
            public void cancelPayment() {
                if (cancelPaymentClickListener != null) {
                    cancelPaymentClickListener.cancelPayment();
                }
                passwordEditTextDialog.dismiss();
            }
        });
        passwordEditTextDialog.setPasswordFullListener(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                if (passwordFullListener != null) {
                    passwordFullListener.passwordFull(password);
                }
                passwordEditTextDialog.dismiss();
            }
        });
        return passwordEditTextDialog;
    }

}
