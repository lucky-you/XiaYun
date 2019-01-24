package com.goulala.xiayun.shopcar.presenter;

import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.view.IMakeSureTheOrderView;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class MakeSureTheOrderPresenter extends BasePresenter<IMakeSureTheOrderView> {
    public MakeSureTheOrderPresenter(IMakeSureTheOrderView mvpView) {
        super(mvpView);
    }

    //获取收货地址
    public void getShoppingAddress(String token, String param) {
        addDisposableObserver(apiService.getShoppingAddressList(token, param), new ApiServiceCallback<List<ShoppingAddressList>>() {
            @Override
            public void onSuccess(List<ShoppingAddressList> response, String message) {
                mvpView.getShoppingAddressSuccess(response);
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

    //获取订单信息
    public void getOrderMessageOrSubmitOrder(final int requestType, String token, String param) {
        addDisposableObserver(apiService.getMakeSureTheOrderMessage(token, param), new ApiServiceCallback<OrderMessage>() {
            @Override
            public void onSuccess(OrderMessage response, String message) {
                mvpView.getOrderMessageOrSubmitOrderSuccess(requestType, response);
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

    //余额支付或者支付宝支付
    public void useBalanceOrAliPayPayment(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfStringDate(token, param), new ApiServiceCallback<String>() {
            @Override
            public void onSuccess(String response, String message) {
                mvpView.useBalanceOrAliPayPaymentSuccess(requestType, message);
            }

            @Override
            public void onFailure(int resultCode, String failureMessage) {
                mvpView.useBalanceOrAliPayPaymentFailed(requestType, failureMessage);
            }

            @Override
            public void onErrorThrowable(String errorMessage) {
                mvpView.onNewWorkException(errorMessage);
            }
        });
    }


    //微信支付
    public void useWeChatPayment(String token, String param) {
        addDisposableObserver(apiService.useWeChatPayment(token, param), new ApiServiceCallback<WxPayReqInfo>() {
            @Override
            public void onSuccess(WxPayReqInfo response, String message) {
                mvpView.useWeChatPaymentSuccess(response);
            }

            @Override
            public void onFailure(int resultCode, String failureMessage) {
                mvpView.useWeChatPaymentFailed(failureMessage);

            }

            @Override
            public void onErrorThrowable(String errorMessage) {
                mvpView.onNewWorkException(errorMessage);
            }
        });

    }

    //是否设置了用户密码
    public void checkPasswordExist(String token, String param) {

        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.checkPasswordExist(response);
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

    //获取用余额
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


}
