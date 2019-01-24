package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.PaymentDetailsBean;
import com.goulala.xiayun.mycenter.view.IMyCommissionView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 收支明细  提现  公用
 */
public class MyCommissionPresenter extends BasePresenter<IMyCommissionView> {
    public MyCommissionPresenter(IMyCommissionView mvpView) {
        super(mvpView);
    }

    //获取余额
    public void getUserBalance(String token, String param) {
        addDisposableObserver(apiService.getAccountBalance(token, param), new ApiServiceCallback<AccountBalance>() {
            @Override
            public void onSuccess(AccountBalance response, String message) {
                mvpView.getAccountBalanceSuccess(response);
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

    //收支明细
    public void getPaymentDetails(String token, String param) {
        addDisposableObserver(apiService.getPaymentDetailsList(token, param), new ApiServiceCallback<PaymentDetailsBean>() {
            @Override
            public void onSuccess(PaymentDetailsBean response, String message) {
                mvpView.paymentDetailsSuccess(response);
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

    //提现成功
    public void withdrawalMoney(String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.withdrawalMoneySuccess(message);
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
