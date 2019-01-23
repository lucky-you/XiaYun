package com.goulala.xiayun.shopcar.callback;

/**
 * author      : Z_B
 * date       : 2018/9/10
 * function  : 购物车数量的回调
 */
public interface ShopCarGoodNumberListener {

    //增加
    void addGoodItemToShopCar(int merchant_id, int item_id, int item_num);

    //减少
    void reduceGoodItemFormShopCar(int merchant_id, int item_id, int item_num);


    //商品的点击事件
    void onGoodItemClick(int goodId);

}
