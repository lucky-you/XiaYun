package com.goulala.xiayun.mycenter.model;


import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/17
 * function  : 总订单的信息（存在着多订单，多店铺）
 */
public class OrderDateMessage {

    /**
     * id : 306
     * pay_no : 20180917120529081113500388149029
     * shop_order : 20180917120529081792200335200398
     * item_order : 20180917120529081956900476838214
     * user_id : 23
     * merchant_id : 27
     * item_id : 26
     * active_id : 55
     * num : 5
     * discount_money : 0.00
     * unit_price : 120.00
     * pay_money : 607.00
     * money : 600.00
     * remark : 11111111
     * status : 0
     * createtime : 1537157129
     * updatetime : 1537157129
     * freight : 7.00
     * coupon_money : 0.00
     * user_coupon_id : 0
     * fail_status : 0
     * after_status : 0
     * sum_pay_money : 2114.00
     * sum_pay_num : 12
     * is_dismantle : 0
     * shop : [{"shop_order":"20180917120529081792200335200398","status":0,"create_time":"2018-09-17 12:05:29","merchant":"供应商J","sum_pay_money":1806,"sum_pay_num":9,"list":[{"id":306,"pay_no":"20180917120529081113500388149029","shop_order":"20180917120529081792200335200398","item_order":"20180917120529081956900476838214","user_id":23,"merchant_id":27,"item_id":26,"active_id":55,"num":5,"discount_money":"0.00","unit_price":"120.00","pay_money":"607.00","money":"600.00","remark":"11111111","status":0,"createtime":1537157129,"updatetime":1537157129,"freight":"7.00","coupon_money":"0.00","user_coupon_id":0,"fail_status":0,"after_status":0,"item":{"id":26,"name":"10号xx商品又是木林森","subname":"","description":"简介10号xx商品又是木林森","stock":88,"merchant_id":27,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180904/FpWGl0W1rEUX39B-mg349CLUIqm8.jpg","http://xyfile.nacy.cc/uploads/20180904/FuncPgt-AMIx9Q5vsOX6ogXe779a.jpg","http://xyfile.nacy.cc/uploads/20180904/Fp3tYKSukFrIeV6kTCwidBSz4iSO.jpg"],"broadcast_images_ids":"","price":"40.00","original_price":"50.00","member_price":"45.00","detail_images":["http://xyfile.nacy.cc/uploads/20180904/FphKrIh1jkfDQe2V17G5z25G4HZH.jpg","http://xyfile.nacy.cc/uploads/20180904/Fn4tuVqSCEZV3TAWeUI4Y29lgy3A.jpg","http://xyfile.nacy.cc/uploads/20180904/FgRRA3w3SjwWcME9wMinG3J_5gek.jpg","http://xyfile.nacy.cc/uploads/20180904/FjgbId6tyHCOfkntpyjeOSd38o5B.jpg"],"detail_images_ids":"","createtime":1536047267,"updatetime":1536047267,"brand":"10号某某","shelf_life":"7天","origin":"湖北潜江","specification":"500G/袋","express_bancity_ids":"5","item_tag_ids":"2,1","status":1,"province_id":1709,"city_id":1710,"storage":"冷藏","taste":"蒜蓉","freight":"7.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180904/FpWGl0W1rEUX39B-mg349CLUIqm8.jpg?imageView/2/w/100/h/100","province":"湖北省","city":"武汉市","sells":28,"active":[{"id":55,"active_category_id":3,"name":"品质优选","smallimage":null,"largeimage":null,"starttime":1537059600,"endtime":1538269200,"preferential_id":0,"trade_amount":"0.00","trade_num":0,"discount_amount":"0.00","coupon_id":0,"sell_count":0,"item_min":1,"item_max":3,"status":1,"weigh":0,"createtime":1536631041,"updatetime":1536982687,"sell_amount":0,"sell_num":0,"sell_title":"品质优选","sell_rule":"","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":55,"item_id":26,"price":"120.00","member_price":"120.00","num":88,"stock":88}]},"apply":[]},{"id":308,"pay_no":"20180917120529081113500388149029","shop_order":"20180917120529081792200335200398","item_order":"20180917120529082590500329465135","user_id":23,"merchant_id":27,"item_id":42,"active_id":55,"num":4,"discount_money":"0.00","unit_price":"300.00","pay_money":"1199.00","money":"1200.00","remark":"333333","status":0,"createtime":1537157129,"updatetime":1537157129,"freight":"-1.00","coupon_money":"0.00","user_coupon_id":0,"fail_status":0,"after_status":0,"item":{"id":42,"name":"26号XX商品","subname":"","description":"简介零零零零","stock":888,"merchant_id":27,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180904/Fk7Yt9FR_SziMGZrPBigZha55Dil.jpg","http://xyfile.nacy.cc/uploads/20180904/Fkk565KEWZD9FO6hk8CHOl5zpGZn.jpg"],"broadcast_images_ids":"","price":"88.00","original_price":"99.00","member_price":"95.00","detail_images":["http://xyfile.nacy.cc/uploads/20180904/Fv_5dpX8VsstcQvD6T9CFVvCBZsB.jpg","http://xyfile.nacy.cc/uploads/20180904/FjNeIu9vWQlV61d7H1oLqwE2j009.jpg","http://xyfile.nacy.cc/uploads/20180904/FktDwcrC_shOCDvPC3rG41Nsr2qU.jpg","http://xyfile.nacy.cc/uploads/20180904/Ft3gS9Z7oljTg1dcwzyu9Z9Us-Sv.jpg","http://xyfile.nacy.cc/uploads/20180904/Fl3eBbx2zglEq_Jt_J_c7yIyXjE8.jpg"],"detail_images_ids":"","createtime":1536051460,"updatetime":1536051460,"brand":"26号某某","shelf_life":"7天","origin":"湖北潜江","specification":"500G/袋","express_bancity_ids":"4","item_tag_ids":"3","status":1,"province_id":1709,"city_id":1710,"storage":"冷藏","taste":"蒜蓉","freight":"-1.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180904/Fk7Yt9FR_SziMGZrPBigZha55Dil.jpg?imageView/2/w/100/h/100","province":"湖北省","city":"武汉市","sells":99,"active":[{"id":55,"active_category_id":3,"name":"品质优选","smallimage":null,"largeimage":null,"starttime":1537059600,"endtime":1538269200,"preferential_id":0,"trade_amount":"0.00","trade_num":0,"discount_amount":"0.00","coupon_id":0,"sell_count":0,"item_min":1,"item_max":3,"status":1,"weigh":0,"createtime":1536631041,"updatetime":1536982687,"sell_amount":0,"sell_num":0,"sell_title":"品质优选","sell_rule":"","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":55,"item_id":42,"price":"300.00","member_price":"280.00","num":888,"stock":888}]},"apply":[]}]},{"shop_order":"20180917120529082188000415893178","status":0,"create_time":"2018-09-17 12:05:29","merchant":"供应商Q","sum_pay_money":"308.00","sum_pay_num":3,"list":[{"id":307,"pay_no":"20180917120529081113500388149029","shop_order":"20180917120529082188000415893178","item_order":"20180917120529082363100413331456","user_id":23,"merchant_id":17,"item_id":41,"active_id":55,"num":3,"discount_money":"0.00","unit_price":"100.00","pay_money":"308.00","money":"300.00","remark":"22222222","status":0,"createtime":1537157129,"updatetime":1537157129,"freight":"8.00","coupon_money":"0.00","user_coupon_id":0,"fail_status":0,"after_status":0,"item":{"id":41,"name":"25号xX撒打卡时间大苏打","subname":"","description":"简介啊萨达打撒十大","stock":888,"merchant_id":17,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180904/Fv7lHQmo9pZf_xH7OUcQZlbMUh8X.jpg","http://xyfile.nacy.cc/uploads/20180904/Fr5I-m_0xFSCf9h8Qs1RY-pyW2_a.jpg","http://xyfile.nacy.cc/uploads/20180904/Fsoe1wDevN2VHkx7hOywjrcC1h-3.jpg"],"broadcast_images_ids":"","price":"50.00","original_price":"60.00","member_price":"55.00","detail_images":["http://xyfile.nacy.cc/uploads/20180904/FvMCeGYiKuyQIRMcNa9kt5dacU2m.jpg","http://xyfile.nacy.cc/uploads/20180904/FmLgmODtKcjUGwbJEAk8Ndijzfok.jpg","http://xyfile.nacy.cc/uploads/20180904/Fl78s0Nd7ulhmUcrNqaUSn4A88qt.jpg"],"detail_images_ids":"","createtime":1536050590,"updatetime":1536050590,"brand":"25号某某","shelf_life":"7天","origin":"湖北潜江","specification":"500G/袋","express_bancity_ids":"4,2","item_tag_ids":"3","status":1,"province_id":1709,"city_id":1710,"storage":"冷藏","taste":"蒜蓉","freight":"8.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180904/Fv7lHQmo9pZf_xH7OUcQZlbMUh8X.jpg?imageView/2/w/100/h/100","province":"湖北省","city":"武汉市","sells":3,"active":[{"id":55,"active_category_id":3,"name":"品质优选","smallimage":null,"largeimage":null,"starttime":1537059600,"endtime":1538269200,"preferential_id":0,"trade_amount":"0.00","trade_num":0,"discount_amount":"0.00","coupon_id":0,"sell_count":0,"item_min":1,"item_max":3,"status":1,"weigh":0,"createtime":1536631041,"updatetime":1536982687,"sell_amount":0,"sell_num":0,"sell_title":"品质优选","sell_rule":"","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":55,"item_id":41,"price":"100.00","member_price":"80.00","num":888,"stock":888}]},"apply":[]}]}]
     */

