package com.goulala.xiayun.home.model;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/15
 * @function : 商品详情的数据(全局通用)
 */
public class GoodsDetailsMessage {

    /**
     * id : 28
     * name : 12号xx电吹风
     * subname :
     * description : 简介12号xx电吹风12号xx电吹风12号xx电吹风12号xx电吹风
     * stock : 88
     * merchant_id : 23
     * broadcast_images : ["http://xyfile.nacy.cc/uploads/20180904/FnyolV54OVG-_J4teWn2TMXkqaVk.jpg","http://xyfile.nacy.cc/uploads/20180904/Fn8_3udyZxFAf-he83gZfv6-QJ62.jpg","http://xyfile.nacy.cc/uploads/20180904/FpVW2SY2fMVJrlC10BvNvK--ia2Q.jpg"]
     * broadcast_images_ids :
     * price : 66.00
     * original_price : 86.00
     * member_price : 76.00
     * detail_images : ["http://xyfile.nacy.cc/uploads/20180904/FtaNf4AShMqddPZZOJ3PbGt-wzGT.jpg","http://xyfile.nacy.cc/uploads/20180904/FiAJIpyYZcNg75_TM6kv7NP18MTi.jpg","http://xyfile.nacy.cc/uploads/20180904/FjeTUeqjYnCrPB9ze3oMbKf0rZPm.jpg","http://xyfile.nacy.cc/uploads/20180904/Fv0GNbKdF6CdyDoYw-_x1L5NB1HF.jpg"]
     * detail_images_ids :
     * brand : 12号某某
     * shelf_life : 7天
     * origin : 湖北潜江
     * specification : 500G/袋
     * express_bancity_ids : 3
     * item_tag_ids : 2,4
     * status : 1
     * province_id : 1709
     * city_id : 1710
     * storage : 冷藏
     * taste : 风的味道
     * freight : 0.00
     * sales : 0
     * createtime : 1536049116
     * updatetime : 1536052282
     * smallimage : http://xyfile.nacy.cc/uploads/20180904/FnyolV54OVG-_J4teWn2TMXkqaVk.jpg?imageView/2/w/100/h/100
     * province : 湖北省
     * city : 武汉市
     * sells : 88
     * active : [{"id":54,"active_category_id":2,"name":"超值热卖","smallimage":"http://xyfile.nacy.cc/uploads/20180911/FkGVCWiTlyxOAHzym5oMRsEAV0zK.png","largeimage":"http://xyfile.nacy.cc/uploads/20180911/FleIY6jWu_DNauteWKyf90-DGKNJ.png","starttime":1536627600,"endtime":1536714000,"preferential_id":1,"trade_amount":"120.00","trade_num":0,"discount_amount":"100.00","coupon_id":0,"sell_count":0,"item_min":1,"item_max":100,"status":1,"weigh":0,"createtime":1536630986,"updatetime":1536633060,"sell_amount":0,"sell_num":0,"sell_title":"限时促销","sell_rule":"满120.00减100","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":54,"item_id":28,"price":"100.00","member_price":"100.00","num":88,"stock":88,"starttime_text":"2018-09-11 09:00:00","endtime_text":"2018-09-12 09:00:00","status_text":"Status 1"}]
     * favorite : true
     * vip : []
     */

    private int id;
    private String name;
    private String subname;
    private String description;
    private int stock;
    private int merchant_id;
    private String broadcast_images_ids;
    private double price;
    private double original_price;
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
    private double freight;
    private int sales;
    private int createtime;
    private int updatetime;
    private String smallimage;
    private String province;
    private String city;
    private String express_str;
    private int sells;
    private double share_fee_mix;
    private double share_fee_max;
    private boolean favorite;
    private List<String> broadcast_images;
    private List<String> detail_images;
    private List<GoodActivityBean> active;
    private boolean vip;

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

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
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

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
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

    public String getExpress_str() {
        return express_str;
    }

    public void setExpress_str(String express_str) {
        this.express_str = express_str;
    }

    public int getSells() {
        return sells;
    }

    public void setSells(int sells) {
        this.sells = sells;
    }


    public double getShare_fee_mix() {
        return share_fee_mix;
    }

    public void setShare_fee_mix(double share_fee_mix) {
        this.share_fee_mix = share_fee_mix;
    }

    public double getShare_fee_max() {
        return share_fee_max;
    }

    public void setShare_fee_max(double share_fee_max) {
        this.share_fee_max = share_fee_max;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
