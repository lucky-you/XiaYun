package com.goulala.xiayun.shopcar.model;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/28
 * @function : 购物车的信息（最外层的活动）
 */
public class ShopCarMessage {

    /**
     * id : 32
     * active_category_id : 2
     * name : 满3件减20
     * smallimage : http://xyfile.nacy.cc/uploads/20180906/Fnsm_ZBIXvZjnK7BCRWAPbzDdzLZ.png
     * largeimage : http://xyfile.nacy.cc/uploads/20180906/Fnsm_ZBIXvZjnK7BCRWAPbzDdzLZ.png
     * starttime : 1535299200
     * endtime : 1535385600
     * preferential_id : 2
     * trade_amount : 0.00
     * trade_num : 3
     * discount_amount : 20.00
     * coupon_id : 0
     * sell_count : 200
     * item_min : 1
     * item_max : 20
     * status : 1
     * weigh : 0
     * createtime : 1535352763
     * updatetime : 1536200900
     * sell_amount : 2000
     * sell_num : 10
     * sell_title : 限时促销
     * sell_rule : 满3件减20.00
     * sell_desc : 已购满10件,已减20.00
     * title : 作废,请及时修改
     * desc : 作废,请及时修改
     * active_item_name :
     * active_id : 32
     * item_id : 7
     * price : 200.00
     * member_price : 200.00
     * num : 300
     * cell : 1
     * merchant : [{"id":2,"admin_id":6,"user_id":2,"name":"供应商U","contacts":"张浩浩","bank_name":"6","bank_num":"65214552155884725","payee_username":"张浩浩","last_login_time":0,"status":1,"createtime":1534576210,"updatetime":1536033204,"cell":2,"item":[{"id":7,"name":"洪湖香十三香小龙香虾","subname":"","description":"夜宵首选，回味无穷","stock":300,"merchant_id":2,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png","http://xyfile.nacy.cc/uploads/20180828/Fq7QfqOv7fU4fnbfpd7imBFABsbK.png"],"broadcast_images_ids":"","price":"100.00","original_price":"80.00","member_price":"60.00","detail_images":["http://xyfile.nacy.cc/uploads/20180828/FgWCgFmO_np4OO9b9QiYnHl9bCm7.png","http://xyfile.nacy.cc/uploads/20180828/Fscgh8WwxWFc6rWMwo_QuukuvPBc.png","http://xyfile.nacy.cc/uploads/20180828/FpZUB7MzkeDYSju0aUIEtP2C5e5J.png","http://xyfile.nacy.cc/uploads/20180828/Fv7JDvuKxa41IGx39SeAZMQcLxD1.png","http://xyfile.nacy.cc/uploads/20180828/Fn_6pzi2UG4f8I6Bm3e4h_ES2QGX.png","http://xyfile.nacy.cc/uploads/20180828/FscY5SHgfOTG7sEkS7kx67COxvwf.png","http://xyfile.nacy.cc/uploads/20180828/FrgmPOeesJj6I3PhercfGlJNTZ0G.png","http://xyfile.nacy.cc/uploads/20180828/FgHLHjTWB6WFZ-CinoKTP6ZOq30t.png","http://xyfile.nacy.cc/uploads/20180828/FvkKZVwxoBp5wh7C5wvt-aCmnueg.png"],"detail_images_ids":"","createtime":1534160419,"updatetime":1535456560,"brand":"洪湖湿地","shelf_life":"18个月","origin":"湖北洪湖","specification":"900g/袋","express_bancity_ids":"3,4,2,5","item_tag_ids":"2,3,1","status":1,"province_id":1168,"city_id":1196,"storage":"常温保存","taste":"香辣","freight":"10.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png?imageView/2/w/100/h/100","province":"福建省","city":"三明市","sells":53,"active":[{"id":32,"active_category_id":2,"name":"满3件减20","smallimage":"http://xyfile.nacy.cc/uploads/20180906/Fnsm_ZBIXvZjnK7BCRWAPbzDdzLZ.png","largeimage":"http://xyfile.nacy.cc/uploads/20180906/Fnsm_ZBIXvZjnK7BCRWAPbzDdzLZ.png","starttime":1535299200,"endtime":1535385600,"preferential_id":2,"trade_amount":"0.00","trade_num":3,"discount_amount":"20.00","coupon_id":0,"sell_count":200,"item_min":1,"item_max":20,"status":1,"weigh":0,"createtime":1535352763,"updatetime":1536200900,"sell_amount":0,"sell_num":0,"sell_title":"限时促销","sell_rule":"满3件减20.00","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":32,"item_id":7,"price":"200.00","member_price":"200.00","num":300}],"item_num":10,"cart_id":25,"cell":3}]}]
     */

