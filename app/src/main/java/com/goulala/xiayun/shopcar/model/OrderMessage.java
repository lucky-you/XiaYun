package com.goulala.xiayun.shopcar.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/11
 * function  : 确认下单
 */
public class OrderMessage {


    /**
     * list : [{"merchant":"供应商F","list":[{"id":28,"name":"12号xx电吹风","subname":"","description":"简介12号xx电吹风12号xx电吹风12号xx电吹风12号xx电吹风","stock":88,"merchant_id":23,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180904/FnyolV54OVG-_J4teWn2TMXkqaVk.jpg","http://xyfile.nacy.cc/uploads/20180904/Fn8_3udyZxFAf-he83gZfv6-QJ62.jpg","http://xyfile.nacy.cc/uploads/20180904/FpVW2SY2fMVJrlC10BvNvK--ia2Q.jpg"],"broadcast_images_ids":"","price":"66.00","original_price":"86.00","member_price":"76.00","detail_images":["http://xyfile.nacy.cc/uploads/20180904/FtaNf4AShMqddPZZOJ3PbGt-wzGT.jpg","http://xyfile.nacy.cc/uploads/20180904/FiAJIpyYZcNg75_TM6kv7NP18MTi.jpg","http://xyfile.nacy.cc/uploads/20180904/FjeTUeqjYnCrPB9ze3oMbKf0rZPm.jpg","http://xyfile.nacy.cc/uploads/20180904/Fv0GNbKdF6CdyDoYw-_x1L5NB1HF.jpg"],"detail_images_ids":"","createtime":1536049116,"updatetime":1536052282,"brand":"12号某某","shelf_life":"7天","origin":"湖北潜江","specification":"500G/袋","express_bancity_ids":"3","item_tag_ids":"2,4","status":1,"province_id":1709,"city_id":1710,"storage":"冷藏","taste":"风的味道","freight":"0.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180904/FnyolV54OVG-_J4teWn2TMXkqaVk.jpg?imageView/2/w/100/h/100","province":"湖北省","city":"武汉市","sells":64,"active":[{"id":54,"active_category_id":2,"name":"超值热卖","smallimage":"http://xyfile.nacy.cc/uploads/20180911/FkGVCWiTlyxOAHzym5oMRsEAV0zK.png","largeimage":"http://xyfile.nacy.cc/uploads/20180911/FleIY6jWu_DNauteWKyf90-DGKNJ.png","starttime":1536627600,"endtime":1536714000,"preferential_id":1,"trade_amount":"120.00","trade_num":0,"discount_amount":"100.00","coupon_id":0,"sell_count":0,"item_min":1,"item_max":100,"status":1,"weigh":0,"createtime":1536630986,"updatetime":1536633060,"sell_amount":0,"sell_num":0,"sell_title":"限时促销","sell_rule":"满120.00减100","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":54,"item_id":28,"price":"100.00","member_price":"100.00","num":88}]}]}]
     * total_freight : 0.00
     * total_price : 100
     * coupon_price : 0
     * discount_member_price : 0
     * pay_price : 100
     * pay_no: "20180912113053062078900728700982",
     * plus_str:您是Plus会员，全场8折起，此商品已为您节省20元
     * is_vip:boolean
     */

    private double total_freight;
    private String pay_no;
    private double total_price;
    private double coupon_price;
    private double discount_member_price;
    private double pay_price;
    private String plus_str;
    private List<MerchantMessage> list;
    private boolean is_vip;

    public double getTotal_freight() {
        return total_freight;
    }

    public void setTotal_freight(double total_freight) {
        this.total_freight = total_freight;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(double coupon_price) {
        this.coupon_price = coupon_price;
    }

    public double getDiscount_member_price() {
        return discount_member_price;
    }

    public void setDiscount_member_price(double discount_member_price) {
        this.discount_member_price = discount_member_price;
    }

    public double getPay_price() {
        return pay_price;
    }

    public void setPay_price(double pay_price) {
        this.pay_price = pay_price;
    }

    public String getPlus_str() {
        return plus_str;
    }

    public void setPlus_str(String plus_str) {
        this.plus_str = plus_str;
    }

    public List<MerchantMessage> getList() {
        return list;
    }

    public void setList(List<MerchantMessage> list) {
        this.list = list;
    }

    public boolean isIs_vip() {
        return is_vip;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }
}
