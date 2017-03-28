package com.landicorp.nuoxin;

import android.app.Application;
import android.content.Context;

/**
 * 系统应用标示 <br>
 * 2014年10月24日 <br>
 * davintan
 */
public class MainApplication extends Application {

	/**
	 * 应用上下文
	 */
	public static Context mContext = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	}

}
