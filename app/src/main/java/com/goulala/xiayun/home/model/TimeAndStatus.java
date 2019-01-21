package com.goulala.xiayun.home.model;

/**
 * @author : Z_B
 * @date : 2018/8/8
 * @function : 爆款秒杀的时间和状态
 */
public class TimeAndStatus {
    public String time;
    public String status;

    public TimeAndStatus(String time, String status) {
        this.time = time;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
