package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/27
 * @function : 收藏和足迹的商品信息
 */
public class CollectGoodMessage {


    /**
     * total : 57
     * per_page : 1
     * current_page : 10
     * last_page : 57
     * data : [{"id":59,"user_id":23,"item_id":7,"createtime":1535422992,"updatetime":0,"item":{"id":7,"name":"洪湖十三香小龙虾","subname":"","description":"夜宵首选，回味无穷","stock":300,"merchant_id":2,"broadcast_images":["http://xyfile.nacy.cc/uploads/20180813/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png","http://xyfile.nacy.cc/uploads/20180813/Fq7QfqOv7fU4fnbfpd7imBFABsbK.png","http://xyfile.nacy.cc/uploads/20180813/Fvs8Kf8npje5jO0Y3ZVf8aWZWZCO.png"],"broadcast_images_ids":"","price":"100.00","original_price":"80.00","member_price":"60.00","detail_images":["http://xyfile.nacy.cc/uploads/20180813/FuYJ80ZHVfAvFJEmCZjF_zZq7s-P.png","http://xyfile.nacy.cc/uploads/20180813/FsnGjLwtptvSXT4-EC17kAKdXT1U.png","http://xyfile.nacy.cc/uploads/20180813/FnizFMEUhm4zr5HVe2oAypvYmdhA.png","http://xyfile.nacy.cc/uploads/20180813/FhOoQI_PV5YVuTpVd3GhvEoihfqQ.png","http://xyfile.nacy.cc/uploads/20180813/Fr79uIhXzazAc_5raYiQ6ER1067s.png","http://xyfile.nacy.cc/uploads/20180813/FuYhSD8s3hNrW6QqSWU81QsXFvLY.png","http://xyfile.nacy.cc/uploads/20180813/FtbMu33q6h2M4bs9d-RQQSjbSYD9.png"],"detail_images_ids":"","createtime":1534160419,"updatetime":1534420658,"brand":"洪湖湿地","shelf_life":"18个月","origin":"湖北洪湖","specification":"900g/袋","express_bancity_ids":"3,4,2,5","item_tag_ids":"2,3,1","status":1,"province_id":1168,"city_id":1196,"storage":"常温保存","taste":"香辣","freight":"10.00","sales":0,"active":[{"id":2,"price":8.5,"member_price":4.5,"desc":"满99减20"}]}}]
     */

    private int total;
    private String per_page;
    private int current_page;
    private int last_page;
    private List<CollectionGoodList> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<CollectionGoodList> getData() {
        return data;
    }

    public void setData(List<CollectionGoodList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CollectGoodMessage{" +
                "total=" + total +
                ", per_page='" + per_page + '\'' +
                ", current_page=" + current_page +
                ", last_page=" + last_page +
                ", data=" + data +
                '}';
    }
}
