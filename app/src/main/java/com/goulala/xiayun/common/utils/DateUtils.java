package com.goulala.xiayun.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author      : Z_B
 * date       : 2018/12/8
 * function  : 时间戳的工具类
 */
public class DateUtils {

    public static String getStringDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(date);
    }


    public static String getResultStringDate(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    /**
     * 时间戳转字符串,服务器的时间给到了秒，需要x1000,转成毫秒
     */
    public static String getStrTime(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getStrTimeTwo(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
