package com.landicorp.nuoxin.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.landicorp.nuoxin.R;


/**
 * 自定义进度对话框
 * <br>日期：2014年12月5日
 * <br>@author：davintan
 */
public class CustomProgressDialog extends Dialog
{
	/**
	 * 应用上下文
	 */
	private static Context context = null;
	
	/**
	 * 单例：自定义进度对话框
	 */
	private static CustomProgressDialog customProgressDialog = null;
	
	/**
	 * 进度图片显示
	 */
	private ImageView imageView;
	
	/**
	 * 进度图片旋转动画
	 */
	private AnimationDrawable animationDrawable;
	
	/**
	 * 自定义进度对话框构造函数，为实现单例模式
	 * @param ctx
	 */
	private CustomProgressDialog(Context ctx)
	{
		super(context);
		this.context = ctx;
	}
	
	/**
	 * 自定义进度对话框构造函数，为实现单例模式
	 * @param ctx
	 * @param theme
	 */
	public CustomProgressDialog(Context ctx, int theme)
	{
		super(context, theme);
		this.context = ctx;
	}
	
	/**
	 * 单例进度对话框对象创建
	 * <br>日期：2014年12月5日
	 * <br>@author：davintan
	 * <br>返回类型：CustomProgressDialog
	 */
	public static CustomProgressDialog createDialog(Context ctx)
	{
		context = ctx;
		
		customProgressDialog = new CustomProgressDialog(ctx, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.layout_custom_progressdialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
		return customProgressDialog;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		if (customProgressDialog == null)
		{
			return;
		}
		
		imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		animationDrawable = (AnimationDrawable) imageView.getBackground();
		if (animationDrawable != null && !animationDrawable.isRunning())
		{
			animationDrawable.start();
		}
	}
	
	@Override
	public void show()
	{
		super.show();
		
		if(imageView == null)
		{
			imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		}
		
		Animation an = AnimationUtils.loadAnimation(context, R.anim.progress_rotation);
		imageView.startAnimation(an);
	}

	@Override
	public void dismiss()
	{
		if (animationDrawable != null && animationDrawable.isRunning())
		{
			animationDrawable.stop();
		}
		System.gc();
		super.dismiss();
	}
}