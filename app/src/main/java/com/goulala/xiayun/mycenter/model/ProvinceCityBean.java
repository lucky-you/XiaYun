package com.goulala.xiayun.mycenter.model;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/28
 * function  : 省市区的数据
 */
public class ProvinceCityBean implements IPickerViewData {


    /**
     * id : 1
     * pid : 0
     * shortname : 北京
     * name : 北京
     * mergename : 中国,北京
     * level : 1
     * pinyin : beijing
     * code :
     * zip :
     * first : B
     * lng : 116.405285
     * lat : 39.904989
     * child : [{"id":2,"pid":1,"shortname":"北京","name":"北京市","mergename":"中国,北京,北京市","level":2,"pinyin":"beijing","code":"010","zip":"100000","first":"B","lng":"116.405285","lat":"39.904989","child":[{"id":3,"pid":2,"shortname":"东城","name":"东城区","mergename":"中国,北京,北京市,东城区","level":3,"pinyin":"dongcheng","code":"010","zip":"100010","first":"D","lng":"116.41005","lat":"39.93157"},{"id":4,"pid":2,"shortname":"西城","name":"西城区","mergename":"中国,北京,北京市,西城区","level":3,"pinyin":"xicheng","code":"010","zip":"100032","first":"X","lng":"116.36003","lat":"39.9305"},{"id":5,"pid":2,"shortname":"朝阳","name":"朝阳区","mergename":"中国,北京,北京市,朝阳区","level":3,"pinyin":"chaoyang","code":"010","zip":"100020","first":"C","lng":"116.48548","lat":"39.9484"},{"id":6,"pid":2,"shortname":"丰台","name":"丰台区","mergename":"中国,北京,北京市,丰台区","level":3,"pinyin":"fengtai","code":"010","zip":"100071","first":"F","lng":"116.28625","lat":"39.8585"},{"id":7,"pid":2,"shortname":"石景山","name":"石景山区","mergename":"中国,北京,北京市,石景山区","level":3,"pinyin":"shijingshan","code":"010","zip":"100043","first":"S","lng":"116.2229","lat":"39.90564"},{"id":8,"pid":2,"shortname":"海淀","name":"海淀区","mergename":"中国,北京,北京市,海淀区","level":3,"pinyin":"haidian","code":"010","zip":"100089","first":"H","lng":"116.29812","lat":"39.95931"},{"id":9,"pid":2,"shortname":"门头沟","name":"门头沟区","mergename":"中国,北京,北京市,门头沟区","level":3,"pinyin":"mentougou","code":"010","zip":"102300","first":"M","lng":"116.10137","lat":"39.94043"},{"id":10,"pid":2,"shortname":"房山","name":"房山区","mergename":"中国,北京,北京市,房山区","level":3,"pinyin":"fangshan","code":"010","zip":"102488","first":"F","lng":"116.14257","lat":"39.74786"},{"id":11,"pid":2,"shortname":"通州","name":"通州区","mergename":"中国,北京,北京市,通州区","level":3,"pinyin":"tongzhou","code":"010","zip":"101149","first":"T","lng":"116.65716","lat":"39.90966"},{"id":12,"pid":2,"shortname":"顺义","name":"顺义区","mergename":"中国,北京,北京市,顺义区","level":3,"pinyin":"shunyi","code":"010","zip":"101300","first":"S","lng":"116.65417","lat":"40.1302"},{"id":13,"pid":2,"shortname":"昌平","name":"昌平区","mergename":"中国,北京,北京市,昌平区","level":3,"pinyin":"changping","code":"010","zip":"102200","first":"C","lng":"116.2312","lat":"40.22072"},{"id":14,"pid":2,"shortname":"大兴","name":"大兴区","mergename":"中国,北京,北京市,大兴区","level":3,"pinyin":"daxing","code":"010","zip":"102600","first":"D","lng":"116.34149","lat":"39.72668"},{"id":15,"pid":2,"shortname":"怀柔","name":"怀柔区","mergename":"中国,北京,北京市,怀柔区","level":3,"pinyin":"huairou","code":"010","zip":"101400","first":"H","lng":"116.63168","lat":"40.31602"},{"id":16,"pid":2,"shortname":"平谷","name":"平谷区","mergename":"中国,北京,北京市,平谷区","level":3,"pinyin":"pinggu","code":"010","zip":"101200","first":"P","lng":"117.12133","lat":"40.14056"},{"id":17,"pid":2,"shortname":"密云","name":"密云县","mergename":"中国,北京,北京市,密云县","level":3,"pinyin":"miyun","code":"010","zip":"101500","first":"M","lng":"116.84295","lat":"40.37618"},{"id":18,"pid":2,"shortname":"延庆","name":"延庆县","mergename":"中国,北京,北京市,延庆县","level":3,"pinyin":"yanqing","code":"010","zip":"102100","first":"Y","lng":"115.97494","lat":"40.45672"}]}]
     */

    private int id;
    private int pid;
    private String shortname;
    private String name;
    private String mergename;
    private int level;
    private String pinyin;
    private String code;
    private String zip;
    private String first;
    private String lng;
    private String lat;
    private List<ProvinceCityBean> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMergename() {
        return mergename;
    }

    public void setMergename(String mergename) {
        this.mergename = mergename;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public List<ProvinceCityBean> getChild() {
        return child;
    }

    public void setChild(List<ProvinceCityBean> child) {
        this.child = child;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
