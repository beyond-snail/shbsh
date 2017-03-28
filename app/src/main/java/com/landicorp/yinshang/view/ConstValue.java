package com.landicorp.yinshang.view;

import android.app.Activity;
import android.os.Environment;

/**
 * 
 * @author davintan
 */
public class ConstValue
{
	
	/**
	 * 是否从Home键退出应用
	 */
	public static boolean isHomeExist = false;
	
	/**
	 * 文件是否在下载
	 */
	public static boolean isDownloading = false;
	
	/**
	 * 首页任务Tab提示个数广播
	 */
	public static final String BADGECHANGEACTION = "com.metlife.badgechange";
	
	/**
	 * 推送数据
	 */
	public static final String PUSHDATA = "fixedUserData";
	
	/**
	 * 主Activity
	 */
	public static Activity mainActivity = null;
	
	/**
	 * 文件上传路径
	 */
	public static String FILEUPLOAD = "";
	
	/**
	 * 文件下载路径
	 */
	public static String FILEDOWNLOAD = "";
	
	/**
	 * 数据获取
	 */
	public static String HTTPDATAGET = "";
	
	/**
	 * 任务模块操作列表序号
	 */
	public static int TASKACTION_POS = -1;
	
	/**
	 * 屏幕分辨率比
	 */
	public static float SCREEN_DENSITY = 0f;
	
	/**
	 * 屏幕宽带
	 */
	public static int SCREEN_WIDTH = 0;
	
	/**
	 * 屏幕高带
	 */
	public static int SCREEN_HEIGHT = 0;
	
	/**
	 * 外部存储卡路径
	 */
	public static String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath()+"/crm/";

	/**
	 * 登录用户
	 */
	public static String USERNAME = "username";
	
	/**
	 * 登录密码
	 */
	public static String PASSWORD = "password";
	/**
	 * 用户ID
	 */
	public static String USER_ID = "userId";
	/**
	 * 部门id
	 */
	public static String UNIT_ID = "unitID";
	
	/**
	 * 店长角色code
	 */
	public static final String ROLE_SHOP_MANAGER = "DZ";
	/**
	 * 仓管员角色code
	 */
	public static final String ROLE_WAREHOUSE_KEEPER = "CGY";
	/**
	 * 销售员角色code
	 */
	public static final String ROLE_SALES_PERSON = "YYY";
}
