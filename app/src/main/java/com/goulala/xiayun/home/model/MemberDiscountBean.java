package com.goulala.xiayun.home.model;

/**
 * author : Z_B
 * date : 2018/8/10
 * function : 会员优惠
 */
public class MemberDiscountBean {


    /**
     * discount_price : 10.02
     * user_id : 26
     * vip_str :  会员 199****5393 累计优惠 10.02
     * mobile : 199****5393
     * completetime_text :
     */

    private String discount_price;
    private int user_id;
    private String vip_str;
    private String mobile;
    private String completetime_text;

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getVip_str() {
        return vip_str;
    }

    public void setVip_str(String vip_str) {
        this.vip_str = vip_str;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompletetime_text() {
        return completetime_text;
    }

    public void setCompletetime_text(String completetime_text) {
        this.completetime_text = completetime_text;
    }
}
