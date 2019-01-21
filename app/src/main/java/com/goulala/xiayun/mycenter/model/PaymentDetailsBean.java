package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/29
 * function  : 收支明细
 */
public class PaymentDetailsBean {


    /**
     * total : 3
     * per_page : 5
     * current_page : 1
     * last_page : 1
     * data : [{"user_id":23,"user_type":1,"source":2,"type":1,"pay_no":"20046390200459323154","money":"140.00","remark":"退款申请","status":2,"createtime":1538029580,"createip":"0.0.0.0","updatetime":1538029580,"source_text":"Source 2","type_text":"Type 1","status_text":"Status 2","callbacktime_text":"1970-01-01 08:00:00"},{"user_id":23,"user_type":1,"source":2,"type":2,"pay_no":"36023659800490189264","money":"200.00","remark":"余额支付","status":2,"createtime":1538134956,"createip":"0.0.0.0","updatetime":1538134956,"source_text":"Source 2","type_text":"Type 2","status_text":"Status 2","callbacktime_text":"2018-09-28 19:42:36"},{"user_id":23,"user_type":1,"source":2,"type":1,"pay_no":"36023659800423423423","money":"500.00","remark":"加钱","status":2,"createtime":1538134956,"createip":"0.0.0.0","updatetime":1538134956,"source_text":"Source 2","type_text":"Type 1","status_text":"Status 2","callbacktime_text":"2018-09-28 19:42:36"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<PaymentDetailsList> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
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

    public List<PaymentDetailsList> getData() {
        return data;
    }

    public void setData(List<PaymentDetailsList> data) {
        this.data = data;
    }
}
