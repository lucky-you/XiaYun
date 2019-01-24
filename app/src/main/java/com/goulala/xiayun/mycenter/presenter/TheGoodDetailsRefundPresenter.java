package com.goulala.xiayun.mycenter.presenter;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.ApplyRefundDetailsDate;
import com.goulala.xiayun.mycenter.model.BankCardList;
import com.goulala.xiayun.mycenter.view.ITheGoodDetailsRefundView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :  退款--退货 公用的presenter
 */
public class TheGoodDetailsRefundPresenter extends BasePresenter<ITheGoodDetailsRefundView> {
    public TheGoodDetailsRefundPresenter(ITheGoodDetailsRefundView mvpView) {
        super(mvpView);
    }

    //获取售后详情
    public void getApplyRefundDateDetails(String token, String param) {
        addDisposableObserver(apiService.applyRefundDetailsDate(token, param), new ApiServiceCallback<ApplyRefundDetailsDate>() {
            @Override
            public void onSuccess(ApplyRefundDetailsDate response, String message) {
                mvpView.getApplyRefundDetailsSuccess(response);
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

    //撤销申请--继续提交
    public void cancelTheApplicationOrSubmit(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.cancelTheApplicationOrToSubmitSuccess(requestType, message);
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

    //获取物流公司
    public void accessLogisticsCompany(String token, String param) {
        addDisposableObserver(apiService.getBankCardList(token, param), new ApiServiceCallback<List<BankCardList>>() {
            @Override
            public void onSuccess(List<BankCardList> response, String message) {
                mvpView.accessLogisticsCompanySuccess(response);
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
