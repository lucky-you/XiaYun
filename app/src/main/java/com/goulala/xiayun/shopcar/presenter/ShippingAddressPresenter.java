package com.goulala.xiayun.shopcar.presenter;


import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.shopcar.view.IShippingAddressView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :  添加和选择公用
 */
public class ShippingAddressPresenter extends BasePresenter<IShippingAddressView> {
    public ShippingAddressPresenter(IShippingAddressView mvpView) {
        super(mvpView);
    }

    //收货地址
    public void getShoppingAddressList(String token, String param) {
        addDisposableObserver(apiService.getShoppingAddressList(token, param), new ApiServiceCallback<List<ShoppingAddressList>>() {
            @Override
            public void onSuccess(List<ShoppingAddressList> response, String message) {
                mvpView.getShoppingAddressListSuccess(response);
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

    //对地址的操作,设置默认,删除收货地址
    public void operationOnTheAddress(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                mvpView.theOperationOfAddress(requestType, message);
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
