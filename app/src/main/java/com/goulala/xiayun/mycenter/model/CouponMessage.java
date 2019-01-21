package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/14
 * function  : 优惠券的数据
 */
public class CouponMessage {


    /**
     * total : 4
     * per_page : 5
     * current_page : 1
     * last_page : 1
     * data : [{"id":3,"coupon_id":7,"user_id":23,"starttime":1534497230,"expiretime":1534497230,"useswitch":1,"createtime":1534497242,"updatetime":1535791877,"discount_amount":"200.00","use_trade_amount":"1.00","use_item_id":0,"use_item_num":1,"use_vip_level":0,"expire_desc":"优惠券已经过期"},{"id":4,"coupon_id":7,"user_id":23,"starttime":1534497230,"expiretime":1534497230,"useswitch":0,"createtime":1535791458,"updatetime":1535791458,"discount_amount":"200.00","use_trade_amount":"1.00","use_item_id":0,"use_item_num":1,"use_vip_level":0,"expire_desc":"优惠券已经过期"},{"id":5,"coupon_id":7,"user_id":23,"starttime":1534497230,"expiretime":1534497230,"useswitch":0,"createtime":1535791463,"updatetime":1535791463,"discount_amount":"200.00","use_trade_amount":"1.00","use_item_id":0,"use_item_num":1,"use_vip_level":0,"expire_desc":"优惠券已经过期"},{"id":6,"coupon_id":7,"user_id":23,"starttime":1534497230,"expiretime":1534497230,"useswitch":0,"createtime":1535791925,"updatetime":1535791925,"discount_amount":"200.00","use_trade_amount":"1.00","use_item_id":0,"use_item_num":1,"use_vip_level":0,"expire_desc":"优惠券已经过期"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<CouponList> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<CouponList> getData() {
        return data;
    }

    public void setData(List<CouponList> data) {
        this.data = data;
    }

}
