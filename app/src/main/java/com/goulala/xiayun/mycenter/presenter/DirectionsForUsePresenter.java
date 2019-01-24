package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.view.IDirectionsForUseView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class DirectionsForUsePresenter extends BasePresenter<IDirectionsForUseView> {
    public DirectionsForUsePresenter(IDirectionsForUseView mvpView) {
        super(mvpView);
    }

    public void getCouponsRules(String token, String param) {
        addDisposableObserver(apiService.publicResultOfStringDate(token, param), new ApiServiceCallback<String>() {
            @Override
            public void onSuccess(String response, String message) {
                mvpView.getCouponsRules(response);
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
