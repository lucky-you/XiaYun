package com.goulala.xiayun.home.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.home.model.CoupleCouponsBean;
import com.goulala.xiayun.home.view.INewExclusiveView;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class NewExclusivePresenter extends BasePresenter<INewExclusiveView> {
    public NewExclusivePresenter(INewExclusiveView mvpView) {
        super(mvpView);
    }


    //获取优惠券
    public void getCouponDateList(String token, String param) {
        addDisposableObserver(apiService.getCouponDateList(token, param), new ApiServiceCallback<CoupleCouponsBean>() {
            @Override
            public void onSuccess(CoupleCouponsBean response, String message) {
                mvpView.getCouponDateListSuccess(response);
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

    //领取优惠券
    public void getTheCoupon(String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.getTheCouponSuccess(message);
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
