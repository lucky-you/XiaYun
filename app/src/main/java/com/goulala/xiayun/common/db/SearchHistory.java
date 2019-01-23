package com.goulala.xiayun.common.db;

/**
 * author      : Z_B
 * date       : 2019/1/21
 * function  : 历史搜索记录
 */
public class SearchHistory {

    public Long id;
    public String name;
    public String user_id;
    public String hit;
    public String createtime;
    public String updatetime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHit() {
        return this.hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}