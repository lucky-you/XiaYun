package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  : 会员优惠券
 */
public class VipCouponTicketMessage {

    private int total;
    private String per_page;
    private int current_page;
    private int last_page;
    private List<VipCouponTicketList> data;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
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

    public List<VipCouponTicketList> getData() {
        return data;
    }

    public void setData(List<VipCouponTicketList> data) {
        this.data = data;
    }
}
