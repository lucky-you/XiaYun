package com.goulala.xiayun.common.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author      : Z_B
 * date       : 2019/1/21
 * function  :  游客的商品信息
 */
@Entity
public class TouristsGoodList {


    @NotNull
    private Long item_id;
    @NotNull
    private int merchant_id;
    @NotNull
    private int item_num;
    @Generated(hash = 1724895855)
    public TouristsGoodList(@NotNull Long item_id, int merchant_id, int item_num) {
        this.item_id = item_id;
        this.merchant_id = merchant_id;
        this.item_num = item_num;
    }
    @Generated(hash = 653514757)
    public TouristsGoodList() {
    }
    public Long getItem_id() {
        return this.item_id;
    }
    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }
    public int getMerchant_id() {
        return this.merchant_id;
    }
    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
    public int getItem_num() {
        return this.item_num;
    }
    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }
}
