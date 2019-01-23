package com.goulala.xiayun.shopcar.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.model.ShopCarBaseDate;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/16
 * function : 购物车的view
 */
public interface IShopCarView extends IBaseView {

    int THE_TYPE_OF_GET_SHOP_CAR_GOOD_LIST = 1;        //购物车

    int THE_TYPE_OF_GET_TOURIST_GOOD_LIST = 2; //  游客购物车

    int THE_TYPE_OF_GET_GUESS_YOU_LIKE_GOOD_LIST = 3; //猜你喜欢

    int THE_TYPE_OF_BLUL_ITEM_TO_SHOP_CAR = 4; //购物车批量添加商品

    int THE_TYPE_OF_ADD_ITEM_TO_SHOP_CAR = 5; //购物车添加商品

    int THE_TYPE_OF_REDUCE_ITEM_FROM_SHOP_CAR = 6; //购物车减少商品

    int THE_TYPE_OF_DELETE_ITEM_FROM_SHOP_CAR = 7;//购物车移除商品

    //购物车的商品信息
    void getShopCarGoodListSuccess(List<ShopCarBaseDate> goodMessage);

    //猜你喜欢的商品
    void getGuessYouLikeGoodListSuccess(List<GoodItemMessage> goodMessage);

    //游客购物车
    void getTouristShopGoodListSuccess(List<ShopCarBaseDate> touristShopGood);


    //购物车添加商品
    void addGoodItemsToYouShopCarSuccess(String message);

    //购物车减少商品
    void reduceGoodItemFormYouShopCar(String message);

    // 购物车移除商品
    void deleteThatGoodFromShopCarSuccess(String message);

    //购物车统计商品总数
    void countsTheTotalGoodItemsOfYouShopCar(GoodMessage goodMessage);

    //获取商品的合计总价
    void getShopCarTotalPriceSuccess(OrderMessage orderMessage);



}
