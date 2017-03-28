package com.landicorp.yinshang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.response.SaleResponse;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.LoginRespBean;
import com.landicorp.yinshang.db.LoginRespBeanDao;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MyToast;

import java.util.List;

/**
 * Created by u on 2017/1/9.商户信息页面
 */
public class SettingActivity extends BaseActivity implements ReqCallBack<SaleResponse> {

    private LinearLayout lv_left;
    private RelativeLayout layout_sign;//
    private RelativeLayout layout_modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settting);
        init();
    }

    private void init() {
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        layout_sign = (RelativeLayout) findViewById(R.id.layout_sign);
        layout_modify = (RelativeLayout) findViewById(R.id.layout_modify);
        layout_sign.setOnClickListener(clickListener);
        layout_modify.setOnClickListener(clickListener);
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
                case R.id.lv_left:
                    finish();
                    break;
                case R.id.layout_sign:
                    startActivity(new Intent(SettingActivity.this, ReLoginActivity.class));
                    break;
                case R.id.layout_modify:
                    startActivity(new Intent(SettingActivity.this, ModifyActivity.class));
                    break;

            }
        }
    };
}
