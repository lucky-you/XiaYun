package com.goulala.xiayun.common.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * author      : Z_B
 * date       : 2019/1/21
 * function  : 历史搜索记录
 */
@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    public Long id;
    @NotNull
    public String name;
    public String user_id;
    public String hit;
    public String createtime;
    public String updatetime;

    @Generated(hash = 1315973898)
    public SearchHistory(Long id, @NotNull String name, String user_id, String hit,
                         String createtime, String updatetime) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.hit = hit;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public SearchHistory(String name) {
        this.name = name;
    }

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