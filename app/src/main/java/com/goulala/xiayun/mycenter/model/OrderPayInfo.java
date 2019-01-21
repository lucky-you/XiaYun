package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/18
 * function  : 订单的支付信息
 */
public class OrderPayInfo {

    public static final int USE_BALANCE_PAYMENT_TYPE = 2;//2是余额支付
    public static final int USE_ALIPAY_PAYMENT_TYPE = 3;//3是支付宝支付
    public static final int USE_WECHAT_PAYMENT_TYPE = 4;//4是微信支付


    /**
     * id : 94
     * user_id : 23
     * merchant_id : 0
     * source : 3   //2是余额支付，3是支付宝支付，4是微信支付
     * pay_no : 20180917201109090209400591423052
     * money : 0.01
     * type : 2
     * status : 2
     * createtime : 1537186330
     * updatetime : 1537220171
     * remark : 支付宝支付
     * callbacktime : 1537220171
     */

    private int id;
    private int user_id;
    private int merchant_id;
    private int source;
    private String pay_no;
    private double money;
    private int type;
    private int status;
    private long createtime;
    private long updatetime;
    private String remark;
    private long callbacktime;

    public static int getUseBalancePaymentType() {
        return USE_BALANCE_PAYMENT_TYPE;
    }

    public static int getUseAlipayPaymentType() {
        return USE_ALIPAY_PAYMENT_TYPE;
    }

    public static int getUseWechatPaymentType() {
        return USE_WECHAT_PAYMENT_TYPE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCallbacktime() {
        return callbacktime;
    }

    public void setCallbacktime(long callbacktime) {
        this.callbacktime = callbacktime;
    }
}
