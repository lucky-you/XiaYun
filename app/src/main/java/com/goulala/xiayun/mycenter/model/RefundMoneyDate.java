package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/20
 * function  : 退款的model
 */
public class RefundMoneyDate {


    /**
     * money : 108
     * num : 1
     * order : [{"id":347,"pay_no":"16092589900145162735","shop_order":"16093385000199726879","item_order":"16093386600290115286","user_id":23,"merchant_id":17,"item_id":41,"active_id":55,"num":1,"discount_money":"0.00","unit_price":"100.00","pay_money":"108.00","money":"100.00","remark":"下单","status":1,"createtime":1537318156,"updatetime":1537405999,"freight":"8.00","coupon_money":"0.00","user_coupon_id":0,"fail_status":0,"after_status":0,"completetime":0,"item":{"id":41,"name":"25号xX撒打卡时间大苏打","subname":"","description":"简介啊萨达打撒十大","stock":888,"merchant_id":17,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180904/Fv7lHQmo9pZf_xH7OUcQZlbMUh8X.jpg","http://xyfile.nacy.cc/uploads/20180904/Fr5I-m_0xFSCf9h8Qs1RY-pyW2_a.jpg","http://xyfile.nacy.cc/uploads/20180904/Fsoe1wDevN2VHkx7hOywjrcC1h-3.jpg"],"broadcast_images_ids":"","price":"50.00","original_price":"60.00","member_price":"55.00","detail_images":["http://xyfile.nacy.cc/uploads/20180904/FvMCeGYiKuyQIRMcNa9kt5dacU2m.jpg","http://xyfile.nacy.cc/uploads/20180904/FmLgmODtKcjUGwbJEAk8Ndijzfok.jpg","http://xyfile.nacy.cc/uploads/20180904/Fl78s0Nd7ulhmUcrNqaUSn4A88qt.jpg"],"detail_images_ids":"","createtime":1536050590,"updatetime":1536050590,"brand":"25号某某","shelf_life":"7天","origin":"湖北潜江","specification":"500G/袋","express_bancity_ids":"4,2","item_tag_ids":"3","status":1,"province_id":1709,"city_id":1710,"storage":"冷藏","taste":"蒜蓉","freight":"8.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180904/Fv7lHQmo9pZf_xH7OUcQZlbMUh8X.jpg?imageView/2/w/100/h/100","province":"湖北省","city":"武汉市","sells":88,"active":[{"id":55,"active_category_id":3,"name":"品质优选","smallimage":null,"largeimage":null,"starttime":1537059600,"endtime":1538269200,"preferential_id":0,"trade_amount":"0.00","trade_num":0,"discount_amount":"0.00","coupon_id":0,"sell_count":0,"item_min":1,"item_max":3,"status":1,"weigh":0,"createtime":1536631041,"updatetime":1536982687,"sell_amount":0,"sell_num":0,"sell_title":"品质优选","sell_rule":"","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":55,"item_id":41,"price":"100.00","member_price":"80.00","num":888,"stock":888,"starttime_text":"2018-09-16 09:00:00","endtime_text":"2018-09-30 09:00:00","status_text":"Status 1"}]},"completetime_text":"1970-01-01 08:00:00"}]
     */

    private double money;
    private int num;
    private int max_num;
    private List<ShopItemMessage> order;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMax_num() {
        return max_num;
    }

    public void setMax_num(int max_num) {
        this.max_num = max_num;
    }

    public List<ShopItemMessage> getOrder() {
        return order;
    }

    public void setOrder(List<ShopItemMessage> order) {
        this.order = order;
    }
}
