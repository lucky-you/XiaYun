package com.goulala.xiayun.common.model;

import java.io.Serializable;

/**
 * Created by：Z_B on 2018/6/15 11:53
 * Effect：用户信息
 */
public class UserInfo implements Serializable {

    /**
     * id : 23
     * username : 13677197786
     * nickname : 13677197786
     * mobile : 13677197786
     * avatar : http://xyfile.nacy.cc/assets/img/avatar.png
     * gender : 0
     * birthday : null
     * address :
     * bio :
     * score : 0
     * token : 6c8401b4-5270-4b87-a50f-b557d16283b9
     * user_id : 23
     * createtime : 1538100709
     * expiretime : 1540692709
     * expires_in : 2587818
     */

    private int id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private int gender;
    private String birthday;
    private String address;
    private String bio;
    private int score;
    private String token;
    private int user_id;
    private int createtime;
    private int expiretime;
    private int expires_in;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
