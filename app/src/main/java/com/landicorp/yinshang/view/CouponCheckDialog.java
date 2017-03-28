package com.landicorp.yinshang.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.landicorp.yinshang.R;
import com.landicorp.yinshang.adapter.CouponAdapter;
import com.landicorp.yinshang.data.model.CouponBean;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by u on 2017/1/10.
 */

public class CouponCheckDialog extends Dialog {

    public interface OnCoupCheckDialogListener {
        public void couponMoney(String money);
    }

    private Context context;
    private OnCoupCheckDialogListener customDialogListener;
    private ListView listview_coupons;
    private ArrayList<CouponBean> couponBeanList;
    private HashSet<CouponBean> selectOpponentList = new HashSet<CouponBean>();
    private CouponAdapter adapter;

    public CouponCheckDialog(Context context, ArrayList<CouponBean> couponBeanList, OnCoupCheckDialogListener customDialogListener) {
        super(context);
        this.context = context;
        this.customDialogListener = customDialogListener;
        this.couponBeanList = couponBeanList;
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_check);
//		setTitle(name);
        listview_coupons = (ListView) findViewById(R.id.listview_coupons);
        Button confirmBtn = (Button) findViewById(R.id.dialog_confirm_btn);
        Button cancelBtn = (Button) findViewById(R.id.dialog_cancel_btn);
        adapter = new CouponAdapter(context, couponBeanList, selectOpponentList);
        listview_coupons.setAdapter(adapter);
        confirmBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
        initConfig();
    }

    private void initConfig() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ConstValue.SCREEN_WIDTH * 0.96);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.dialog_confirm_btn) {
                customDialogListener.couponMoney("");
                CouponCheckDialog.this.dismiss();
            } else if(v.getId() == R.id.dialog_cancel_btn) {
                CouponCheckDialog.this.dismiss();
            }
        }
    };
}
