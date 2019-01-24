package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.CollectGoodMessage;
import com.goulala.xiayun.mycenter.view.IMyCollectionOrMyFootprintView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class MyCollectionOrMyFootprintPresenter extends BasePresenter<IMyCollectionOrMyFootprintView> {
    public MyCollectionOrMyFootprintPresenter(IMyCollectionOrMyFootprintView mvpView) {
        super(mvpView);
    }


    /**
     * 获取列表
     */
    public void getGoodList(String token, String param) {
        addDisposableObserver(apiService.getCollectThatGoodDateList(token, param), new ApiServiceCallback<CollectGoodMessage>() {
            @Override
            public void onSuccess(CollectGoodMessage response, String message) {
                mvpView.getListSuccess(response);
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
     * 删除
     */
    public void deleteThatGood(String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.deleteGoodSuccess(message);
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
