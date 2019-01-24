package com.goulala.xiayun.mycenter.presenter;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.view.IMyProfileView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class MyProfilePresenter extends BasePresenter<IMyProfileView> {
    public MyProfilePresenter(IMyProfileView mvpView) {
        super(mvpView);
    }


    public void getUserInfoMessage(String token, String param) {
        addDisposableObserver(apiService.publicResultOfUserInfoDate(token, param), new ApiServiceCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo response, String message) {
                mvpView.getUserInfoMessage(response);
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
     * 修改会员信息
     */
    public void modifyMemberInformation(String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.modifySuccess(message);
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

    public void getQinIuSetMessage(String token, String param) {
        addDisposableObserver(apiService.getQinIuSetMessage(token, param), new ApiServiceCallback<QinIuBean>() {
            @Override
            public void onSuccess(QinIuBean response, String message) {
                mvpView.getQinIuSetMessageSuccess(response);
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
