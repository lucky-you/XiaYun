package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.LogisticsMessageBean;
import com.goulala.xiayun.mycenter.view.IMessageDetailsView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class MessageDetailsPresenter extends BasePresenter<IMessageDetailsView> {
    public MessageDetailsPresenter(IMessageDetailsView mvpView) {
        super(mvpView);
    }

    public void getMessageDetails(String token, String param) {
        addDisposableObserver(apiService.getTheLogisticsMessage(token, param), new ApiServiceCallback<LogisticsMessageBean>() {
            @Override
            public void onSuccess(LogisticsMessageBean response, String message) {
                mvpView.getMessageDetails(response);
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

    public void readMessage(String token, String param) {
        addDisposableObserver(apiService.publicResultOfObjectData(token, param), new ApiServiceCallback<Object>() {
            @Override
            public void onSuccess(Object response, String message) {
                mvpView.readMessageSuccess(message);
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
