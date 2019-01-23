package com.goulala.xiayun.common.ipresenter;

import android.content.Context;

import com.goulala.xiayun.common.iview.IWebDetailsView;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class WebDetailsPresenter extends BasePresenter<IWebDetailsView> {
    public WebDetailsPresenter(IWebDetailsView mvpView) {
        super(mvpView);
    }

    public void getWebViewDetails(String token, String param) {
        addDisposableObserver(apiService.publicResultOfStringDate(token, param), new ApiServiceCallback<String>() {
            @Override
            public void onSuccess(String response, String message) {
                mvpView.webViewDetails(response);
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
