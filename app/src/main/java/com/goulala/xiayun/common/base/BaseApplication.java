package com.goulala.xiayun.common.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.goulala.xiayun.common.utils.FileUtils;
import com.goulala.xiayun.common.utils.SPUtils;


/**
 * application的基类
 */
public class BaseApplication extends Application {

    protected static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SPUtils.init(this);
        FileUtils.init("xiayun");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static BaseApplication getInstance() {
        return instance;
    }


    /**
     * 获取当前进程的名称
     */
    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
