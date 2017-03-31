package com.landicorp.yinshang.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by u on 2017/1/17.
 */

public class DBManager {

    private static DBManager mInstance;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private static MySqlLiteOpenHelper mySqlLiteOpenHelper;
    private static Database mDatabase;

    //数据库名，表名是自动被创建的
    public static final String DB_NAME = "sample.db";

    public DBManager() {
        //创建数据库
        //注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        DaoMaster.DevOpenHelper devOpenHelper = new  DaoMaster.DevOpenHelper(BaseApplication.mContext, DB_NAME, null);
//        //得到数据库管理者
//        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
//        //得到daoSession，可以执行增删改查操作
//        mDaoSession = mDaoMaster.newSession();

    }

    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                mInstance = new DBManager();
            }
        }
        return mInstance;
    }

    public static void init(Context context){
        mySqlLiteOpenHelper = new MySqlLiteOpenHelper(context,DB_NAME,null);
        mDatabase = mySqlLiteOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(mDatabase).newSession();
    }


    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
