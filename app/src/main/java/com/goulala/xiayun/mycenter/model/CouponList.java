package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/14
 * function  :
 */
public class CouponList {


    /**
     * id : 47
     * coupon_id : 35
     * user_id : 48
     * starttime : 1547539589
     * expiretime : 1547625989
     * useswitch : 0
     * createtime : 1547539589
     * updatetime : 1547539589
     * name : 新手优惠券
     * discount_amount : 20.00
     * use_trade_amount : 0.00
     * use_item_id :
     * use_item_num : 1
     * use_vip_level : 0
     * expire_desc :
     * merchant_desc : 全场通用
     * use_desc : 无门槛
     */

    private int id;
    private int coupon_id;
    private int user_id;
    private int starttime;
    private int expiretime;
    private int useswitch;
    private int createtime;
    private int updatetime;
    private String name;
    private String discount_amount;
    private String use_trade_amount;
    private String use_item_id;
    private int use_item_num;
    private int use_vip_level;
    private String expire_desc;
    private String merchant_desc;
    private String use_desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public int getUseswitch() {
        return useswitch;
    }

    public void setUseswitch(int useswitch) {
        this.useswitch = useswitch;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUse_item_id() {
        return use_item_id;
    }

    public void setUse_item_id(String use_item_id) {
        this.use_item_id = use_item_id;
    }

    public int getUse_item_num() {
        return use_item_num;
    }

    public void setUse_item_num(int use_item_num) {
        this.use_item_num = use_item_num;
    }

    public int getUse_vip_level() {
        return use_vip_level;
    }

    public void setUse_vip_level(int use_vip_level) {
        this.use_vip_level = use_vip_level;
    }

    public String getExpire_desc() {
        return expire_desc;
    }

    public void setExpire_desc(String expire_desc) {
        this.expire_desc = expire_desc;
    }

    public String getMerchant_desc() {
        return merchant_desc;
    }

    public void setMerchant_desc(String merchant_desc) {
        this.merchant_desc = merchant_desc;
    }

    public String getUse_desc() {
        return use_desc;
    }

    public void setUse_desc(String use_desc) {
        this.use_desc = use_desc;
    }
}
