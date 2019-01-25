package com.goulala.xiayun.common.share;

/**
 * author      : Z_B
 * date       : 2019/1/25
 * function  : 分享的回调
 */
public interface ShareCallBackListener {

    /**
     * 成功
     */
    void onSuccess();

    /**
     * 失败
     */
    void onFailed();

    /**
     * 取消
     */
    void onCancel();

}
