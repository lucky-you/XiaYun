package com.goulala.xiayun.shopcar.presenter;

import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.model.ShopCarBaseDate;
import com.goulala.xiayun.shopcar.view.IShopCarView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class ShopCarPresenter extends BasePresenter<IShopCarView> {
    public ShopCarPresenter(IShopCarView mvpView) {
        super(mvpView);
    }


    //购物车的商品信息, 游客购物车 公用
    public void getShopCarGoodList(final int requestType, String token, String param) {
        addDisposableObserver(apiService.getShopCarGoodDateList(token, param), new ApiServiceCallback<List<ShopCarBaseDate>>() {
            @Override
            public void onSuccess(List<ShopCarBaseDate> response, String message) {
                switch (requestType) {
                    case IShopCarView.THE_TYPE_OF_GET_SHOP_CAR_GOOD_LIST:
                        mvpView.getShopCarGoodListSuccess(response);
                        break;
                    case IShopCarView.THE_TYPE_OF_GET_TOURIST_GOOD_LIST:
                        mvpView.getTouristShopGoodListSuccess(response);
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

    //猜你喜欢
    public void getGuessYouLikeGoodList(String token, String param) {
        addDisposableObserver(apiService.getGuessYouLikeGoodList(token, param), new ApiServiceCallback<List<GoodItemMessage>>() {
            @Override
            public void onSuccess(List<GoodItemMessage> response, String message) {
                mvpView.getGuessYouLikeGoodListSuccess(response);
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


    //购物车批量添加商品, 购物车添加商品, 购物车减少商品 ,购物车移除商品 公用
    public void addOrDeleteThatGoodFromShopCar(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                switch (requestType) {
                    case IShopCarView.THE_TYPE_OF_ADD_ITEM_TO_SHOP_CAR:
                        //数量增加
                        mvpView.addGoodItemsToYouShopCarSuccess(message);
                        break;
                    case IShopCarView.THE_TYPE_OF_REDUCE_ITEM_FROM_SHOP_CAR:
                        //数量减少
                        mvpView.reduceGoodItemFormYouShopCar(message);
                        break;

                    case IShopCarView.THE_TYPE_OF_DELETE_ITEM_FROM_SHOP_CAR:
                        //删除购物车
                        mvpView.deleteThatGoodFromShopCarSuccess(message);
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

    //购物车统计商品总数
    public void countsTheTotalGoodItemsOfYouShopCar(String token, String param) {



    }


    //获取商品的合计总价
    public void getShopCarTotalPrice(String token, String param) {
        addDisposableObserver(apiService.getMakeSureTheOrderMessage(token, param), new ApiServiceCallback<OrderMessage>() {
            @Override
            public void onSuccess(OrderMessage response, String message) {
                mvpView.getShopCarTotalPriceSuccess(response);
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
