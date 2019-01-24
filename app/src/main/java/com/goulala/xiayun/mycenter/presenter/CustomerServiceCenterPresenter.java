package com.goulala.xiayun.mycenter.presenter;


import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.mycenter.model.ServiceCenterList;
import com.goulala.xiayun.mycenter.view.ICustomerServiceCenterView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public class CustomerServiceCenterPresenter extends BasePresenter<ICustomerServiceCenterView> {
    public CustomerServiceCenterPresenter(ICustomerServiceCenterView mvpView) {
        super(mvpView);
    }

    public void getServiceList(String token, String param) {
        addDisposableObserver(apiService.getServiceCenterList(token, param), new ApiServiceCallback<List<ServiceCenterList>>() {
            @Override
            public void onSuccess(List<ServiceCenterList> response, String message) {
                mvpView.getServiceListSuccess(response);
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
