package com.landicorp.yinshang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.RequestManager;
import com.landicorp.yinshang.data.model.BaseReqBean;
import com.landicorp.yinshang.data.model.LoginReqSubBean;
import com.landicorp.yinshang.data.response.LoginResponse;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.LoginRespBeanDao;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.utils.Util;
import com.landicorp.yinshang.view.ClearEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by u on 2017/1/9.
 */
public class ModifyActivity extends BaseActivity  {

    private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    private Button btn_cancel;
    private Button btn_sign;
    private ClearEditText login_username;
    private ClearEditText login_password;
    private ClearEditText login_original_password;
    private LinearLayout lv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        login_username = (ClearEditText) findViewById(R.id.login_username);
        login_password = (ClearEditText) findViewById(R.id.login_password);
        login_original_password = (ClearEditText) findViewById(R.id.login_original_password);
        btn_cancel.setOnClickListener(clickListener);
        btn_sign.setOnClickListener(clickListener);
        lv_left.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lv_left:
                    finish();
                    break;
                case R.id.btn_cancel:
                    finish();
                    break;
                case R.id.btn_sign:
//                    if(login_username.getText().toString().trim().equals("")) {
//                        MyToast.showText("用户名不能为空");
//                        return;
//                    }
                    if(login_password.getText().toString().trim().equals("")) {
                        MyToast.showText("密码不能为空");
                        return;
                    }
                    String originalPassword = SharePreferenceHelper.getInstance(ModifyActivity.this).getString("password");
                    if(!originalPassword.equals(login_original_password.getText().toString())) {
                        MyToast.showText("原密码不正确");
                        return;
                    }
                    SharePreferenceHelper.getInstance(ModifyActivity.this).putString("username", login_username.getText().toString());
                    SharePreferenceHelper.getInstance(ModifyActivity.this).putString("password", login_password.getText().toString());
                    MyToast.showText("修改成功");
                    ModifyActivity.this.finish();
                    break;
            }
        }
    };
}
