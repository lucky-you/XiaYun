package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.view.IPlatformSuggestionBoxView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class PlatformSuggestionBoxPresenter extends BasePresenter<IPlatformSuggestionBoxView> {
    public PlatformSuggestionBoxPresenter(IPlatformSuggestionBoxView mvpView) {
        super(mvpView);
    }

    public void submitPlatformMessage(String token, String param) {
        addDisposableObserver(apiService.publicResultOfObjectData(token, param), new ApiServiceCallback<Object>() {
            @Override
            public void onSuccess(Object response, String message) {
                mvpView.submitPlatformMessage(message);
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
