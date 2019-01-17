package com.goulala.xiayun.common.mvp;

/**
 * view的基类
 */
public interface IBaseView {


    /**
     * 网络异常
     *
     * @param message 错误信息
     */
    void onNewWorkException(String message);

    /**
     * 请求数据失败
     *
     * @param resultCode 错误code
     * @param message    错误信息
     */
    void onRequestFailure(int resultCode, String message);
}