    private int id;
    private int active_category_id;
    private String name;
    private String smallimage;
    private String largeimage;
    private int starttime;
    private int endtime;
    private int preferential_id;
    private String trade_amount;
    private int trade_num;
    private String discount_amount;
    private int coupon_id;
    private int sell_count;
    private int item_min;
    private int item_max;
    private int status;
    private int weigh;
    private int createtime;
    private int updatetime;
    private int sell_amount;
    private int sell_num;
    private String sell_title;
    private String sell_rule;
    private String sell_desc;
    private String title;
    private String desc;
    private String active_item_name;
    private int active_id;
    private int item_id;
    private String price;
    private String member_price;
    private int num;
    private int cell;
    private List<ShopCarStoreBean> merchant;

    private boolean isActiveSelect; //活动是否选择

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive_category_id() {
        return active_category_id;
    }

    public void setActive_category_id(int active_category_id) {
        this.active_category_id = active_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallimage() {
        return smallimage;
    }

    public void setSmallimage(String smallimage) {
        this.smallimage = smallimage;
    }

    public String getLargeimage() {
        return largeimage;
    }

    public void setLargeimage(String largeimage) {
        this.largeimage = largeimage;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public int getPreferential_id() {
        return preferential_id;
    }

    public void setPreferential_id(int preferential_id) {
        this.preferential_id = preferential_id;
    }

    public String getTrade_amount() {
        return trade_amount;
    }

    public void setTrade_amount(String trade_amount) {
        this.trade_amount = trade_amount;
    }

    public int getTrade_num() {
        return trade_num;
    }

    public void setTrade_num(int trade_num) {
        this.trade_num = trade_num;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getSell_count() {
        return sell_count;
    }

    public void setSell_count(int sell_count) {
        this.sell_count = sell_count;
    }

    public int getItem_min() {
        return item_min;
    }

    public void setItem_min(int item_min) {
        this.item_min = item_min;
    }

    public int getItem_max() {
        return item_max;
    }

    public void setItem_max(int item_max) {
        this.item_max = item_max;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
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

    public int getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(int sell_amount) {
        this.sell_amount = sell_amount;
    }

    public int getSell_num() {
        return sell_num;
    }

    public void setSell_num(int sell_num) {
        this.sell_num = sell_num;
    }

    public String getSell_title() {
        return sell_title;
    }

    public void setSell_title(String sell_title) {
        this.sell_title = sell_title;
    }

    public String getSell_rule() {
        return sell_rule;
    }

    public void setSell_rule(String sell_rule) {
        this.sell_rule = sell_rule;
    }

    public String getSell_desc() {
        return sell_desc;
    }

    public void setSell_desc(String sell_desc) {
        this.sell_desc = sell_desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getActive_item_name() {
        return active_item_name;
    }

    public void setActive_item_name(String active_item_name) {
        this.active_item_name = active_item_name;
    }

    public int getActive_id() {
        return active_id;
    }

    public void setActive_id(int active_id) {
        this.active_id = active_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMember_price() {
        return member_price;
    }

    public void setMember_price(String member_price) {
        this.member_price = member_price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public List<ShopCarStoreBean> getMerchant() {
        return merchant;
    }

    public void setMerchant(List<ShopCarStoreBean> merchant) {
        this.merchant = merchant;
    }

    public boolean isActiviteSelect() {
        return isActiveSelect;
    }

    public void setActiveSelect(boolean activeSelect) {
        isActiveSelect = activeSelect;
    }

    @Override
    public String toString() {
        return "ShopCarMessage{" +
                "id=" + id +
                ", active_category_id=" + active_category_id +
                ", name='" + name + '\'' +
                ", smallimage='" + smallimage + '\'' +
                ", largeimage='" + largeimage + '\'' +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", preferential_id=" + preferential_id +
                ", trade_amount='" + trade_amount + '\'' +
                ", trade_num=" + trade_num +
                ", discount_amount='" + discount_amount + '\'' +
                ", coupon_id=" + coupon_id +
                ", sell_count=" + sell_count +
                ", item_min=" + item_min +
                ", item_max=" + item_max +
                ", status=" + status +
                ", weigh=" + weigh +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", sell_amount=" + sell_amount +
                ", sell_num=" + sell_num +
                ", sell_title='" + sell_title + '\'' +
                ", sell_rule='" + sell_rule + '\'' +
                ", sell_desc='" + sell_desc + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", active_item_name='" + active_item_name + '\'' +
                ", active_id=" + active_id +
                ", item_id=" + item_id +
                ", price='" + price + '\'' +
                ", member_price='" + member_price + '\'' +
                ", num=" + num +
                ", cell=" + cell +
                ", merchant=" + merchant +
                ", isActivieSelect=" + isActiveSelect +
                '}';
    }
}
