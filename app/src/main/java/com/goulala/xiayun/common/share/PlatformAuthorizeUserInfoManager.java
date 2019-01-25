package com.goulala.xiayun.common.share;

import android.util.Log;

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

    private PlatformActionListener platformActionListener; //首次授权的回调---》回调在子线程
    private SharePlatformAuthorizeUserInfoListener sharePlatformAuthorizeUserInfoListener; //首次授权之后，获取用户信息的回调，回调在主线程

    public PlatformAuthorizeUserInfoManager(PlatformActionListener platformActionListener,
                                            SharePlatformAuthorizeUserInfoListener sharePlatformAuthorizeUserInfoListener) {
        this.platformActionListener = platformActionListener;
        this.sharePlatformAuthorizeUserInfoListener = sharePlatformAuthorizeUserInfoListener;
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
            platform.setPlatformActionListener(platformActionListener);
            if (platform.isAuthValid()) {
//                platform.removeAccount(true);
                Log.d("xy", "已经授权过了");
                //已经授权过了,直接在这里获取用户信息
                sharePlatformAuthorizeUserInfoListener.getAuthorizeUserSuccess(platform);
                return;
            }
            Log.d("xy", "第一次授权");
            platform.SSOSetting(true);
            platform.authorize();  //执行到了authorize，，才会走回调的
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
     * @param platform 平台名称
     *                 用户信息的代码
     */
    public void doUserInfo(Platform platform, String account) {
        if (platform != null) {
            platform.showUser(account);
        }
    }

    /**
     * @param platform 平台名称
     *                 用户信息的代码
     */
    public void doUserInfo(Platform platform, String account, PlatformActionListener listener) {
        if (platform != null) {
            platform.setPlatformActionListener(listener);
            platform.showUser(account);
        }
    }


}
