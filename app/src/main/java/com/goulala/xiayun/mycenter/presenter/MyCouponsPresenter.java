package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.CouponMessage;
import com.goulala.xiayun.mycenter.view.IMyCouponsView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class MyCouponsPresenter extends BasePresenter<IMyCouponsView> {
    public MyCouponsPresenter(IMyCouponsView mvpView) {
        super(mvpView);
    }

    //获取优惠券的信息
    public void getCouponMessage(String token, String param) {
        addDisposableObserver(apiService.getCouponMessage(token, param), new ApiServiceCallback<CouponMessage>() {
            @Override
            public void onSuccess(CouponMessage response, String message) {
                mvpView.getCouponMessageSuccess(response);
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
