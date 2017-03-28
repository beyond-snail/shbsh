package com.landicorp.yinshang.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.landicorp.yinshang.BaseApplication;

/**
 * 自定义Toast显示类
 * <br>日期：2015年1月24日
 * <br>@author：davintan
 */
@SuppressLint("ShowToast")
public class MyToast
{
	/**
	 * 单例提示
	 */
	private static Toast mToast = null;
	
	/**
	 * 系统Toast消息提示单例方法
	 * <br>日期：2015年1月24日
	 * <br>@author：lixin
	 * <br>返回类型：Toast
	 */
	public static void showText(CharSequence text)
	{
		if(mToast == null)
		{
			mToast = Toast.makeText(BaseApplication.mContext, text, Toast.LENGTH_SHORT);
		}else
		{
			mToast.setText(text);                 
			mToast.setDuration(Toast.LENGTH_SHORT);   
		}
		
		mToast.show();
	}
	
	/**
	 * 系统Toast消息提示单例方法
	 * <br>日期：2015年1月24日
	 * <br>@author：lixin
	 * <br>返回类型：Toast
	 */
	public static void showTextLong(CharSequence text)
	{
		if(mToast == null)
		{
			mToast = Toast.makeText(BaseApplication.mContext, text, Toast.LENGTH_LONG);
		}else
		{
			mToast.setText(text);                 
			mToast.setDuration(Toast.LENGTH_LONG);   
		}
		
		mToast.show();
	}
	
	/**
	 * 系统Toast消息提示单例方法
	 * <br>日期：2015年1月24日
	 * <br>@author：lixin
	 * <br>返回类型：Toast
	 */
	public static void showText(CharSequence text, int time)
	{
		if(mToast == null)
		{
			mToast = Toast.makeText(BaseApplication.mContext, text, Toast.LENGTH_SHORT);
		}else
		{
			mToast.setText(text);                 
			mToast.setDuration(time);   
		}
		
		mToast.show();
	}
	
	public static void cancel()
	{
		if(mToast != null)
		{
			mToast.cancel();
		}
	}
}
