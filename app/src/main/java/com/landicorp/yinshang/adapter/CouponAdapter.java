package com.landicorp.yinshang.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.landicorp.yinshang.R;
import com.landicorp.yinshang.data.model.CouponBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class CouponAdapter extends BaseAdapter {

	private ArrayList<CouponBean> list;
	private Context context;
	// 用来控制CheckBox的选中状况  
    private static HashMap<Integer,Boolean> isSelected;  
    private Set<CouponBean> selectList;

	public CouponAdapter(Context context, ArrayList<CouponBean> list, Set<CouponBean> selectList) {
		this.list = list;
		this.context = context;
		this.selectList = selectList;
		isSelected = new HashMap<Integer, Boolean>();  
        // 初始化数据  
        initDate();  
	}
	
	// 初始化isSelected的数据  
    private void initDate(){  
    	for(int i=0; i<list.size(); i++) {
    		if(selectList.contains(list.get(i))) {
    			 getIsSelected().put(i,true);  
    		} else {
    			 getIsSelected().put(i,false);  
    		}
    	}
    }  
//	// 初始化isSelected的数据  
//    private void initDate(){  
//        for(int i=0; i<list.size();i++) {  
//            getIsSelected().put(i,false);  
//        }  
//    }  
//    
    public void update(int size) {
    	int k = isSelected.size();
    	for(int i=isSelected.size(); i<size+k;i++) {  
            getIsSelected().put(i,false);  
        }  
    }
    
    public void update(List<CouponBean> addList, Set<CouponBean> selectList) {
//    	int size = addList.size();
    	int k = isSelected.size();
//    	for(int i=isSelected.size(); i<size+k;i++) {  
//            getIsSelected().put(i,false);  
//        }  
    	
    	for(int i=0; i<addList.size(); i++) {
    		if(selectList.contains(addList.get(i))) {
    			getIsSelected().put(i+k,true); 
    		} else {
    			getIsSelected().put(i+k,false); 
    		}
    	}
//    	for(int i=0; i<addList.size(); i++) {
//    		boolean isExist = false;
//    		for(OpponentBean select : selectList) {
//    			if(addList.get(i).getMemberId().equals(select.getMemberId())) {
//    				Log.i("name", addList.get(i).getNickName()+"-------------");
//    				isExist = true;
////    				getIsSelected().put(i+k,true);
//    			}
//    		}
//    		if(isExist) {
//    			getIsSelected().put(i+k,true); 
//    		} else {
//    			getIsSelected().put(i+k,false); 
//    		}
//    	}
    }
    
    public static HashMap<Integer,Boolean> getIsSelected() {  
        return isSelected;  
    }  
  
    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {  
    	CouponAdapter.isSelected = isSelected;
    } 

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listview_coupon, null);
			holder.txt_coupon_sn = (TextView) convertView.findViewById(R.id.txt_coupon_sn);
			holder.txt_coupon_name = (TextView) convertView.findViewById(R.id.txt_coupon_name);
			holder.txt_coupon_money = (TextView) convertView.findViewById(R.id.txt_coupon_money);
			holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_coupon_sn.setText(String.valueOf(list.get(position).getSn()));
		holder.txt_coupon_name.setText(list.get(position).getName());
		holder.txt_coupon_money.setText(String.valueOf(list.get(position).getMoney()));
		// 根据isSelected来设置checkbox的选中状况
//		if(getIsSelected().get(position) != null)
			holder.cb.setChecked(getIsSelected().get(position)); 
		return convertView;
	}

	public class ViewHolder {
		public TextView txt_coupon_sn;//sn
		public TextView txt_coupon_name;//名称
		public TextView txt_coupon_money;//金额
		public CheckBox cb;
	}

}
