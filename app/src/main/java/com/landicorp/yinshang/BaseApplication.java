package com.landicorp.yinshang;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.gson.Gson;
import com.landicorp.yinshang.data.model.SystemInfoBean;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.glide.OkHttpUrlLoader;
import com.landicorp.yinshang.utils.CrashHandler;
import com.ums.AppHelper;

import org.litepal.LitePalApplication;

import java.io.InputStream;

/**
 * @Project retrofitrxandroiddagger2
 * @Packate com.micky.retrofitrxandroiddagger2
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2015-12-21 17:47
 * @Version 0.1
 */
public class BaseApplication extends LitePalApplication {

    public static Context mContext = null;
    private SystemInfoBean systemInfo;
    private Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        String sysInfo = AppHelper.getBaseSysInfo(getApplicationContext());
        systemInfo = gson.fromJson(sysInfo, SystemInfoBean.class);
        CrashHandler.getInstance().init(this);
//        DBManager.getInstance();
        DBManager.init(this);
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    public SystemInfoBean getSystemInfo() {
        return systemInfo;
    }

}
