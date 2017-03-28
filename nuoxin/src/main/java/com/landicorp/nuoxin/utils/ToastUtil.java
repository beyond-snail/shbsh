package com.landicorp.nuoxin.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.landicorp.nuoxin.R;

public class ToastUtil extends Toast {  
  
    public ToastUtil(Context context) {  
        super(context);  
    }  
      
    public static Toast makeText(Context context, int resId, CharSequence text, int duration) {  
        Toast result = new Toast(context);  
          
        //获取LayoutInflater对象  
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
        //由layout文件创建一个View对象  
        View layout = inflater.inflate(R.layout.my_toast, null);  
          
        //实例化ImageView和TextView对象  
        ImageView imageView = (ImageView) layout.findViewById(R.id.taostIV);
        TextView textView = (TextView) layout.findViewById(R.id.toastTV);  
          
        imageView.setImageResource(resId);  
        textView.setText(text);  
          
        result.setView(layout);  
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
        result.setDuration(duration);  
          
        return result;  
    } 
    
    public static Toast makeText(Context context, CharSequence text) {  
    	return makeText(context, R.color.nulls, text, Toast.LENGTH_SHORT);
    }  
  
}  
