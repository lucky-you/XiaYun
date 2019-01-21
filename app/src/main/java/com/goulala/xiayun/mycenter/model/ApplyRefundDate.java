package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/21
 * function  : 申请售后的数据
 */
public class ApplyRefundDate {

    /**
     * id : 44
     * service_no : 33000949000630754843
     * type : 1
     * pay_no : 47074361300646203452
     * shop_order : 47074972300590081808
     * item_order : 0
     * user_id : 31
     * item_id : 0
     * num : 1
     * money : 52.00
     * reason : 不想买了
     * status : 2
     * createtime : 1541560533
     * updatetime : 1541560533
     * is_sale : 1
     * last_status : 70
     * last_introduce : 已完成退款
     * type_text : Type 1
     * status_text : Status 2
     */

    private int id;
    private String service_no;
    private int type;
    private String pay_no;
    private String shop_order;
    private String item_order;
    private int user_id;
    private int item_id;
    private int num;
    private String money;
    private String reason;
    private int status;
    private long createtime;
    private long updatetime;
    private int is_sale;
    private int last_status;
    private String last_introduce;
    private String type_text;
    private String status_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_no() {
        return service_no;
    }

    public void setService_no(String service_no) {
        this.service_no = service_no;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public int getIs_sale() {
        return is_sale;
    }

    public void setIs_sale(int is_sale) {
        this.is_sale = is_sale;
    }

    public int getLast_status() {
        return last_status;
    }

    public void setLast_status(int last_status) {
        this.last_status = last_status;
    }

    public String getLast_introduce() {
        return last_introduce;
    }

    public void setLast_introduce(String last_introduce) {
        this.last_introduce = last_introduce;
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
}
