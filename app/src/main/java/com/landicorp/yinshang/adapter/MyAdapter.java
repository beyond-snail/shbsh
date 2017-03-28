package com.landicorp.yinshang.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.landicorp.yinshang.R;
import com.landicorp.yinshang.data.model.CouponBean;
import com.landicorp.yinshang.utils.AmountUtil;
import com.landicorp.yinshang.utils.MyToast;


public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CouponBean> list;
    private ArrayList<CouponBean> selectCoupons;
    TextView txt_allMoney;

    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();

    class ViewHolder {

        CheckBox cb;
        TextView txt_coupon_sn;//优惠券ID
        TextView txt_coupon_name;//优惠券名称
        TextView txt_coupon_money;//优惠券金额
        TextView txt_more_select;//多选
    }

    public MyAdapter(Context context, ArrayList<CouponBean> list, TextView txt_allMoney) {
        this.list = list;
        this.context = context;
        this.txt_allMoney = txt_allMoney;
//        this.selectCoupons = selectCoupons;
//        isSelected = new HashMap<Integer, Boolean>();
//        list.get(0).setCanMultiChoose(true);
//        list.get(1).setCanMultiChoose(true);
        // 初始化数据
//        initDate();
    }

    // 初始化isSelected的数据
//    private void initDate() {
//        for (int i = 0; i < list.size(); i++) {
//            boolean isExist = false;
//            for(CouponBean bean : selectCoupons) {
//                if(bean.getId() == list.get(i).getId() && bean.getSn().equals(list.get(i).getSn())) {
//                    isExist = true;
//                    break;
//                }
//            }
//            getIsSelected().put(i, isExist);
//        }
//    }

    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 页面
        ViewHolder holder;
        CouponBean bean = list.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.listviewitem, null);
            holder = new ViewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            holder.txt_coupon_sn = (TextView) convertView.findViewById(R.id.txt_coupon_sn);
            holder.txt_coupon_money = (TextView) convertView.findViewById(R.id.txt_coupon_money);
            holder.txt_coupon_name = (TextView) convertView.findViewById(R.id.txt_coupon_name);
            holder.txt_more_select = (TextView) convertView.findViewById(R.id.txt_more_select);
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_coupon_sn.setText(bean.getSn());
        double amt = (double)bean.getMoney();
        holder.txt_coupon_money.setText("¥ " + AmountUtil.divide(amt,100d, 2));
        holder.txt_coupon_name.setText(bean.getName());
        if(bean.isCanMultiChoose()) {
            holder.txt_more_select.setText("支持多选");
        } else {
            holder.txt_more_select.setText("不支持多选");
        }
        // 监听checkBox并根据原来的状态来设置新的状态
        holder.cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                    setIsSelected(isSelected);
                    notifyDataSetChanged();
                } else {
                    if(!list.get(position).isCanMultiChoose()) {//不能多选
                        boolean isChange = true;//是否当前这个选项可以被选，取决于已经被选的中没有 不支持多选的优惠券
                        for (Map.Entry<Integer, Boolean> entry: isSelected.entrySet()) {
                            boolean value = entry.getValue();
                            int pos = entry.getKey();
                            if(value && !list.get(pos).isCanMultiChoose()) {
                                isChange = false;
                            }
                        }
                        if(isChange) {
                            initDate();
                            isSelected.put(position, true);
                            setIsSelected(isSelected);
                        } else {
                            MyToast.showText("不支持多选");
                        }
                    } else {
                        boolean isChange = true;
                        for (Map.Entry<Integer, Boolean> entry: isSelected.entrySet()) {
                            boolean value = entry.getValue();
                            int pos = entry.getKey();
                            if(value && !list.get(pos).isCanMultiChoose()) {
                                isChange = false;
                            }
                        }
                        if(isChange) {
                            isSelected.put(position, true);
                            setIsSelected(isSelected);
                        } else {
                            MyToast.showText("不支持多选");
                        }
                    }
                    notifyDataSetChanged();
                    int allAmt = 0;
                    for (Map.Entry<Integer, Boolean> entry: isSelected.entrySet()) {
                        boolean value = entry.getValue();
                        int pos = entry.getKey();
                        if(value)
                        allAmt +=list.get(pos).getMoney();
                    }
                    String styledText1 = "优惠总金额<font color='red'>" + AmountUtil.divide((double)(allAmt), 100d,2)+"</font>元";
                    txt_allMoney.setText(Html.fromHtml(styledText1), TextView.BufferType.SPANNABLE);
                }

            }
        });
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        MyAdapter.isSelected = isSelected;
    }
}