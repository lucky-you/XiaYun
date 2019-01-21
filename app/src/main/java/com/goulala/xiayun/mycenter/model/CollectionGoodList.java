package com.goulala.xiayun.mycenter.model;

import com.goulala.xiayun.home.model.GoodActivityBean;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/28
 * @function : 收藏和足迹的列表
 */
public class CollectionGoodList {

    /**
     * id : 34
     * item_id : 34
     * user_id : 23
     * createtime : 1536049713
     * name : 18号xx商品你猜卖多少钱
     * subname :
     * description : 简介18号xx商品你猜卖多少钱
     * stock : 333
     * merchant_id : 13
     * broadcast_images : ["http://xyfile.nacy.cc/uploads/20180904/Fgo8hgXavtg7tN1awgRBU-JO3xPp.jpg","http://xyfile.nacy.cc/uploads/20180904/FgitO5ApbH7EBQ1vVXbAGI4mp9-O.jpg"]
     * broadcast_images_ids :
     * price : 200.00
     * original_price : 300.00
     * member_price : 250.00
     * detail_images : ["http://xyfile.nacy.cc/uploads/20180904/FjudxWEihLINjO4_YKsV72S4jJxz.jpg","http://xyfile.nacy.cc/uploads/20180904/FmKt6lRJLrjfQGw9Yw37BVK2XZs1.jpg","http://xyfile.nacy.cc/uploads/20180904/FvzFdbYIK2rC2LfwJ_JIEAtNzYvA.jpg","http://xyfile.nacy.cc/uploads/20180904/FqLYXU2kv0-CaSYLlgu30Qvy0PEN.jpg"]
     * detail_images_ids :
     * updatetime : 1536049713
     * brand : 18号某某
     * shelf_life : 7天
     * origin : 湖北潜江
     * specification : 500G/袋
     * express_bancity_ids :
     * item_tag_ids : 1
     * status : 1
     * province_id : 1709
     * city_id : 1710
     * storage : 冷藏
     * taste : 蒜蓉
     * freight : -1.00
     * sales : 0
     * smallimage : http://xyfile.nacy.cc/uploads/20180904/Fgo8hgXavtg7tN1awgRBU-JO3xPp.jpg?imageView/2/w/100/h/100
     * province : 湖北省
     * city : 武汉市
     * sells : 48
     * active : [{"id":58,"active_category_id":2,"name":"超值热卖2","smallimage":"http://xyfile.nacy.cc/uploads/20180911/FkGVCWiTlyxOAHzym5oMRsEAV0zK.png","largeimage":"http://xyfile.nacy.cc/uploads/20180911/FleIY6jWu_DNauteWKyf90-DGKNJ.png","starttime":1536547487,"endtime":1536547490,"preferential_id":1,"trade_amount":"100.00","trade_num":0,"discount_amount":"80.00","coupon_id":0,"sell_count":0,"item_min":2,"item_max":2,"status":1,"weigh":0,"createtime":1536633928,"updatetime":1536633928,"sell_amount":0,"sell_num":0,"sell_title":"限时促销","sell_rule":"满100.00减80","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":58,"item_id":34,"price":"100.00","member_price":"80.00","num":333,"stock":333}]
     */

    private int id;
    private int item_id;
    private int user_id;
    private int createtime;
    private String name;
    private String subname;
    private String description;
    private int stock;
    private int merchant_id;
    private String broadcast_images_ids;
    private String price;
    private String original_price;
    private String member_price;
    private String detail_images_ids;
    private int updatetime;
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
    private String smallimage;
    private String province;
    private String city;
    private int sells;
    private boolean isSelect;
    private List<String> broadcast_images;
    private List<String> detail_images;
    private List<GoodActivityBean> active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getMember_price() {
        return member_price;
    }

    public void setMember_price(String member_price) {
        this.member_price = member_price;
    }

    public String getDetail_images_ids() {
        return detail_images_ids;
    }

    public void setDetail_images_ids(String detail_images_ids) {
        this.detail_images_ids = detail_images_ids;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
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


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
}
