package com.example.cc.code_container;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.cc.code_container.greendao.DaoMaster;
import com.example.cc.code_container.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class MyApplication extends Application {
    //忽略内存泄漏错误
    @SuppressLint("StaticFieldLeak")
    private static Context sContext = null;
    private static MyApplication sInstance = null;

    private DaoMaster.DevOpenHelper mHelper;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        sInstance = this;

        setDatabase();
    }

    public static Context getsContext(){
        return sContext;
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "info.db", null);
        db = mHelper.getEncryptedWritableDb("2015122031");
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 得到DaoSession对象
     *
     * @return
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