    private int id;
    private String pay_no;
    private String shop_order;
    private String item_order;
    private int user_id;
    private int merchant_id;
    private int item_id;
    private int active_id;
    private int num;
    private double discount_money;
    private double unit_price;
    private double pay_money;
    private double money;
    private String remark;
    private int status;
    private long createtime;
    private long updatetime;
    private double freight;
    private double coupon_money;
    private int user_coupon_id;
    private int fail_status;
    private int after_status;
    private double sum_pay_money;
    private String sum_pay_num;
    private int is_dismantle;
    private String completetime_text;
    private long callbacktime;
    private List<ShopDateMessage> shop;


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

    public int getActive_id() {
        return active_id;
    }

    public void setActive_id(int active_id) {
        this.active_id = active_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getDiscount_money() {
        return discount_money;
    }

    public void setDiscount_money(double discount_money) {
        this.discount_money = discount_money;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public double getPay_money() {
        return pay_money;
    }

    public void setPay_money(double pay_money) {
        this.pay_money = pay_money;
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

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(double coupon_money) {
        this.coupon_money = coupon_money;
    }

    public int getUser_coupon_id() {
        return user_coupon_id;
    }

    public void setUser_coupon_id(int user_coupon_id) {
        this.user_coupon_id = user_coupon_id;
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

    public double getSum_pay_money() {
        return sum_pay_money;
    }

    public void setSum_pay_money(double sum_pay_money) {
        this.sum_pay_money = sum_pay_money;
    }

    public String getSum_pay_num() {
        return sum_pay_num;
    }

    public void setSum_pay_num(String sum_pay_num) {
        this.sum_pay_num = sum_pay_num;
    }

    public int getIs_dismantle() {
        return is_dismantle;
    }

    public void setIs_dismantle(int is_dismantle) {
        this.is_dismantle = is_dismantle;
    }

    public String getCompletetime_text() {
        return completetime_text;
    }

    public void setCompletetime_text(String completetime_text) {
        this.completetime_text = completetime_text;
    }

    public long getCallbacktime() {
        return callbacktime;
    }

    public void setCallbacktime(long callbacktime) {
        this.callbacktime = callbacktime;
    }

    public List<ShopDateMessage> getShop() {
        return shop;
    }

    public void setShop(List<ShopDateMessage> shop) {
        this.shop = shop;
    }
}
