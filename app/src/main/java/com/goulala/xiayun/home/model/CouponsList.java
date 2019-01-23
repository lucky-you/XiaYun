package com.goulala.xiayun.home.model;

/**
 * @author : Z_B
 * @date : 2018/8/14
 * @function : 新人专享优惠券实体类
 */
public class CouponsList {

    private int id;
    private String name;
    private int get_mode;
    private int get_max;
    private String discount_amount;
    private String use_trade_amount;
    private int use_starttime;
    private int use_endtime;
    private int use_expireday;
    private int use_item_brand;
    private int get_status;
    private String get_desc;
    private String brnad_desc;
    private String brand_desc;
    private String use_desc;
    private String expire_desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGet_mode() {
        return get_mode;
    }

    public void setGet_mode(int get_mode) {
        this.get_mode = get_mode;
    }

    public int getGet_max() {
        return get_max;
    }

    public void setGet_max(int get_max) {
        this.get_max = get_max;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getUse_trade_amount() {
        return use_trade_amount;
    }

    public void setUse_trade_amount(String use_trade_amount) {
        this.use_trade_amount = use_trade_amount;
    }

    public int getUse_starttime() {
        return use_starttime;
    }

    public void setUse_starttime(int use_starttime) {
        this.use_starttime = use_starttime;
    }

    public int getUse_endtime() {
        return use_endtime;
    }

    public void setUse_endtime(int use_endtime) {
        this.use_endtime = use_endtime;
    }

    public int getUse_expireday() {
        return use_expireday;
    }

    public void setUse_expireday(int use_expireday) {
        this.use_expireday = use_expireday;
    }

    public int getUse_item_brand() {
        return use_item_brand;
    }

    public void setUse_item_brand(int use_item_brand) {
        this.use_item_brand = use_item_brand;
    }

    public int getGet_status() {
        return get_status;
    }

    public void setGet_status(int get_status) {
        this.get_status = get_status;
    }

    public String getGet_desc() {
        return get_desc;
    }

    public void setGet_desc(String get_desc) {
        this.get_desc = get_desc;
    }

    public String getBrnad_desc() {
        return brnad_desc;
    }

    public void setBrnad_desc(String brnad_desc) {
        this.brnad_desc = brnad_desc;
    }

    public String getBrand_desc() {
        return brand_desc;
    }

    public void setBrand_desc(String brand_desc) {
        this.brand_desc = brand_desc;
    }

    public String getUse_desc() {
        return use_desc;
    }

    public void setUse_desc(String use_desc) {
        this.use_desc = use_desc;
    }

    public String getExpire_desc() {
        return expire_desc;
    }

    public void setExpire_desc(String expire_desc) {
        this.expire_desc = expire_desc;
    }
}
