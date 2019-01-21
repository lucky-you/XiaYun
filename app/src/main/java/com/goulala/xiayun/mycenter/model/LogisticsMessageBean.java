package com.goulala.xiayun.mycenter.model;


import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/31
 * function  :消息列表的bean
 */
public class LogisticsMessageBean {


    /**
     * total : 3
     * per_page : 5
     * current_page : 1
     * last_page : 1
     * data : [{"id":15,"topic":2,"name":"订单已发货","memo":"订单35091966400262673744已发货 2018年10月31日","attachtype":2,"attachno":"35091966400262673744","publisher_id":1,"subscriber_id":"23","weigh":15,"createtime":1540969857,"updatetime":1540969857,"readtime":0,"attach":{"express_number":"3242343242","item_img":"http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png?imageView/2/w/100/h/100","name":"洪湖香十三香小龙香虾"}},{"id":13,"topic":2,"name":"订单已发货","memo":"订单20099087200490842440已发货 2018年10月31日","attachtype":2,"attachno":"20099087200490842440","publisher_id":1,"subscriber_id":"23","weigh":13,"createtime":1540948708,"updatetime":1540948708,"readtime":0,"attach":{"express_number":"1000957941643","item_img":"http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png?imageView/2/w/100/h/100","name":"洪湖十三香小龙虾洪湖十三香小龙虾洪湖十三香小龙虾"}},{"id":11,"topic":2,"name":"订单已发货","memo":"订单31004531900524022229已发货 2018年10月30日","attachtype":2,"attachno":"31004531900524022229","publisher_id":1,"subscriber_id":"23","weigh":11,"createtime":1540892165,"updatetime":1540892165,"readtime":0,"attach":{"express_number":"3380838656252","item_img":"http://xyfile.nacy.cc/uploads/20180904/FsYUkrrwAjFU31xUKtEd68TTZ9Mc.jpg?imageView/2/w/100/h/100","name":"15号xx商品没有商品名称"}}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<LogisticsMessageList> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
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

    public List<LogisticsMessageList> getData() {
        return data;
    }

    public void setData(List<LogisticsMessageList> data) {
        this.data = data;
    }
}
