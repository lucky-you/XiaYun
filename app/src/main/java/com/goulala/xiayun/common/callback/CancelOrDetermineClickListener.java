package com.goulala.xiayun.common.callback;

import android.view.View;

/**
 * author      : Z_B
 * date       : 2018/9/13
 * function  : 取消或者确定的回调
 */
public interface CancelOrDetermineClickListener {


    /**
     * 取消
     */
     void cancelClick();

    /**
     * 确定
     */
    void determineClick(View view);
}
