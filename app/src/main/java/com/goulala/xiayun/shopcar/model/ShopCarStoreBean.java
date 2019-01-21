package com.goulala.xiayun.shopcar.model;

import com.goulala.xiayun.home.model.GoodItemMessage;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/28
 * @function : 购物车店铺的信息
 */
public class ShopCarStoreBean {


    /**
     * id : 2
     * admin_id : 6
     * user_id : 2
     * name : 供应商U
     * contacts : 张浩浩
     * bank_name : 6
     * bank_num : 65214552155884725
     * payee_username : 张浩浩
     * last_login_time : 0
     * status : 1
     * createtime : 1534576210
     * updatetime : 1536033204
     * cell : 2
     * item : [{"id":7,"name":"洪湖香十三香小龙香虾","subname":"","description":"夜宵首选，回味无穷","stock":300,"merchant_id":2,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png","http://xyfile.nacy.cc/uploads/20180828/Fq7QfqOv7fU4fnbfpd7imBFABsbK.png"],"broadcast_images_ids":"","price":"100.00","original_price":"80.00","member_price":"60.00","detail_images":["http://xyfile.nacy.cc/uploads/20180828/FgWCgFmO_np4OO9b9QiYnHl9bCm7.png","http://xyfile.nacy.cc/uploads/20180828/Fscgh8WwxWFc6rWMwo_QuukuvPBc.png","http://xyfile.nacy.cc/uploads/20180828/FpZUB7MzkeDYSju0aUIEtP2C5e5J.png","http://xyfile.nacy.cc/uploads/20180828/Fv7JDvuKxa41IGx39SeAZMQcLxD1.png","http://xyfile.nacy.cc/uploads/20180828/Fn_6pzi2UG4f8I6Bm3e4h_ES2QGX.png","http://xyfile.nacy.cc/uploads/20180828/FscY5SHgfOTG7sEkS7kx67COxvwf.png","http://xyfile.nacy.cc/uploads/20180828/FrgmPOeesJj6I3PhercfGlJNTZ0G.png","http://xyfile.nacy.cc/uploads/20180828/FgHLHjTWB6WFZ-CinoKTP6ZOq30t.png","http://xyfile.nacy.cc/uploads/20180828/FvkKZVwxoBp5wh7C5wvt-aCmnueg.png"],"detail_images_ids":"","createtime":1534160419,"updatetime":1535456560,"brand":"洪湖湿地","shelf_life":"18个月","origin":"湖北洪湖","specification":"900g/袋","express_bancity_ids":"3,4,2,5","item_tag_ids":"2,3,1","status":1,"province_id":1168,"city_id":1196,"storage":"常温保存","taste":"香辣","freight":"10.00","sales":0,"smallimage":"http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png?imageView/2/w/100/h/100","province":"福建省","city":"三明市","sells":53,"active":[{"id":32,"active_category_id":2,"name":"满3件减20","smallimage":"http://xyfile.nacy.cc/uploads/20180906/Fnsm_ZBIXvZjnK7BCRWAPbzDdzLZ.png","largeimage":"http://xyfile.nacy.cc/uploads/20180906/Fnsm_ZBIXvZjnK7BCRWAPbzDdzLZ.png","starttime":1535299200,"endtime":1535385600,"preferential_id":2,"trade_amount":"0.00","trade_num":3,"discount_amount":"20.00","coupon_id":0,"sell_count":200,"item_min":1,"item_max":20,"status":1,"weigh":0,"createtime":1535352763,"updatetime":1536200900,"sell_amount":0,"sell_num":0,"sell_title":"限时促销","sell_rule":"满3件减20.00","sell_desc":"","title":"作废,请及时修改","desc":"作废,请及时修改","active_item_name":"","active_id":32,"item_id":7,"price":"200.00","member_price":"200.00","num":300}],"item_num":10,"cart_id":25,"cell":3}]
     */

    private int id;
    private int admin_id;
    private int user_id;
    private String name;
    private String contacts;
    private String bank_name;
    private String bank_num;
    private String payee_username;
    private int last_login_time;
    private int status;
    private int createtime;
    private int updatetime;
    private int cell;
    private List<GoodItemMessage> item;
    //店铺是否选择
    private boolean isStoreSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public String getPayee_username() {
        return payee_username;
    }

    public void setPayee_username(String payee_username) {
        this.payee_username = payee_username;
    }

    public int getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(int last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public List<GoodItemMessage> getItem() {
        return item;
    }

    public void setItem(List<GoodItemMessage> item) {
        this.item = item;
    }

    public boolean isStoreSelected() {
        return isStoreSelected;
    }

    public void setStoreSelected(boolean storeSelected) {
        isStoreSelected = storeSelected;
    }

    @Override
    public String toString() {
        return "ShopCarStoreBean{" +
                "id=" + id +
                ", admin_id=" + admin_id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_num='" + bank_num + '\'' +
                ", payee_username='" + payee_username + '\'' +
                ", last_login_time=" + last_login_time +
                ", status=" + status +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", cell=" + cell +
                ", item=" + item +
                ", isStoreSelected=" + isStoreSelected +
                '}';
    }
}
