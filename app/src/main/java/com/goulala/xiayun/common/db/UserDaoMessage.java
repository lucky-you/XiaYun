package com.goulala.xiayun.common.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author      : Z_B
 * date       : 2019/1/21
 * function  :
 */
@Entity
public class UserDaoMessage {


    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    @NotNull
    private String token;
    private int user_id;

    @Generated(hash = 394095469)
    public UserDaoMessage(String username, String nickname, String mobile,
                          String avatar, @NotNull String token, int user_id) {
        this.username = username;
        this.nickname = nickname;
        this.mobile = mobile;
        this.avatar = avatar;
        this.token = token;
        this.user_id = user_id;
    }

    @Generated(hash = 846459052)
    public UserDaoMessage() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
