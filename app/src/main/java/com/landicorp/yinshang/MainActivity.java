package com.landicorp.yinshang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.response.SaleResponse;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.zfbj.ShiftRoomActivity;

/**
 * Created by u on 2017/1/9.
 */
public class MainActivity extends BaseActivity implements ReqCallBack<SaleResponse> {

    private LinearLayout btn_shouyin;
    private LinearLayout btn_shanghu;
    private LinearLayout btn_trans_record;
    private LinearLayout btn_modify_passwrd;
    private LinearLayout btn_sign;
    private LinearLayout btn_bj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        btn_shouyin = (LinearLayout) findViewById(R.id.btn_shouyin);
        btn_shanghu = (LinearLayout) findViewById(R.id.btn_shanghu);
        btn_trans_record = (LinearLayout) findViewById(R.id.btn_trans_record);
        btn_modify_passwrd = (LinearLayout) findViewById(R.id.btn_modify_passwrd);
        btn_sign = (LinearLayout) findViewById(R.id.btn_sign);
        btn_bj = (LinearLayout) findViewById(R.id.btn_bj);
//        btn_setting.setOnClickListener(clickListener);
        btn_shouyin.setOnClickListener(clickListener);
        btn_shanghu.setOnClickListener(clickListener);
        btn_trans_record.setOnClickListener(clickListener);
        btn_sign.setOnClickListener(clickListener);
        btn_modify_passwrd.setOnClickListener(clickListener);
        btn_bj.setOnClickListener(clickListener);
    }


    @Override
    public void onReqSuccess(SaleResponse result) {
        if(result != null && result.code.equals(Constant.SUCCESS_CODE)) {
        }
    }

    @Override
    public void onReqFailed(String errorMsg) {
        MyToast.showText("请求失败" );
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_shouyin:
                    startActivity(new Intent(MainActivity.this, InputAmtActivity.class));
                    break;
                case R.id.btn_trans_record:
                    startActivity(new Intent(MainActivity.this,
                        TransRecordActivity.class));
                    break;
                case R.id.btn_shanghu:
                    startActivity(new Intent(MainActivity.this,
                            VentorActivity.class));
                    break;
//                case R.id.btn_setting:
//                    startActivity(new Intent(MainActivity.this,
//                            SettingActivity.class));
//                    break;
                case R.id.btn_modify_passwrd:
                    startActivity(new Intent(MainActivity.this,
                            ModifyActivity.class));
                    break;
                case R.id.btn_sign:
                    startActivity(new Intent(MainActivity.this,
                            ReLoginActivity.class));
                    break;
                case R.id.btn_bj:
                    startActivity(new Intent(MainActivity.this, ShiftRoomActivity.class));
                    break;

            }
        }
    };
}
