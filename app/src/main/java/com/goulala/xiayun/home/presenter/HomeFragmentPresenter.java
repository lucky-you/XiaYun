package com.goulala.xiayun.home.presenter;

import android.content.Context;

import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.home.model.DefaultSearchKeyWords;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.home.model.HomeBannerDate;
import com.goulala.xiayun.home.model.HomeValueSellingBean;
import com.goulala.xiayun.home.model.HotStyleSecondsKill;
import com.goulala.xiayun.home.model.MemberDiscountBean;
import com.goulala.xiayun.home.view.IHomeFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class HomeFragmentPresenter extends BasePresenter<IHomeFragmentView> {
    public HomeFragmentPresenter(IHomeFragmentView mvpView) {
        super(mvpView);
    }

    //默认关键字
    public void getDefaultSearch(String token, String param) {
        addDisposableObserver(apiService.getDefaultSearchResult(token, param), new ApiServiceCallback<DefaultSearchKeyWords>() {
            @Override
            public void onSuccess(DefaultSearchKeyWords response, String message) {
                mvpView.getDefaultSearch(response);
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

    //获取首页的banner数据
    public void getHomeBannerDate(String token, String param, final String bannerType) {
        addDisposableObserver(apiService.getHomeBannerDate(token, param), new ApiServiceCallback<List<HomeBannerDate>>() {
            @Override
            public void onSuccess(List<HomeBannerDate> response, String message) {
                switch (bannerType) {
                    case ApiParam.TOP_BANNER:
                        mvpView.getHomeBannerSuccess(response, ApiParam.TOP_BANNER);
                        break;
                    case ApiParam.RIGHT_BANNER_ONE:
                        mvpView.getHomeBannerSuccess(response, ApiParam.RIGHT_BANNER_ONE);
                        break;
                    case ApiParam.RIGHT_BANNER_TWO:
                        mvpView.getHomeBannerSuccess(response, ApiParam.RIGHT_BANNER_TWO);
                        break;
                }
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

    //获取超值热卖
    public void getHomeValueSellingGood(String token, String param) {
        addDisposableObserver(apiService.HomeValueSellingGood(token, param), new ApiServiceCallback<List<HomeValueSellingBean>>() {
            @Override
            public void onSuccess(List<HomeValueSellingBean> response, String message) {
                mvpView.getHomeValueSellingGood(response);
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

    //爆品秒杀
    public void getHotStyleSecondsKill(Context context, String token, String param) {
        addDisposableObserver(apiService.HotStyleKillProducts(token, param), new ApiServiceCallback<ArrayList<HotStyleSecondsKill>>() {
            @Override
            public void onSuccess(ArrayList<HotStyleSecondsKill> response, String message) {
                mvpView.getHotStyleSecondsKillSuccess(response);
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

    //品质优选
    public void getHomeQualityTheOptimization(Context context, String token, String param) {
        addDisposableObserver(apiService.HomeQualityTheOptimization(token, param), new ApiServiceCallback<GoodMessage>() {
            @Override
            public void onSuccess(GoodMessage response, String message) {
                mvpView.getHomeQualityTheOptimization(response);
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

    //会员优惠榜单
    public void getMembershipList(String token, String param) {
        addDisposableObserver(apiService.getMembershipList(token, param), new ApiServiceCallback<List<MemberDiscountBean>>() {
            @Override
            public void onSuccess(List<MemberDiscountBean> response, String message) {
                mvpView.getMembershipList(response);
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

    //检查token是否过期
    public void checkUserTokenIsOverdue(String token, String param) {
        addDisposableObserver(apiService.publicResultOfUserInfoDate(token, param), new ApiServiceCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo response, String message) {
                mvpView.checkUserTokenIsOverdue(response);
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
