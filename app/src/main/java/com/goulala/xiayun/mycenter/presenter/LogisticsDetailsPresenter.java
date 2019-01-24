package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.LogisticsDetailsBean;
import com.goulala.xiayun.mycenter.view.ILogisticsDetailsView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class LogisticsDetailsPresenter extends BasePresenter<ILogisticsDetailsView> {
    public LogisticsDetailsPresenter(ILogisticsDetailsView mvpView) {
        super(mvpView);
    }

    public void getLogisticsDetails(String token, String param) {
        addDisposableObserver(apiService.getLogisticsDetails(token, param), new ApiServiceCallback<LogisticsDetailsBean>() {
            @Override
            public void onSuccess(LogisticsDetailsBean response, String message) {
                mvpView.getLogisticsDetailsSuccess(response);
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
