package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2019/1/16
 * function  : 客服中心的model
 */
public class ServiceCenterList {


    /**
     * id : 1
     * logoimage : http://xyfile.nacy.cc/uploads/20190116/Fjr5TcJR9Uh2cLdrFsG7GOKXsqdX.png
     * title : 代购指引微信号
     * content : goulala11
     * createtime : 1547607650
     * updatetime : 1547607650
     */

    private int id;
    private String logoimage;
    private String title;
    private String content;
    private int createtime;
    private int updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoimage() {
        return logoimage;
    }

    public void setLogoimage(String logoimage) {
        this.logoimage = logoimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
