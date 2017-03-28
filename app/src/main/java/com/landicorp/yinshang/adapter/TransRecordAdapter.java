package com.landicorp.yinshang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.landicorp.yinshang.R;
import com.landicorp.yinshang.data.model.CouponBean;
import com.landicorp.yinshang.db.TransactionReqSubBean;
import com.landicorp.yinshang.utils.AmountUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class TransRecordAdapter extends BaseAdapter {

	private SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private List<TransactionReqSubBean> list;
	private Context context;
	// 用来控制CheckBox的选中状况

	public TransRecordAdapter(Context context, List<TransactionReqSubBean> list) {
		this.list = list;
		this.context = context;
        // 初始化数据
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
					R.layout.listview_trans_record, null);
			holder.txt_trans_num = (TextView) convertView.findViewById(R.id.txt_trans_num);
			holder.txt_trans_money = (TextView) convertView.findViewById(R.id.txt_trans_money);
			holder.txt_trans_time = (TextView) convertView.findViewById(R.id.txt_trans_time);
			holder.txt_trans_type = (TextView) convertView.findViewById(R.id.txt_trans_type);
			holder.txt_upload_status = (TextView) convertView.findViewById(R.id.txt_upload_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_trans_num.setText(list.get(position).getClientOrderNo());
//		if(list.get(position).getPayType() != 7) {
//			holder.txt_trans_money.setText("¥ " +  AmountUtil.divide((double) (list.get(position).getBankAmount()), (double) 100, 2));
//		} else {
//			holder.txt_trans_money.setText("¥ " + AmountUtil.divide((double) (list.get(position).getCash()), (double) 100, 2));
//		}
		//非现金支付时getCash 肯定是0，现金支付时getBankAmount肯定是0
//		int dingdanAmt = list.get(position).getBankAmount() + list.get(position).getCash() + list.get(position).getCouponCoverAmount() + list.get(position).getPointCoverAmount();
//		holder.txt_trans_money.setText("¥ " + AmountUtil.divide((double) dingdanAmt, (double) 100, 2));
		holder.txt_trans_money.setText("¥ " + AmountUtil.divide(Double.valueOf(list.get(position).getOrderAmount()), (double) 100, 2));
		String type ="";
		TransactionReqSubBean bean = list.get(position);
		switch (bean.getPayType()) {
			case 1:
				type = "支付宝";
				if(bean.getIsUndo() != null)
					type = "支付宝（已撤销）";
				break;
			case 3:
				type = "微信";
				if(bean.getIsUndo() != null)
					type = "微信（已撤销）";
				break;
			case 4:
				type = "百付宝";
				break;
			case 5:
				type = "京东";
				break;
			case 6:
				type = "刷卡";
				if(bean.getIsUndo() != null)
					type = "刷卡（已撤销）";
				break;
			case 7:
				type = "现金";
				break;
			case 10:
				type = "刷卡撤销";
				break;
			case 11:
				type = "微信撤销";
				break;
			case 12:
				type = "支付撤销";
				break;
			case 13:
				type = "钱包";
				if(bean.getIsUndo() != null)
					type = "钱包（已撤销）";
				break;
			case 14:
				type = "钱包撤销";
				break;
		}
		holder.txt_trans_type.setText(type);
		holder.txt_trans_time.setText(sf.format(new Date(list.get(position).getT()*1000)));
		if(list.get(position).getIsUploadSuccess()!=null) {
			holder.txt_upload_status.setText("已上传");
		} else {
			holder.txt_upload_status.setText("未上传");
		}
		return convertView;
	}

	public class ViewHolder {
		public TextView txt_trans_num;//订单号
		public TextView txt_trans_money;//金额
		public TextView txt_trans_time;//时间
		public TextView txt_trans_type;//类型
		public TextView txt_upload_status;//上传流水状态
	}

}
