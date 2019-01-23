package com.goulala.xiayun.common.utils;

/**
 * author      : Z_B
 * date       : 2018/11/12
 * function  :  防止按钮被快速点击
 */
public final class ButtonClickUtils {

    private static long lastClickTime = 0;//上次点击的时间
    private static int spaceTime = 500;//时间间隔

    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击
        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }
        lastClickTime = currentTime;
        return isAllowClick;
    }

}
