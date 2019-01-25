package com.goulala.xiayun.common.db;


import com.goulala.xiayun.DaoMaster;
import com.goulala.xiayun.DaoSession;
import com.goulala.xiayun.LocationBeanDao;
import com.goulala.xiayun.SearchHistoryDao;
import com.goulala.xiayun.TouristsGoodListDao;
import com.goulala.xiayun.common.base.BaseApplication;

public class DBManager {

    private static final String DB_NAME = "XiaYun.db";
    private static DBManager instance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static void initDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getInstance(), DB_NAME, null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase()); //可写数据库
        daoSession = daoMaster.newSession();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


    public SearchHistoryDao getHistoryDao() {
        return getDaoSession().getSearchHistoryDao();
    }

    public TouristsGoodListDao getTouristsGoodListDao() {
        return getDaoSession().getTouristsGoodListDao();
    }


    public LocationBeanDao getLocationBeanDao() {
        return getDaoSession().getLocationBeanDao();
    }


}
