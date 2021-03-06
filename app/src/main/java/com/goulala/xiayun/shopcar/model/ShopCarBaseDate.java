package com.goulala.xiayun.shopcar.model;

import com.goulala.xiayun.home.model.GoodActivityBean;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/17
 * function  : 购物车的数据
 */
public class ShopCarBaseDate {


    /**
     * id : 43
     * name : 27号XX商品名称不能为空
     * subname :
     * description : 简介不能为空
     * stock : 99
     * merchant_id : 23
     * broadcast_images : ["http://xyfile.nacy.cc/uploads/20180904/Foo1aZUFJ8QDDHTDaGvamWEmr4Ap.jpg","http://xyfile.nacy.cc/uploads/20180904/FnzZLXwciWjd2b-K0GRsA-u8PTp0.jpg","http://xyfile.nacy.cc/uploads/20180904/Fr4gWMQmDhkIxfflK9QZRg0Md9pL.jpg"]
     * broadcast_images_ids :
     * price : 100.00
     * original_price : 160.00
     * member_price : 140.00
     * detail_images : ["http://xyfile.nacy.cc/uploads/20180904/FuHdwmxv0ms-aOWln0fk6OgkzSkj.jpg","http://xyfile.nacy.cc/uploads/20180904/FuyEyB9kaD2Opi9roggQ-RDHdQl6.jpg","http://xyfile.nacy.cc/uploads/20180904/FvrPCkkvUVeHtevNND0NHvYcIbDw.jpg"]
     * detail_images_ids :
     * brand : 27号某某
     * shelf_life : 7天
     * origin : 湖北潜江
     * specification : 500G/袋
     * express_bancity_ids : 4
     * item_tag_ids : 3
     * status : 1
     * province_id : 1709
     * city_id : 1710
     * storage : 冷藏
     * taste : 蒜蓉
     * freight : 6.00
     * sales : 0
     * createtime : 1536051542
     * updatetime : 1536051542
     * smallimage : http://xyfile.nacy.cc/uploads/20180904/Foo1aZUFJ8QDDHTDaGvamWEmr4Ap.jpg?imageView/2/w/100/h/100
     * province : 湖北省
     * city : 武汉市
     * sells : 195
     * active : [{"id":58,"active_category_id":2,"name":"超值热卖2","smallimage":"http://xyfile.nacy.cc/uploads/20180911/FkGVCWiTlyxOAHzym5oMRsEAV0zK.png","largeimage":"http://xyfile.nacy.cc/uploads/20180911/FleIY6jWu_DNauteWKyf90-DGKNJ.png","starttime":1536547487,"endtime":1536547490,"preferential_id":1,"trade_amount":"100.00","trade_num":0,"discount_amount":"80.00","coupon_id":0,"sell_count":0,"item_min":2,"item_max":2,"status":1,"weigh":0,"createtime":1536633928,"updatetime":1536633928,"sell_amount":0,"sell_num":0,"sell_title":"限时促销","sell_rule":"满100.00减80","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":58,"item_id":43,"price":"80.00","member_price":"80.00","num":99,"stock":99,"starttime_text":"2018-09-10 10:44:47","endtime_text":"2018-09-10 10:44:50","status_text":"Status 1"}]
     * item_num : 1
     * cart_id : 124
     * cell : 3
     */


    private String sell_title;
    private String sell_rule;
    private String sell_desc;
    private String sell_link;

    private int id;
    private int active_id;  //活动的id
    private String name;
    private String subname;
    private String description;
    private int stock;
    private int merchant_id;
    private String broadcast_images_ids;
    private double price;
    private String original_price;
    private double member_price;
    private String detail_images_ids;
    private String brand;
    private String shelf_life;
    private String origin;
    private String specification;
    private String express_bancity_ids;
    private String item_tag_ids;
    private int status;
    private int province_id;
    private int city_id;
    private String storage;
    private String taste;
    private String freight;
    private int sales;
    private int createtime;
    private int updatetime;
    private String smallimage;
    private String province;
    private String city;
    private int sells;
    private int item_num;
    private int cart_id;
    private int cell;
    private List<String> broadcast_images;
    private List<String> detail_images;
    private List<GoodActivityBean> active;

    private boolean isGoodSelect; //是否被选择


    public int getActive_id() {
        return active_id;
    }

    public void setActive_id(int active_id) {
        this.active_id = active_id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getBroadcast_images_ids() {
        return broadcast_images_ids;
    }

    public void setBroadcast_images_ids(String broadcast_images_ids) {
        this.broadcast_images_ids = broadcast_images_ids;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public double getMember_price() {
        return member_price;
    }

    public void setMember_price(double member_price) {
        this.member_price = member_price;
    }

    public String getDetail_images_ids() {
        return detail_images_ids;
    }

    public void setDetail_images_ids(String detail_images_ids) {
        this.detail_images_ids = detail_images_ids;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getExpress_bancity_ids() {
        return express_bancity_ids;
    }

    public void setExpress_bancity_ids(String express_bancity_ids) {
        this.express_bancity_ids = express_bancity_ids;
    }

    public String getItem_tag_ids() {
        return item_tag_ids;
    }

    public void setItem_tag_ids(String item_tag_ids) {
        this.item_tag_ids = item_tag_ids;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
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

    public String getSmallimage() {
        return smallimage;
    }

    public void setSmallimage(String smallimage) {
        this.smallimage = smallimage;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSells() {
        return sells;
    }

    public void setSells(int sells) {
        this.sells = sells;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public List<String> getBroadcast_images() {
        return broadcast_images;
    }

    public void setBroadcast_images(List<String> broadcast_images) {
        this.broadcast_images = broadcast_images;
    }

    public List<String> getDetail_images() {
        return detail_images;
    }

    public void setDetail_images(List<String> detail_images) {
        this.detail_images = detail_images;
    }

    public List<GoodActivityBean> getActive() {
        return active;
    }

    public void setActive(List<GoodActivityBean> active) {
        this.active = active;
    }


    public boolean isGoodSelect() {
        return isGoodSelect;
    }

    public void setGoodSelect(boolean goodSelect) {
        isGoodSelect = goodSelect;
    }

    public String getSell_link() {
        return sell_link;
    }

    public void setSell_link(String sell_link) {
        this.sell_link = sell_link;
    }
}
