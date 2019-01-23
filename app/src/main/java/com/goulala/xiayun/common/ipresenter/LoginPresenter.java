package com.goulala.xiayun.common.ipresenter;


import com.goulala.xiayun.common.iview.ILoginView;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView mvpView) {
        super(mvpView);
    }

    /**
     * 登录
     */
    public void toLogin(String token, String param) {
        addDisposableObserver(apiService.publicResultOfUserInfoDate(token, param), new ApiServiceCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo response, String message) {
                mvpView.LoginSuccess(response, message);
            }

            @Override
            public void onFailure(int resultCode, String failureMessage) {
                mvpView.LoginFailed(resultCode, failureMessage);
            }

            @Override
            public void onErrorThrowable(String errorMessage) {
                mvpView.onNewWorkException(errorMessage);
            }
        });
    }
}
