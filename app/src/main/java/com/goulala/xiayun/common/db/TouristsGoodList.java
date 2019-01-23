package com.goulala.xiayun.common.db;

/**
 * author      : Z_B
 * date       : 2019/1/21
 * function  :  游客的商品信息
 */
public class TouristsGoodList {


    public Long item_id;
    public int merchant_id;
    public int item_num;

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }
}
