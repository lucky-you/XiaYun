package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/18
 * function  : 订单的收货地址，在订单详情中使用
 */
public class OrderAddressBean {


    /**
     * id : 395
     * shop_order : 35091966400262673744
     * consignee_name : 周小川
     * consignee_mobile : 13677197784
     * consignee_address : 江西省南昌市青山湖区朝阳区朝阳门北京饭店0668室
     * express_company_id : 4
     * express_number : 3242343242
     * item_order : 35091967500144546959
     * pay_no : 35088066000623444986
     * deliverytime : 1540969857
     * createtime : 1540969835
     * updatetime : 1540969857
     */

    private int id;
    private String shop_order;
    private String consignee_name;
    private String consignee_mobile;
    private String consignee_address;
    private int express_company_id;
    private String express_number;
    private String item_order;
    private String pay_no;
    private long deliverytime;
    private long createtime;
    private long updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop_order() {
        return shop_order;
    }

    public void setShop_order(String shop_order) {
        this.shop_order = shop_order;
    }

    public String getConsignee_name() {
        return consignee_name;
    }

    public void setConsignee_name(String consignee_name) {
        this.consignee_name = consignee_name;
    }

    public String getConsignee_mobile() {
        return consignee_mobile;
    }

    public void setConsignee_mobile(String consignee_mobile) {
        this.consignee_mobile = consignee_mobile;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public int getExpress_company_id() {
        return express_company_id;
    }

    public void setExpress_company_id(int express_company_id) {
        this.express_company_id = express_company_id;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public String getItem_order() {
        return item_order;
    }

    public void setItem_order(String item_order) {
        this.item_order = item_order;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public long getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(long deliverytime) {
        this.deliverytime = deliverytime;
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
}
