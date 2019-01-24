package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.CollectionAndHistoryBean;
import com.goulala.xiayun.mycenter.model.UserIsMembersBean;
import com.goulala.xiayun.mycenter.view.IMyCenterView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class MyCenterPresenter extends BasePresenter<IMyCenterView> {
    public MyCenterPresenter(IMyCenterView mvpView) {
        super(mvpView);
    }

    public void getCollectionAndHistoryDate(final int requestType, String token, String param) {

        addDisposableObserver(apiService.getCollectionAndHistory(token, param), new ApiServiceCallback<CollectionAndHistoryBean>() {
            @Override
            public void onSuccess(CollectionAndHistoryBean response, String message) {
                mvpView.getCollectionAndHistoryDate(requestType, response);
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

    public void usersAreNotMembers(String token, String param) {
        addDisposableObserver(apiService.checkUserIsMember(token, param), new ApiServiceCallback<UserIsMembersBean>() {
            @Override
            public void onSuccess(UserIsMembersBean response, String message) {
                mvpView.usersAreNotMembers(response);
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
