package com.landicorp.nuoxin.utils;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.landicorp.nuoxin.MainApplication;
import com.landicorp.nuoxin.R;
import com.landicorp.nuoxin.views.CustomProgressDialog;

public class Util {

	/**
	 * 系统自定义进度条
	 */
	private static CustomProgressDialog progressDialog = null;

	/**
	 * Activity 管理堆栈
	 */
	private static ArrayList<Activity> activityStack = null;

	/**
	 * 加入Activity堆栈 <br>
	 * 日期：2015年1月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void addToStack(Activity act) {
		if (activityStack == null) {
			activityStack = new ArrayList<Activity>();
		}

		activityStack.add(act);
	}

	/**
	 * 获取当前显示界面的Context <br>
	 * 日期：2015年1月24日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：Context
	 */
	public static Context getCurrentContext() {
		Context mContext = null;

		if (activityStack != null && activityStack.size() > 0) {
			mContext = activityStack.get(activityStack.size() - 1);
		} else {
			mContext = MainApplication.mContext;
		}

		return mContext;
	}

	/**
	 * 弹出Activity堆栈 <br>
	 * 日期：2015年1月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void popFromStack(Activity act) {
		if (activityStack == null) {
			activityStack = new ArrayList<Activity>();
		}

		activityStack.remove(act);
	}

	/**
	 * 开启进度条 <br>
	 * 日期：2014年12月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void showProgress() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog
					.createDialog(getCurrentContext());
			progressDialog.setCancelable(false);
			progressDialog.setOnKeyListener(onKeyListener);
		}

		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.show();
		}
	}
	
	/**
	 * 开启进度条 <br>
	 * 日期：2014年12月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void showProgress(Context context) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog
					.createDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setOnKeyListener(onKeyListener);
		}

		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.show();
		}
	}

	/**
	 * 关闭进度条 <br>
	 * 日期：2014年12月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void dismissProgress() {
		try {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

			progressDialog = null;
		} catch (Exception e) {
		}
	}

	/**
	 * 对话框接受按钮事件
	 */
	private static OnKeyListener onKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				dismissProgress();
				return true;
			}
			return false;
		}
	};

	/**
	 * Method_网络是否连接
	 * @return 结果
	 */
	public static boolean isConnected() {
		ConnectivityManager connectivity = (ConnectivityManager) getCurrentContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method_判断WIFI网络是否可用
	 * @return 结果
	 */
	public boolean isConnectedByWifi() {
		return isConnecteByType(ConnectivityManager.TYPE_WIFI);
	}

	/**
	 * Method_判断MOBILE网络是否可用
	 * 
	 * @return 结果
	 */
	public boolean isConnectedByMobile() {
		return isConnecteByType(ConnectivityManager.TYPE_MOBILE);
	}

	/**
	 * Method_根据网络类型判断是否可用
	 * @param networkType 网络类型
	 * @return 结果
	 */
	private boolean isConnecteByType(int networkType) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) getCurrentContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWiFiNetworkInfo = mConnectivityManager
				.getNetworkInfo(networkType);

		if (mWiFiNetworkInfo != null) {
			return mWiFiNetworkInfo.isConnected();
		}
		return false;
	}
	
	public static Typeface typeface(Context context) {
		 return Typeface.createFromAsset(context.getAssets(), "fonts/ContraptionNarrowLight-Regular.ttf");
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftKeyBoard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	/**
	 * 弹出软键盘
	 */
	public static void popSoftKeyBoard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	

	public static void showNetworkException() {
		ToastUtil.makeText(getCurrentContext(), getCurrentContext().getString(R.string.network_exception)).show();
	}
}
