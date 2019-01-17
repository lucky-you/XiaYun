package com.goulala.xiayun.common.utils;

import android.widget.Toast;

import com.goulala.xiayun.common.base.BaseApplication;


public final class ToastUtils {

    private static Toast mToast;

     /**
     * 显示Toast
     */
    public static void showToast( CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

}
