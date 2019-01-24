package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.RecordBean;
import com.goulala.xiayun.mycenter.view.ICommunicationRecordView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class CommunicationRecordPresenter extends BasePresenter<ICommunicationRecordView> {
    public CommunicationRecordPresenter(ICommunicationRecordView mvpView) {
        super(mvpView);
    }

    public void getCommunicationRecord(String token, String param) {
        addDisposableObserver(apiService.getCommunicationRecord(token, param), new ApiServiceCallback<List<RecordBean>>() {
            @Override
            public void onSuccess(List<RecordBean> response, String message) {
                mvpView.getCommunicationRecordSuccess(response);
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
