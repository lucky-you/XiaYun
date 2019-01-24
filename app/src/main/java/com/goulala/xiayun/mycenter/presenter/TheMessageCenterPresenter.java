package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.MessageCenterList;
import com.goulala.xiayun.mycenter.view.ITheMessageCenterView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class TheMessageCenterPresenter extends BasePresenter<ITheMessageCenterView> {
    public TheMessageCenterPresenter(ITheMessageCenterView mvpView) {
        super(mvpView);
    }

    public void getMessageList(String token, String param) {
        addDisposableObserver(apiService.getMessageCenterList(token, param), new ApiServiceCallback<List<MessageCenterList>>() {
            @Override
            public void onSuccess(List<MessageCenterList> response, String message) {
                mvpView.getMessageListSuccess(response);
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
