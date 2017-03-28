package com.landicorp.yinshang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.landicorp.yinshang.BaseApplication;
import com.landicorp.yinshang.R;
import com.landicorp.yinshang.view.CustomProgressDialog;
import com.landicorp.yinshang.view.SpecialProgressDialog;

/**
 * 系统工具类 <br>
 * 日期：2015年1月28日 <br>
 * 
 * @author：davintan
 */
public class Util
{

	/**
	 * 系统自定义进度条
	 */
	private static SpecialProgressDialog specialProgressDialog = null;


	/**
	 * 系统自定义进度条
	 */
	private static CustomProgressDialog progressDialog = null;

	/**
	 * Activity 管理堆栈
	 */
	private static ArrayList<Activity> activityStack = null;

	/**
	 * 退出程序方法
	 * 
	 * @param context
	 */
	public static void exitApp(Context context)
	{
		if (activityStack != null)
		{
			for (Activity act : activityStack)
			{
				if (act != null)
				{
					act.finish();
					act = null;
				}
			}
		}
		
		// 清空所有活动Activity
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 获取当前显示界面的Context <br>
	 * 日期：2015年1月24日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：Context
	 */
	public static Context getCurrentContext()
	{
		Context mContext = null;

		if (activityStack != null && activityStack.size() > 0)
		{
			mContext = activityStack.get(activityStack.size() - 1);
		} else
		{
			mContext = BaseApplication.mContext;
		}

		return mContext;
	}

	/**
	 * 开启进度条 <br>
	 * 日期：2014年12月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void showProgress()
	{
		if (progressDialog == null)
		{
			progressDialog = CustomProgressDialog
					.createDialog(getCurrentContext());
			progressDialog.setCancelable(false);
			progressDialog.setOnKeyListener(onKeyListener);
		}

		if (progressDialog != null && !progressDialog.isShowing())
		{
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
	 * 开启进度条 <br>
	 * 日期：2014年12月4日 <br>
	 *
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void showSpecialProgress(Context context) {
		if (specialProgressDialog == null) {
			specialProgressDialog = SpecialProgressDialog
					.createDialog(context);
			specialProgressDialog.setCancelable(true);
//			specialProgressDialog.setOnKeyListener(onKeyListener);
		}

		if (specialProgressDialog != null && !specialProgressDialog.isShowing()) {
			specialProgressDialog.show();
		}
	}

	public static void dismissSpecialProgress()
	{
		try
		{
			if (specialProgressDialog != null && specialProgressDialog.isShowing())
			{
				specialProgressDialog.dismiss();
			}

			specialProgressDialog = null;
		} catch (Exception e)
		{
		}
	}

	/**
	 * 对话框接受按钮事件
	 */
	private static OnKeyListener onKeyListener = new OnKeyListener()
	{
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
		{
			if (keyCode == KeyEvent.KEYCODE_BACK)
			{
				// dismissProgress();
				return true;
			}
			return false;
		}
	};

	/**
	 * 关闭进度条 <br>
	 * 日期：2014年12月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void dismissProgress()
	{
		try
		{
			if (progressDialog != null && progressDialog.isShowing())
			{
				progressDialog.dismiss();
			}

			progressDialog = null;
		} catch (Exception e)
		{
		}
	}

	/**
	 * 加入Activity堆栈 <br>
	 * 日期：2015年1月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void addToStack(Activity act)
	{
		if (activityStack == null)
		{
			activityStack = new ArrayList<Activity>();
		}

		activityStack.add(act);
	}

	/**
	 * 弹出Activity堆栈 <br>
	 * 日期：2015年1月4日 <br>
	 * 
	 * @author：davintan <br>
	 *                  返回类型：void
	 */
	public static void popFromStack(Activity act)
	{
		if (activityStack == null)
		{
			activityStack = new ArrayList<Activity>();
		}

		activityStack.remove(act);
	}
	
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
	 * Method_网络请求异常
	 * @return 结果
	 */
	public static void showNetworkException() {
		MyToast.showText(getCurrentContext().getString(R.string.network_exception));
	}
	
	/**
	 * 弹出软键盘
	 * @param context
	 * @param view
	 */
	public static void popSoftKeyBoard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	
	/**
	 * 隐藏软键盘
	 */
	public void hideSoftKeyBoard(Context context, View editText) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public static Bitmap getImage(String Url) throws Exception {
		try {
			URL url = new URL(Url);
			String responseCode = url.openConnection().getHeaderField(0);
			if (responseCode.indexOf("200") < 0)
				throw new Exception("图片文件不存在或路径错误，错误代码：" + responseCode);
			return BitmapFactory.decodeStream(url.openStream());
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}

	}
	
}
