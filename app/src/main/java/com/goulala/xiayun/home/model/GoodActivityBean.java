package com.goulala.xiayun.home.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : Z_B
 * date : 2018/8/29
 * function : 商品活动的数据(通用)
 */
public class GoodActivityBean implements Parcelable {

    /**
     * id : 73
     * active_category_id : 1
     * name : 爆品秒杀
     * smallimage : null
     * largeimage : null
     * starttime : 1541743200
     * endtime : 1541750400
     * preferential_id : 0
     * trade_amount : 0.00
     * trade_num : 0
     * discount_amount : 0.00
     * coupon_id : 0
     * sell_count : 0
     * item_min : 2
     * item_max : 2
     * status : 1
     * weigh : 0
     * createtime : 1541555992
     * updatetime : 1541744967
     * sell_amount : 0
     * sell_num : 0
     * sell_title : 限时秒杀
     * sell_rule :
     * sell_desc :
     * sell_type :
     * sell_link :
     * title : 作废,请及时修改
     * desc : 作废,请及时修改
     * active_item_name :
     * active_id : 73
     * item_id : 35
     * price : 80.00
     * member_price : 50.00
     * num : 300
     * sales : 10
     * stock : 300
     * starttime_text : 2018-11-09 14:00:00
     * endtime_text : 2018-11-09 16:00:00
     * status_text : Status 1
     */

    private int id;
    private int active_category_id;
    private String name;
    private String smallimage;
    private String largeimage;
    private long starttime;
    private long endtime;
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
    private long createtime;
    private long updatetime;
    private int sell_amount;
    private int sell_num;
    private String sell_title;
    private String sell_rule;
    private String sell_desc;
    private String sell_type;
    private String sell_link;
    private String title;
    private String desc;
    private String active_item_name;
    private int active_id;
    private int item_id;
    private double price;
    private double member_price;
    private int num;
    private int sales;
    private int stock;
    private String starttime_text;
    private String endtime_text;
    private String status_text;

    protected GoodActivityBean(Parcel in) {
        id = in.readInt();
        active_category_id = in.readInt();
        name = in.readString();
        smallimage = in.readString();
        largeimage = in.readString();
        starttime = in.readLong();
        endtime = in.readLong();
        preferential_id = in.readInt();
        trade_amount = in.readString();
        trade_num = in.readInt();
        discount_amount = in.readString();
        coupon_id = in.readInt();
        sell_count = in.readInt();
        item_min = in.readInt();
        item_max = in.readInt();
        status = in.readInt();
        weigh = in.readInt();
        createtime = in.readLong();
        updatetime = in.readLong();
        sell_amount = in.readInt();
        sell_num = in.readInt();
        sell_title = in.readString();
        sell_rule = in.readString();
        sell_desc = in.readString();
        sell_type = in.readString();
        sell_link = in.readString();
        title = in.readString();
        desc = in.readString();
        active_item_name = in.readString();
        active_id = in.readInt();
        item_id = in.readInt();
        price = in.readDouble();
        member_price = in.readDouble();
        num = in.readInt();
        sales = in.readInt();
        stock = in.readInt();
        starttime_text = in.readString();
        endtime_text = in.readString();
        status_text = in.readString();
    }

    public static final Creator<GoodActivityBean> CREATOR = new Creator<GoodActivityBean>() {
        @Override
        public GoodActivityBean createFromParcel(Parcel in) {
            return new GoodActivityBean(in);
        }

        @Override
        public GoodActivityBean[] newArray(int size) {
            return new GoodActivityBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(active_category_id);
        parcel.writeString(name);
        parcel.writeString(smallimage);
        parcel.writeString(largeimage);
        parcel.writeLong(starttime);
        parcel.writeLong(endtime);
        parcel.writeInt(preferential_id);
        parcel.writeString(trade_amount);
        parcel.writeInt(trade_num);
        parcel.writeString(discount_amount);
        parcel.writeInt(coupon_id);
        parcel.writeInt(sell_count);
        parcel.writeInt(item_min);
        parcel.writeInt(item_max);
        parcel.writeInt(status);
        parcel.writeInt(weigh);
        parcel.writeLong(createtime);
        parcel.writeLong(updatetime);
        parcel.writeInt(sell_amount);
        parcel.writeInt(sell_num);
        parcel.writeString(sell_title);
        parcel.writeString(sell_rule);
        parcel.writeString(sell_desc);
        parcel.writeString(sell_type);
        parcel.writeString(sell_link);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(active_item_name);
        parcel.writeInt(active_id);
        parcel.writeInt(item_id);
        parcel.writeDouble(price);
        parcel.writeDouble(member_price);
        parcel.writeInt(num);
        parcel.writeInt(sales);
        parcel.writeInt(stock);
        parcel.writeString(starttime_text);
        parcel.writeString(endtime_text);
        parcel.writeString(status_text);
    }

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

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
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

    public String getSell_type() {
        return sell_type;
    }

    public void setSell_type(String sell_type) {
        this.sell_type = sell_type;
    }

    public String getSell_link() {
        return sell_link;
    }

    public void setSell_link(String sell_link) {
        this.sell_link = sell_link;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMember_price() {
        return member_price;
    }

    public void setMember_price(double member_price) {
        this.member_price = member_price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStarttime_text() {
        return starttime_text;
    }

    public void setStarttime_text(String starttime_text) {
        this.starttime_text = starttime_text;
    }

    public String getEndtime_text() {
        return endtime_text;
    }

    public void setEndtime_text(String endtime_text) {
        this.endtime_text = endtime_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}

