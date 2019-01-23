package com.goulala.xiayun.common.ipresenter;

import android.content.Context;

import com.goulala.xiayun.common.iview.IBindPhoneNumberView;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class BindPhoneNumberPresenter extends BasePresenter<IBindPhoneNumberView> {
    public BindPhoneNumberPresenter(IBindPhoneNumberView mvpView) {
        super(mvpView);
    }


    /**
     * 获取验证码
     */
    public void getCode(String token, String params) {

        addDisposableObserver(apiService.publicResultOfBooleanDate(token, params), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.getCodeSuccess(message);
            }

            @Override
            public void onFailure(int resultCode, String failureMessage) {
                mvpView.onRequestFailure(resultCode, failureMessage);
            }

            @Override
            public void onErrorThrowable(String errorMessage) {
                mvpView.onNewWorkException(errorMessage);
            }
        });

    }

    /**
     * 绑定手机号码-->或者微信绑定
     */
    public void startLoginOrBindPhoneNumber(final int requestType, String token, String params) {
        addDisposableObserver(apiService.publicResultOfUserInfoDate(token, params), new ApiServiceCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo response, String message) {
                mvpView.loginOrBindPhoneNumberSuccess(requestType, response, message);
            }

            @Override
            public void onFailure(int resultCode, String failureMessage) {
                mvpView.onRequestFailure(resultCode, failureMessage);
            }

            @Override
            public void onErrorThrowable(String errorMessage) {
                mvpView.onNewWorkException(errorMessage);
            }
        });
    }


}
