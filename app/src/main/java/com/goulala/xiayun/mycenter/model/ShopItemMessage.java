package com.goulala.xiayun.mycenter.model;

import com.goulala.xiayun.home.model.GoodItemMessage;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/17
 * function  : 单个店铺下面商品的信息（存在着多商品情况）
 */
public class ShopItemMessage {

    /**
     * id : 784
     * pay_no : 43041227400594594943
     * shop_order : 43046439800468506608
     * item_order : 43046440900384803668
     * user_id : 31
     * merchant_id : 7
     * item_id : 36
     * num : 1
     * unit_price : 180.00
     * money : 180.00
     * active_id : 65
     * discount_money : 0.00
     * user_coupon_id : 0
     * coupon_money : 0.00
     * pay_money : 180.00
     * freight : 0.00
     * remark :
     * status : 1
     * fail_status : 0
     * after_status : 0
     * completetime : 0
     * createtime : 1541664643
     * updatetime : 1541731069
     * share_user_id : 0
     * share_time : 0
     * member_single_price : 180.00
     * single_price : 80.00
     * item : {}
     * apply : []
     * completetime_text : 1970-01-01 08:00:00
     */

    private int id;
    private String pay_no;
    private String shop_order;
    private String item_order;
    private int user_id;
    private int merchant_id;
    private int item_id;
    private int num;
    private double unit_price;
    private double money;
    private int active_id;
    private double discount_money;
    private int user_coupon_id;
    private double coupon_money;
    private double pay_money;
    private double freight;
    private String remark;
    private int status;
    private int fail_status;
    private int after_status;
    private long completetime;
    private long createtime;
    private long updatetime;
    private int share_user_id;
    private int share_time;
    private double member_single_price;
    private double single_price;
    private GoodItemMessage item;
    private List<ApplyRefundDate> apply;
    private String completetime_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getShop_order() {
        return shop_order;
    }

    public void setShop_order(String shop_order) {
        this.shop_order = shop_order;
    }

    public String getItem_order() {
        return item_order;
    }

    public void setItem_order(String item_order) {
        this.item_order = item_order;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getActive_id() {
        return active_id;
    }

    public void setActive_id(int active_id) {
        this.active_id = active_id;
    }

    public double getDiscount_money() {
        return discount_money;
    }

    public void setDiscount_money(double discount_money) {
        this.discount_money = discount_money;
    }

    public int getUser_coupon_id() {
        return user_coupon_id;
    }

    public void setUser_coupon_id(int user_coupon_id) {
        this.user_coupon_id = user_coupon_id;
    }

    public double getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(double coupon_money) {
        this.coupon_money = coupon_money;
    }

    public double getPay_money() {
        return pay_money;
    }

    public void setPay_money(double pay_money) {
        this.pay_money = pay_money;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFail_status() {
        return fail_status;
    }

    public void setFail_status(int fail_status) {
        this.fail_status = fail_status;
    }

    public int getAfter_status() {
        return after_status;
    }

    public void setAfter_status(int after_status) {
        this.after_status = after_status;
    }

    public long getCompletetime() {
        return completetime;
    }

    public void setCompletetime(long completetime) {
        this.completetime = completetime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public int getShare_user_id() {
        return share_user_id;
    }

    public void setShare_user_id(int share_user_id) {
        this.share_user_id = share_user_id;
    }

    public int getShare_time() {
        return share_time;
    }

    public void setShare_time(int share_time) {
        this.share_time = share_time;
    }

    public double getMember_single_price() {
        return member_single_price;
    }

    public void setMember_single_price(double member_single_price) {
        this.member_single_price = member_single_price;
    }

    public double getSingle_price() {
        return single_price;
    }

    public void setSingle_price(double single_price) {
        this.single_price = single_price;
    }

    public GoodItemMessage getItem() {
        return item;
    }

    public void setItem(GoodItemMessage item) {
        this.item = item;
    }

    public List<ApplyRefundDate> getApply() {
        return apply;
    }

    public void setApply(List<ApplyRefundDate> apply) {
        this.apply = apply;
    }

    public String getCompletetime_text() {
        return completetime_text;
    }

    public void setCompletetime_text(String completetime_text) {
        this.completetime_text = completetime_text;
    }
}
