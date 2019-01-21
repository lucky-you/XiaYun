package com.goulala.xiayun.common.db;


import android.content.Context;

import com.goulala.xiayun.TouristsGoodListDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/6
 * function  : 操作数据库的帮助类
 */
public class DaoManagerUtils {


    /**
     * 存储游客的信息
     */
    public static void insertTouristsGoodListData(TouristsGoodList touristsGoodList) {
        DBManager.getInstance().getTouristsGoodListDao().insert(touristsGoodList);
    }


    /**
     * 删除游客数据
     */
    public static void deleteData(TouristsGoodList touristsGoodList) {
        DBManager.getInstance().getTouristsGoodListDao().delete(touristsGoodList);
    }

    /**
     * 根据id删除数据至数据库
     */
    public static void deleteByKeyData(Void id) {
        DBManager.getInstance().getTouristsGoodListDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     */
    public static void deleteAllData(Context context) {
        DBManager.getInstance().getTouristsGoodListDao().deleteAll();
    }

    /**
     * 更新数据库
     */
    public static void updateData(TouristsGoodList touristsGoodList) {
        DBManager.getInstance().getTouristsGoodListDao().update(touristsGoodList);
    }


    /**
     * 保存游客数据
     */

    public static void saveTouristsGoodListDate(TouristsGoodList touristsGoodList) {
        DBManager.getInstance().getTouristsGoodListDao().save(touristsGoodList);
    }

    /**
     * 插入数据
     */
    public static void insertOrReplaceData(TouristsGoodList touristsGoodList) {
        DBManager.getInstance().getTouristsGoodListDao().insertOrReplace(touristsGoodList);
    }

    /**
     * 查询所有数据
     */
    public static List<TouristsGoodList> queryAll() {
        QueryBuilder<TouristsGoodList> builder = DBManager.getInstance().getTouristsGoodListDao().queryBuilder();
        return builder.build().list();
    }

    /**
     * 根据ID查找
     */
    public static List<TouristsGoodList> queryForId(int id) {
        QueryBuilder<TouristsGoodList> builder = DBManager.getInstance().getTouristsGoodListDao().queryBuilder();
        /**
         * 返回当前id的数据集合,当然where(这里面可以有多组，做为条件);
         * 这里build.list()；与where(TouristsGoodListDao.Properties.Id.eq(id)).list()结果是一样的；
         * 在QueryBuilder类中list()方法return build().list();
         */

        return builder.where(TouristsGoodListDao.Properties.Item_id.eq(id)).build().list();
    }


    /**
     * 存储用户信息
     *
     * @param userMessage
     */
    public static void insertUserMessageData(UserDaoMessage userMessage) {
        DBManager.getInstance().getUserMessageDao().insert(userMessage);
    }

    /**
     * 存储用户信息
     *
     * @param userMessage
     */
    public static void saveUserMessageDate(UserDaoMessage userMessage) {
        DBManager.getInstance().getUserMessageDao().save(userMessage);
    }

    /**
     * 删除用户信息
     */
    public static void deleteUserMessageData(UserDaoMessage userMessage) {
        DBManager.getInstance().getUserMessageDao().delete(userMessage);
    }

    /**
     * 删除全部用户信息
     */
    public static void deleteAllUserMessageData(Context context) {
        DBManager.getInstance().getUserMessageDao().deleteAll();
    }

    /**
     * 历史定位记录
     *
     * @param locationBean
     */
    public static void insertLocationBeanData(LocationBean locationBean) {
        DBManager.getInstance().getLocationBeanDao().save(locationBean);
    }

    /**
     * 获取保存的历史记录
     * @return
     */
    public static List<LocationBean> queryAllLocationList() {
        QueryBuilder<LocationBean> builder = DBManager.getInstance().getLocationBeanDao().queryBuilder();
        return builder.build().list();
    }
}
