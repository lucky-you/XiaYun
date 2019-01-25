package com.goulala.xiayun.common.share;


import cn.sharesdk.framework.Platform;

/**
 * author      : Z_B
 * date       : 2019/1/25
 * function  : 第一次授权之后，获取用户信息的回调
 */
public interface SharePlatformAuthorizeUserInfoListener {

    /**
     * 获取用户信息 ,所有信息都可以通过platform去获取
     */
    void getAuthorizeUserSuccess(Platform platform);


}
