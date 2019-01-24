package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.AllOrderListMessage;
import com.goulala.xiayun.mycenter.model.OrderDetailsMessage;
import com.goulala.xiayun.mycenter.view.IGoodListView;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 商品的presenter 商品列表 和商品详情公用
 */
public class GoodListPresenter extends BasePresenter<IGoodListView> {
    public GoodListPresenter(IGoodListView mvpView) {
        super(mvpView);
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetails(String token, String param) {
        addDisposableObserver(apiService.getOrderDetailsMessage(token, param), new ApiServiceCallback<OrderDetailsMessage>() {
            @Override
            public void onSuccess(OrderDetailsMessage response, String message) {
                mvpView.getOrderDetailsSuccess(response);
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
     * 获取订单列表
     */
    public void getOrderList(String token, String param) {
        addDisposableObserver(apiService.getAllOrderList(token, param), new ApiServiceCallback<AllOrderListMessage>() {
            @Override
            public void onSuccess(AllOrderListMessage response, String message) {
                mvpView.getOrderListSuccess(response);
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
     * 获取余额
     */
    public void getAccountBalance(String token, String param) {
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

    /**
     * 检查是否设置了支付密码
     * 取消订单，删除订单，确认收货
     * 公用
     */
    public void checkPasswordExistOrDeleteOrder(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.checkPasswordExistOrOperationOrder(requestType, response, message);
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
     * 余额支付或者支付宝支付
     */
    public void useBalanceOrAliPayPayment(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfStringDate(token, param), new ApiServiceCallback<String>() {
            @Override
            public void onSuccess(String response, String message) {
                mvpView.useBalanceOrAliPayPaymentSuccess(requestType, message);
            }

            @Override
            public void onFailure(int resultCode, String failureMessage) {
                mvpView.userPaymentFailed(requestType, failureMessage);
            }

            @Override
            public void onErrorThrowable(String errorMessage) {
                mvpView.onNewWorkException(errorMessage);
            }
        });

    }

    /**
     * 微信支付
     */
    public void useWeChatPayment(String token, String param) {
        addDisposableObserver(apiService.useWeChatPayment(token, param), new ApiServiceCallback<WxPayReqInfo>() {
            @Override
            public void onSuccess(WxPayReqInfo response, String message) {
                mvpView.useWeChatPaymentSuccess(response);
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
