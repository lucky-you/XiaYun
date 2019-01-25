package com.goulala.xiayun.common.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.goulala.xiayun.BuildConfig;
import com.goulala.xiayun.common.db.DBManager;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.utils.CrashHandler;
import com.goulala.xiayun.common.utils.FileUtils;
import com.goulala.xiayun.common.utils.SPUtils;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * application的基类
 */
public class BaseApplication extends Application {

    protected static BaseApplication instance;
    private UserInfo userInfo;
    private RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SPUtils.init(this);
        FileUtils.init("xiayun");
        DBManager.initDao();
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(instance));
        initImageLoader();
        initLeakCanary();
        MobSDK.init(this);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(instance);
        config.memoryCacheExtraOptions(480, 800);
        config.diskCacheExtraOptions(720, 1280, null);
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        if (BuildConfig.DEBUG) {
            config.writeDebugLogs(); // Remove for release app
        }
        ImageLoader.getInstance().init(config.build());
    }
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = RefWatcher.DISABLED;
            return;
        }
        mRefWatcher = LeakCanary.install(this);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static BaseApplication getInstance() {
        return instance;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
