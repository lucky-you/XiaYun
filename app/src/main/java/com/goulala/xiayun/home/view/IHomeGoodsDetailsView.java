package com.goulala.xiayun.home.view;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.GoodsDetailsMessage;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface IHomeGoodsDetailsView extends IBaseView {


    //获取商品详情
    void loadHomeGoodDetailsMessage(GoodsDetailsMessage goodsDetailsMessage);

    //获取商品详情失败
    void loadHomeGoodDetailsMessageFailed(String message);

    //收藏商品
    void collectTheGoodSuccess(String message);

    //加入购物车
    void addGoodToShopCarSuccess(String message);

    //取消收藏
    void cancelCollectTheGoodSuccess(String message);

    //该商品是否收藏
    void thatGoodIsCollect(Boolean isCollect);

    //检查token是否过期
    void checkUserTokenIsOverdue(UserInfo userToken);

    //获取商品的分享地址
    void getGoodShareUrlAddressSuccess(String url);

    //通过ip获取定位信息
    void formIpLoadCityBeanSuccess(String locationBean);

    //ip定位失败
    void formIpLoadCityBeanFailed(String error);

    //获取购物车的商品总件数
    void getTheTotalNumberOfShopCar(int number);


}
