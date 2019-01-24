package com.goulala.xiayun.mycenter.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.BankCardList;
import com.goulala.xiayun.mycenter.view.ISelectBankCardView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 选择银行卡
 */
public class SelectBankCardPresenter extends BasePresenter<ISelectBankCardView> {
    public SelectBankCardPresenter(ISelectBankCardView mvpView) {
        super(mvpView);
    }

    public void getBankCardList(String token, String param) {
        addDisposableObserver(apiService.getBankCardList(token, param), new ApiServiceCallback<List<BankCardList>>() {
            @Override
            public void onSuccess(List<BankCardList> response, String message) {
                mvpView.getBankCardListSuccess(response);
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
