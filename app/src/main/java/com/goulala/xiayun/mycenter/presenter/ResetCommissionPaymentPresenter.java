package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.view.IResetCommissionPaymentView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class ResetCommissionPaymentPresenter extends BasePresenter<IResetCommissionPaymentView> {
    public ResetCommissionPaymentPresenter(IResetCommissionPaymentView mvpView) {
        super(mvpView);
    }


    /**
     * 获取验证码
     * 验证code
     * 重置密码
     */
    public void validationCodeOrResetPassword(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.validationCodeOrResetPasswordSuccess(requestType, response, message);
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
