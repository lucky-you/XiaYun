package com.goulala.xiayun.mycenter.presenter;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.model.RefundMoneyDate;
import com.goulala.xiayun.mycenter.model.RefundResultDate;
import com.goulala.xiayun.mycenter.view.IReturnGoodApplicationView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :  退款--退货公用的view
 */
public class ReturnGoodApplicationPresenter extends BasePresenter<IReturnGoodApplicationView> {

    public ReturnGoodApplicationPresenter(IReturnGoodApplicationView mvpView) {
        super(mvpView);
    }


    /**
     * 获取商品信息
     */
    public void getRefundGoodMessage(String token, String param) {
        addDisposableObserver(apiService.getRefundMoneyDate(token, param), new ApiServiceCallback<RefundMoneyDate>() {
            @Override
            public void onSuccess(RefundMoneyDate response, String message) {
                mvpView.getRefundGoodMessageSuccess(response);
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
     * 提交信息
     */
    public void submitApplyRefund(String token, String param) {
        addDisposableObserver(apiService.submitApplyRefundDate(token, param), new ApiServiceCallback<RefundResultDate>() {
            @Override
            public void onSuccess(RefundResultDate response, String message) {
                mvpView.submitApplyRefundSuccess(response);
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
     * 七牛云的配置
     */
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
