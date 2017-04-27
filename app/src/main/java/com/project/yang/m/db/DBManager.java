package com.project.yang.m.db;

import android.database.sqlite.SQLiteDatabase;

import com.project.yang.m.common.App;
import com.yang.m.greendao.gen.DaoMaster;
import com.yang.m.greendao.gen.DaoSession;

/**
 * Created by NiYang on 2017/4/26.
 */

public class DBManager {
    private static DBManager dbManager = null;
    private static final String DB_NAME = "dm.db";//数据库名称
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private SQLiteDatabase db;

    private DBManager() {

    }

    public static DBManager getDbManager() {
        if (dbManager == null) {
            synchronized (DBManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager();
                }
            }
        }
        return dbManager;
    }

    public DaoMaster getDaoMaster() {
        if (this.mDaoMaster == null) {
            mHelper = new DaoMaster.DevOpenHelper(App.getContext(), DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDb());
        }
        return this.mDaoMaster;
    }

    public DaoSession getDaoSession() {
        if (this.mDaoSession == null) {
            if (this.mDaoMaster == null) {
                this.mDaoMaster = getDaoMaster();
            }
            this.mDaoSession = this.mDaoMaster.newSession();
        }
        return this.mDaoSession;
    }

    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession() {
        if (this.mDaoSession != null) {
            this.mDaoSession.clear();
            this.mDaoSession = null;
        }
    }

    public void closeHelper() {
        if (this.mHelper != null) {
            this.mHelper.close();
            this.mHelper = null;
        }
    }
}
