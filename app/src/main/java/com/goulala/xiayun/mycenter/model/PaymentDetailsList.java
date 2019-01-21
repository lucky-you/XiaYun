package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/29
 * function  : 收支明细
 */
public class PaymentDetailsList {


    /**
     * user_id : 23
     * user_type : 1
     * source : 2
     * type : 2
     * pay_no : 36023659800490189264
     * money : 200.00
     * remark : 余额支付
     * status : 2
     * createtime : 1538134956
     * createip : 0.0.0.0
     * updatetime : 1538134956
     * source_text : Source 2
     * type_text : Type 2
     * status_text : Status 2
     * callbacktime_text : 2018-09-28 19:42:36
     */

    private int user_id;
    private int user_type;
    private int source;
    private int type;
    private String pay_no;
    private double money;
    private String remark;
    private int status;
    private long createtime;
    private String createip;
    private long updatetime;
    private String source_text;
    private String type_text;
    private String status_text;
    private String callbacktime_text;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getCreateip() {
        return createip;
    }

    public void setCreateip(String createip) {
        this.createip = createip;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public String getSource_text() {
        return source_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getCallbacktime_text() {
        return callbacktime_text;
    }

    public void setCallbacktime_text(String callbacktime_text) {
        this.callbacktime_text = callbacktime_text;
    }
}
