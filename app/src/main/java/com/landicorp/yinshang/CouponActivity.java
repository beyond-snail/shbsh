package com.landicorp.yinshang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.landicorp.yinshang.adapter.MyAdapter;
import com.landicorp.yinshang.data.model.CouponBean;
import com.landicorp.yinshang.utils.AmountUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by u on 2017/1/9.
 */
public class CouponActivity extends BaseActivity  {

    private LinearLayout lv_left;
    private ArrayList<CouponBean> coupons;
    private Button confirmBtn;
    private Button cancelBtn;
    private TextView txt_allMoney;
    private MyAdapter adapter;
    private ArrayList<CouponBean> selectCoupons = new ArrayList<CouponBean>();//选择的优惠券

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_check);
        init();
    }

    private void init() {
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        lv_left.setOnClickListener(clickListener);
        txt_allMoney = (TextView) findViewById(R.id.txt_allMoney);
        Bundle bd = getIntent().getExtras();
        if(bd != null) {
            coupons = bd.getParcelableArrayList("coupons");
            selectCoupons = bd.getParcelableArrayList("selectCoupons");
        }
        ListView listview_coupons = (ListView) findViewById(R.id.listview_coupons);
        confirmBtn = (Button) findViewById(R.id.dialog_confirm_btn);
        cancelBtn = (Button) findViewById(R.id.dialog_cancel_btn);
        if(coupons == null)
            coupons = new ArrayList<CouponBean>();
        adapter = new MyAdapter(this,coupons, txt_allMoney);
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        for (int i = 0; i < coupons.size(); i++) {
            boolean isExist = false;
            for(CouponBean bean : selectCoupons) {
                if(bean.getId() == coupons.get(i).getId() && bean.getSn().equals(coupons.get(i).getSn())) {
                    isExist = true;
                    break;
                }
            }
            map.put(i, isExist);
        }
        MyAdapter.setIsSelected(map);
        listview_coupons.setAdapter(adapter);
        confirmBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
        int allAmt = 0;
        for(CouponBean bean : selectCoupons) {
            allAmt += bean.getMoney();
        }
        String styledText1 = "优惠总金额<font color='red'>" + AmountUtil.divide((double)(coupons.get(0).getMoney()), 100d,2)+"</font>元";
        txt_allMoney.setText(Html.fromHtml(styledText1), TextView.BufferType.SPANNABLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode) {
            case Activity.RESULT_CANCELED:
                break;
            case Activity.RESULT_OK:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lv_left:
                    finish();
                case R.id.dialog_cancel_btn:
                    finish();
                    break;
                case R.id.dialog_confirm_btn:
                    selectCoupons.clear();
                    HashMap<Integer, Boolean> map = adapter.getIsSelected();
                    for (Map.Entry<Integer, Boolean> entry: map.entrySet()) {
                        boolean value = entry.getValue();
                        int pos = entry.getKey();
                        if(value) {
                            selectCoupons.add(coupons.get(pos));
//                            couponMoney += coupons.get(pos).getMoney();
//                            selectCouponNum++;
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("selectCoupons", selectCoupons);
//                    bundle.putInt("couponMoney", couponMoney);
//                    bundle.putInt("selectCouponNum", selectCouponNum);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case R.id.btn_not_use:
                    break;

            }
        }
    };
}
