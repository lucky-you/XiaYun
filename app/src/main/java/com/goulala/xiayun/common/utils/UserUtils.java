package com.goulala.xiayun.common.utils;

import android.content.Context;

import com.goulala.xiayun.common.base.BaseApplication;
import com.goulala.xiayun.common.lib.ActivityManager;
import com.goulala.xiayun.common.model.UserInfo;


public class UserUtils {
    /**
     * 登录成功
     */
    public static void loginIn(UserInfo user) {
        SPUtils.set(ConstantValue.SP_TOKEN, user.getToken());
        SPUtils.set(ConstantValue.USER_NAME, user.getUsername());
        SPUtils.set(ConstantValue.NICK_NAME, user.getNickname());
        SPUtils.set(ConstantValue.USER_ID, String.valueOf(user.getId()));
        BaseApplication.getInstance().setUserInfo(user);

    }

    /**
     * 退出登录
     */
    public static void loginOut(Context context) {
        SPUtils.set(ConstantValue.SP_TOKEN, "");
        BaseApplication.getInstance().setUserInfo(null);
        ActivityManager.getAppInstance().AppExit(context);
    }

    /**
     * 获取token
     */
    public static String userToken() {
        return (String) SPUtils.get(ConstantValue.SP_TOKEN, "");
    }


    /**
     * 用户名
     */
    public static String getUserName() {
        return (String) SPUtils.get(ConstantValue.USER_NAME, "");
    }

    /**
     * 用户昵称
     */
    public static String getNickName() {
        return (String) SPUtils.get(ConstantValue.NICK_NAME, "");
    }

    /**
     * 用户id
     */
    public static String getUserID() {
        return (String) SPUtils.get(ConstantValue.USER_ID, "");
    }
}
