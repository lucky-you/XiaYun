package com.goulala.xiayun.home.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/27
 * @function :  单个商品的信息 （全局通用）
 */
public class GoodItemMessage implements Parcelable, MultiItemEntity {
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;

    /**
     * id : 8
     * name : 洪湖十三香小龙虾
     * subname :
     * description : 夜宵首选，回味无穷
     * stock : 200      //0是已售罄
     * merchant_id : 3
     * broadcast_images : ["http://xyfile.nacy.cc/uploads/20180813/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png","http://xyfile.nacy.cc/uploads/20180813/Fq7QfqOv7fU4fnbfpd7imBFABsbK.png","http://xyfile.nacy.cc/uploads/20180813/Fvs8Kf8npje5jO0Y3ZVf8aWZWZCO.png"]
     * broadcast_images_ids :
     * price : 200.00
     * original_price : 150.00
     * member_price : 120.00
     * detail_images : ["http://xyfile.nacy.cc/uploads/20180813/FuYJ80ZHVfAvFJEmCZjF_zZq7s-P.png","http://xyfile.nacy.cc/uploads/20180813/FsnGjLwtptvSXT4-EC17kAKdXT1U.png","http://xyfile.nacy.cc/uploads/20180813/FnizFMEUhm4zr5HVe2oAypvYmdhA.png","http://xyfile.nacy.cc/uploads/20180813/FhOoQI_PV5YVuTpVd3GhvEoihfqQ.png","http://xyfile.nacy.cc/uploads/20180813/Fr79uIhXzazAc_5raYiQ6ER1067s.png","http://xyfile.nacy.cc/uploads/20180813/FuYhSD8s3hNrW6QqSWU81QsXFvLY.png","http://xyfile.nacy.cc/uploads/20180813/FtbMu33q6h2M4bs9d-RQQSjbSYD9.png"]
     * detail_images_ids :
     * createtime : 1534160736
     * updatetime : 1534560769
     * brand : 洪湖湿地
     * shelf_life : 18个月
     * origin : 湖北洪湖
     * specification : 900g/袋
     * express_bancity_ids : 4,2
     * item_tag_ids : 2,3,4
     * status : 1
     * province_id : 1168
     * city_id : 1196
     * storage : 冷藏保存
     * taste : 蒜香
     * freight : 0.00
     * sales : 0
     * active：[]
     */

    private int id;
    private String name;
    private String subname;
    private String description;
    private int stock;
    private int merchant_id;
    private List<String> broadcast_images;
    private String broadcast_images_ids;
    private double price;
    private double original_price;
    private double member_price;
    private List<String> detail_images;
    private String detail_images_ids;
    private long createtime;
    private long updatetime;
    private String brand;
    private String shelf_life;
    private String origin;
    private String specification;
    private String express_bancity_ids;
    private String item_tag_ids;
    private int status;   // 判断商品是否下架 //1正常  2下架
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
    private int item_num;
    private int cart_id;
    private int cell;
    private String express_str;
    private String express;
    private List<GoodActivityBean> active;
    //定义一个字段，时候是首页查看更多
    private boolean viewToMore;
    //布局的type
    private int itemType;
    //判断是否选中
    private boolean isSelected;

    public GoodItemMessage(boolean viewToMore) {
        this.viewToMore = viewToMore;
    }

    protected GoodItemMessage(Parcel in) {
        id = in.readInt();
        name = in.readString();
        subname = in.readString();
        description = in.readString();
        stock = in.readInt();
        merchant_id = in.readInt();
        broadcast_images = in.createStringArrayList();
        broadcast_images_ids = in.readString();
        price = in.readDouble();
        original_price = in.readDouble();
        member_price = in.readDouble();
        detail_images = in.createStringArrayList();
        detail_images_ids = in.readString();
        createtime = in.readLong();
        updatetime = in.readLong();
        brand = in.readString();
        shelf_life = in.readString();
        origin = in.readString();
        specification = in.readString();
        express_bancity_ids = in.readString();
        item_tag_ids = in.readString();
        status = in.readInt();
        province_id = in.readInt();
        city_id = in.readInt();
        storage = in.readString();
        taste = in.readString();
        freight = in.readString();
        sales = in.readInt();
        smallimage = in.readString();
        province = in.readString();
        city = in.readString();
        sells = in.readInt();
        item_num = in.readInt();
        cart_id = in.readInt();
        cell = in.readInt();
        express_str = in.readString();
        express = in.readString();
        active = in.createTypedArrayList(GoodActivityBean.CREATOR);
        viewToMore = in.readByte() != 0;
        itemType = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<GoodItemMessage> CREATOR = new Creator<GoodItemMessage>() {
        @Override
        public GoodItemMessage createFromParcel(Parcel in) {
            return new GoodItemMessage(in);
        }

        @Override
        public GoodItemMessage[] newArray(int size) {
            return new GoodItemMessage[size];
        }
    };

    @Override
    public int getItemType() {
        return itemType;
    }

    public static int getTypeOne() {
        return TYPE_ONE;
    }

    public static int getTypeTwo() {
        return TYPE_TWO;
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

    public List<String> getBroadcast_images() {
        return broadcast_images;
    }

    public void setBroadcast_images(List<String> broadcast_images) {
        this.broadcast_images = broadcast_images;
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

    public List<String> getDetail_images() {
        return detail_images;
    }

    public void setDetail_images(List<String> detail_images) {
        this.detail_images = detail_images;
    }

    public String getDetail_images_ids() {
        return detail_images_ids;
    }

    public void setDetail_images_ids(String detail_images_ids) {
        this.detail_images_ids = detail_images_ids;
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

    public String getExpress_str() {
        return express_str;
    }

    public void setExpress_str(String express_str) {
        this.express_str = express_str;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public List<GoodActivityBean> getActive() {
        return active;
    }

    public void setActive(List<GoodActivityBean> active) {
        this.active = active;
    }

    public boolean isViewToMore() {
        return viewToMore;
    }

    public void setViewToMore(boolean viewToMore) {
        this.viewToMore = viewToMore;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(subname);
        parcel.writeString(description);
        parcel.writeInt(stock);
        parcel.writeInt(merchant_id);
        parcel.writeStringList(broadcast_images);
        parcel.writeString(broadcast_images_ids);
        parcel.writeDouble(price);
        parcel.writeDouble(original_price);
        parcel.writeDouble(member_price);
        parcel.writeStringList(detail_images);
        parcel.writeString(detail_images_ids);
        parcel.writeLong(createtime);
        parcel.writeLong(updatetime);
        parcel.writeString(brand);
        parcel.writeString(shelf_life);
        parcel.writeString(origin);
        parcel.writeString(specification);
        parcel.writeString(express_bancity_ids);
        parcel.writeString(item_tag_ids);
        parcel.writeInt(status);
        parcel.writeInt(province_id);
        parcel.writeInt(city_id);
        parcel.writeString(storage);
        parcel.writeString(taste);
        parcel.writeString(freight);
        parcel.writeInt(sales);
        parcel.writeString(smallimage);
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeInt(sells);
        parcel.writeInt(item_num);
        parcel.writeInt(cart_id);
        parcel.writeInt(cell);
        parcel.writeString(express_str);
        parcel.writeString(express);
        parcel.writeTypedList(active);
        parcel.writeByte((byte) (viewToMore ? 1 : 0));
        parcel.writeInt(itemType);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
