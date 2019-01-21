package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/10/31
 * function  :
 */
public class AttachBean {


    /**
     * express_number : 3242343242
     * item_img : http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png?imageView/2/w/100/h/100
     * name : 洪湖香十三香小龙香虾
     */
    private String item_img;
    private String name;
    private String express_number;  //物流单号
    private String express_company_id; //物流公司的id
    private String shop_order;  // 商品的单号
    private String pay_no; //支付的订单编号
    private String money; //系统消息的提现金额

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public String getExpress_company_id() {
        return express_company_id;
    }

    public void setExpress_company_id(String express_company_id) {
        this.express_company_id = express_company_id;
    }

    public String getShop_order() {
        return shop_order;
    }

    public void setShop_order(String shop_order) {
        this.shop_order = shop_order;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
