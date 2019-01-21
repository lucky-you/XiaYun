package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/21
 * function  : 退款详情额数据
 */
public class ApplyRefundDetailsDate {


    /**
     * shop_order : 48013774600438480315
     * item_order : 0
     * item_id : 0
     * num : 1
     * money : 129
     * reason : 不想买了
     * createtime : 1539222347
     * record : {"id":463,"service_no":"47091575800355134653","type":32,"account_id":1,"service_info":"","express_company_id":0,"remark":"客服审核通过","images":"","createtime":1539222389,"updatetime":1539222389}
     * last_status : 32
     * images : []
     * list : [{}]
     * type_text :
     * status_text :
     */

    private String shop_order;
    private String item_order;
    private int item_id;
    private int num;
    private double money;
    private String reason;
    private long createtime;
    private RecordBean record;
    private int last_status;
    private String type_text;
    private String status_text;
    private List<String> images;
    private List<ShopItemMessage> list;

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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public RecordBean getRecord() {
        return record;
    }

    public void setRecord(RecordBean record) {
        this.record = record;
    }

    public int getLast_status() {
        return last_status;
    }

    public void setLast_status(int last_status) {
        this.last_status = last_status;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ShopItemMessage> getList() {
        return list;
    }

    public void setList(List<ShopItemMessage> list) {
        this.list = list;
    }
}
