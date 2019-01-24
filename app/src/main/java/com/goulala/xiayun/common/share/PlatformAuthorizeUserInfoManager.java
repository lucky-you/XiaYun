package com.goulala.xiayun.common.share;

import android.app.Activity;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 第三方授权登录
 */
public class PlatformAuthorizeUserInfoManager {

    private PlatformActionListener myPlatformActionListener = null;

    public PlatformAuthorizeUserInfoManager(PlatformActionListener myPlatformActionListener) {
        this.myPlatformActionListener = myPlatformActionListener;
    }

    /**
     * 微信
     */
    public void WeiXinAuthorize() {
        Platform weiXin = ShareSDK.getPlatform(Wechat.NAME);
        doAuthorize(weiXin);
    }

    /**
     * 新浪微博
     */
    public void sinaAuthorize() {
        Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
        doAuthorize(sina);
    }

    /**
     * QQ空间
     */
    public void qqZoneAuthorize() {
        Platform qqZone = ShareSDK.getPlatform(QZone.NAME);
        doAuthorize(qqZone);
    }

    /**
     * 微信朋友圈
     */
    public void whatMomentsAuthorize() {
        Platform moments = ShareSDK.getPlatform(WechatMoments.NAME);
        doAuthorize(moments);
    }


    /**
     * QQ
     */
    public void qqShareAuthorize() {
        Platform qqShare = ShareSDK.getPlatform(QQ.NAME);
        doAuthorize(qqShare);
    }


    /**
     * 授权的代码
     */
    public void doAuthorize(Platform platform) {
        if (platform != null) {
            platform.setPlatformActionListener(myPlatformActionListener);
            if (platform.isAuthValid()) {
                platform.removeAccount(true);
                return;
            }
            platform.SSOSetting(true);
            platform.authorize();
        }
    }

    /**
     * 授权的代码
     */
    public void doAuthorize(Platform platform, PlatformActionListener listener) {
        if (platform != null) {
            platform.setPlatformActionListener(listener);
            platform.removeAccount(true);
            platform.authorize();
        }
    }

    /**
     * 用户信息的代码
     */
    public void doUserInfo(Platform platform) {
        if (platform != null) {
            platform.showUser(null);
        }
    }

    /**
     * 用户信息的代码
     */
    public void doUserInfo(Platform platform, PlatformActionListener listener) {
        if (platform != null) {
            platform.setPlatformActionListener(listener);
            platform.showUser(null);
        }
    }

    /**
     * @param platform  平台名称
     * @param shareType 分享类型
     *                  用户信息的代码
     */
    public void doUserInfo(Platform platform, String account) {
        if (platform != null) {
            platform.showUser(account);
        }
    }

    /**
     * @param platform  平台名称
     * @param shareType 分享类型
     *                  用户信息的代码
     */
    public void doUserInfo(Platform platform, String account, PlatformActionListener listener) {
        if (platform != null) {
            platform.setPlatformActionListener(listener);
            platform.showUser(account);
        }
    }


}
