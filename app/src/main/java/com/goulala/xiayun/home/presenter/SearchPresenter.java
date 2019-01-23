package com.goulala.xiayun.home.presenter;

import com.goulala.xiayun.common.db.SearchHistory;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.home.view.ISearchView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class SearchPresenter extends BasePresenter<ISearchView> {
    public SearchPresenter(ISearchView mvpView) {
        super(mvpView);
    }


    /**
     * 热门搜索
     */
    public void getHotSearch(String token, String param) {
        addDisposableObserver(apiService.getHotSearchResult(token, param), new ApiServiceCallback<List<SearchHistory>>() {
            @Override
            public void onSuccess(List<SearchHistory> response, String message) {
                mvpView.getHotSearch(response);
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
     * 搜索关联
     */
    public void getSearchRelevanceDate(String token, String param) {
        addDisposableObserver(apiService.getHotSearchResult(token, param), new ApiServiceCallback<List<SearchHistory>>() {
            @Override
            public void onSuccess(List<SearchHistory> response, String message) {
                mvpView.getSearchRelevanceDate(response);
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
