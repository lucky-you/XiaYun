package com.goulala.xiayun.home.presenter;

import android.content.Context;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.home.model.HomeValueSellingBean;
import com.goulala.xiayun.home.view.ISellLotsOfDetailsView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class SellLotsOfDetailsPresenter extends BasePresenter<ISellLotsOfDetailsView> {
    public SellLotsOfDetailsPresenter(ISellLotsOfDetailsView mvpView) {
        super(mvpView);
    }

    public void getActivityDateList(Context context, String token, String param) {
        addDisposableObserver(apiService.HomeValueSellingGood(token, param), new ApiServiceCallback<List<HomeValueSellingBean>>() {
            @Override
            public void onSuccess(List<HomeValueSellingBean> response, String message) {
                mvpView.getActivityDateList(response);
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

    public void getTheTotalNumberOfShopCar(String token, String param) {
        addDisposableObserver(apiService.publicResultOfIntegerDate(token, param), new ApiServiceCallback<Integer>() {
            @Override
            public void onSuccess(Integer response, String message) {
                mvpView.getTheTotalNumberOfShopCar(response);
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
