package com.goulala.xiayun.home.presenter;


import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.retrofit.ApiServiceCallback;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.home.model.GoodsDetailsMessage;
import com.goulala.xiayun.home.view.IHomeGoodsDetailsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public class HomeGoodsDetailsPresenter extends BasePresenter<IHomeGoodsDetailsView> {


    public HomeGoodsDetailsPresenter(IHomeGoodsDetailsView mvpView) {

        super(mvpView);

    }


    /**
     * 获取商品的详情
     */
    public void getHomeGoodDetailsMessage(String token, String param) {
        addDisposableObserver(apiService.getHomeGoodDetails(token, param), new ApiServiceCallback<GoodsDetailsMessage>() {
            @Override
            public void onSuccess(GoodsDetailsMessage response, String message) {
                mvpView.loadHomeGoodDetailsMessage(response);
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
     * 对商品的相关操作--》添加，删除等
     */
    public void commodityOperatingThatGood(final int requestType, String token, String param) {
        addDisposableObserver(apiService.publicResultOfBooleanDate(token, param), new ApiServiceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response, String message) {
                switch (requestType) {
                    case ConstantValue.COLLECTION_THAT_GOOD_TYPE:
                        mvpView.collectTheGoodSuccess(message);
                        break;
                    case ConstantValue.CANCEL_COLLECTION_THAT_GOOD_TYPE:
                        mvpView.cancelCollectTheGoodSuccess(message);
                        break;
                    case ConstantValue.ADD_THAT_GOOD_TO_SHOP_CAR_TYPE:
                        mvpView.addGoodToShopCarSuccess(message);
                        break;
                    case ConstantValue.THAT_GOOD_IS_COLLECTION_TYPE:
                        mvpView.thatGoodIsCollect(response);
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

    /**
     * 购物车商品总数
     */
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

    /**
     * 根据ip定位
     */
    public void getIpLocationBean(String ipAddress, String output, String mapKey) {
        addDisposableObserver(apiService.formIpLoadCityLocationBean(ipAddress, output, mapKey), new ApiServiceCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response, String message) {
                if (response != null) {
                    try {
                        String date = new String(response.bytes());
                        JSONObject jsonObject = new JSONObject(date);
                        int status = jsonObject.optInt("status");
                        String info = jsonObject.optString("info");
                        String provinceName = jsonObject.optString("province");
                        String cityName = jsonObject.optString("city");
                        if (1 == status) {
                            mvpView.formIpLoadCityBeanSuccess(provinceName + cityName);
                        } else {
                            mvpView.formIpLoadCityBeanFailed(info);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    /**
     * 分享的url
     */
    public void getGoodShareUrl(String token, String param) {
        addDisposableObserver(apiService.publicResultOfStringDate(token, param), new ApiServiceCallback<String>() {
            @Override
            public void onSuccess(String response, String message) {
                mvpView.getGoodShareUrlAddressSuccess(response);
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
     * token是否过期
     */
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
