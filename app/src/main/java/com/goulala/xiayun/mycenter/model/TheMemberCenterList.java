package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/29
 * function  :  会员中心
 */
public class TheMemberCenterList {


    /**
     * id : 12
     * term : 12
     * open_price : 100.00
     * discount_open_price : 80.00
     * start_time : 1538125826
     * end_time : 1540977029
     * describe : 一年
     * vip_type : 2
     * vip_title : null
     * createtime : 0
     * updatetime : 0
     * discount_type : 2
     * start_time_text : 2018-09-28 17:10:26
     * end_time_text : 2018-10-31 17:10:29
     */

    private int id;
    private int term;
    private String open_price;
    private String discount_open_price;
    private int start_time;
    private int end_time;
    private String describe;
    private int vip_type;
    private String vip_title;
    private int createtime;
    private int updatetime;
    private int discount_type;
    private String start_time_text;
    private String end_time_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getOpen_price() {
        return open_price;
    }

    public void setOpen_price(String open_price) {
        this.open_price = open_price;
    }

    public String getDiscount_open_price() {
        return discount_open_price;
    }

    public void setDiscount_open_price(String discount_open_price) {
        this.discount_open_price = discount_open_price;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public String getVip_title() {
        return vip_title;
    }

    public void setVip_title(String vip_title) {
        this.vip_title = vip_title;
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

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public String getStart_time_text() {
        return start_time_text;
    }

    public void setStart_time_text(String start_time_text) {
        this.start_time_text = start_time_text;
    }

    public String getEnd_time_text() {
        return end_time_text;
    }

    public void setEnd_time_text(String end_time_text) {
        this.end_time_text = end_time_text;
    }
}
