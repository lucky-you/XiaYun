package com.goulala.xiayun.home.presenter;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.home.view.ISearchResultView;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class SearchResultPresenter extends BasePresenter<ISearchResultView> {

    public SearchResultPresenter(ISearchResultView mvpView) {
        super(mvpView);
    }


    //获取搜索的结果
    public void getSearchResultList(String token, String param) {
        addDisposableObserver(apiService.searchResultRequest(token, param), new ApiServiceCallback<GoodMessage>() {
            @Override
            public void onSuccess(GoodMessage response, String message) {
                mvpView.loadSearchResultList(response);
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
