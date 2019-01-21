package com.goulala.xiayun.home.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/19
 * function  : 新人专享的优惠券
 */
public class CoupleCouponsBean {


    /**
     * total : 2
     * per_page : 5
     * current_page : 1
     * last_page : 1
     * data : [{"id":6,"name":"满100抵扣20","discount_amount":"20.00","get_mode":1,"get_num":2,"get_max":8,"get_stock":3,"must_trade_amount":"0.00","must_item_id":null,"must_item_num":0,"must_reg_starttime":0,"must_reg_endtime":0,"must_vip_level":0,"must_score_amount":0,"use_trade_amount":"40.00","use_item_id":null,"use_item_num":0,"use_vip_level":0,"use_starttime":1535707166,"use_endtime":1571383054,"use_expireday":15,"createtime":1535707477,"updatetime":1539420839,"get_desc":"已领取"},{"id":7,"name":"新手满100减15券","discount_amount":"15.00","get_mode":1,"get_num":1,"get_max":5,"get_stock":null,"must_trade_amount":"0.00","must_item_id":null,"must_item_num":0,"must_reg_starttime":1508311054,"must_reg_endtime":1571383054,"must_vip_level":0,"must_score_amount":0,"use_trade_amount":"30.00","use_item_id":null,"use_item_num":0,"use_vip_level":0,"use_starttime":1535709449,"use_endtime":1574935049,"use_expireday":90,"createtime":1535709481,"updatetime":1539420832,"get_desc":"已领取"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<CouponsList> data;


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

    public List<CouponsList> getData() {
        return data;
    }

    public void setData(List<CouponsList> data) {
        this.data = data;
    }
}
