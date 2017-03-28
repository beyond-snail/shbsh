package com.landicorp.yinshang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
import com.landicorp.yinshang.utils.StringUtils;
import com.landicorp.yinshang.utils.Util;
import com.landicorp.yinshang.view.ClearEditText;
import com.landicorp.yinshang.zfbj.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by u on 2017/1/9.
 */
public class LoginActivity extends BaseActivity implements ReqCallBack<LoginResponse> {

    private String seriesNo = "";
    private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    private Button btn_cancel;
    private Button btn_sign;
    private ClearEditText login_username;
    private ClearEditText login_password;
    private String username = "01";
    private String password = "0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        login_username = (ClearEditText) findViewById(R.id.login_username);
        login_password = (ClearEditText) findViewById(R.id.login_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = SharePreferenceHelper.getInstance(LoginActivity.this).getString("username").equals("") ? "01" : SharePreferenceHelper.getInstance(LoginActivity.this).getString("username");
        password = SharePreferenceHelper.getInstance(LoginActivity.this).getString("password").equals("") ? "0000" : SharePreferenceHelper.getInstance(LoginActivity.this).getString("password");
        if(SharePreferenceHelper.getInstance(LoginActivity.this).getString("Date") != null) {
            String date = SharePreferenceHelper.getInstance(LoginActivity.this).getString("Date");
            if(sf.format(new Date()).equals(date)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {

            }
        }
        btn_cancel.setOnClickListener(clickListener);
        btn_sign.setOnClickListener(clickListener);
    }

    private void login() {
        seriesNo = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        BaseReqBean<LoginReqSubBean> baseReqBean = new BaseReqBean<LoginReqSubBean>();
        baseReqBean.setCmd("posSignIn");
//        LoginReqBean requestBean = new LoginReqBean();
//        requestBean.setCmd("posSignIn");
        LoginReqSubBean requestSubBean = new LoginReqSubBean();
        String verify = MD5Util.md5(seriesNo + Constant.KEY);
        requestSubBean.setVerify(verify);
        requestSubBean.setPos_number(seriesNo);
//        requestBean.setParams(requestSubBean);
//        String postInfoStr = gson.toJson(requestBean);
        baseReqBean.setParams(requestSubBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.login(getBody(postInfoStr)), this);
    }

    @Override
    public void onReqSuccess(LoginResponse result) {
//        MyToast.showText(result.code+ "-----------" + result.result.sid);
        //sid=11
        if(result != null && result.code.equals(Constant.SUCCESS_CODE)) {
            LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
            dao.deleteAll();
            dao.insert(result.result);//插入数据库
            SharePreferenceHelper.getInstance(LoginActivity.this).putInt("SID", result.result.sid);
            SharePreferenceHelper.getInstance(LoginActivity.this).putString("Date", sf.format(new Date()));
            SharePreferenceHelper.getInstance(LoginActivity.this).putString("username", username);
            SharePreferenceHelper.getInstance(LoginActivity.this).putString("password", password);

            SPUtils.put(LoginActivity.this, Constant.SHIFT_ROOM_TIME, StringUtils.getCurTime());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            MyToast.showText(result.msg);
        }
    }

    @Override
    public void onReqFailed(String errorMsg) {
        MyToast.showText("登录失败" );
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_cancel:
                    finish();
                    break;
                case R.id.btn_sign:
                    if(!Util.isConnected()) {
                        MyToast.showText(getString(R.string.no_network));
                        return;
                    }
                    if(login_username.getText().toString().equals(username) && login_password.getText().toString().equals(password)) {
                        login();
                    } else {
                        MyToast.showText("用户名或密码错误");
                        return;
                    }
                    break;
            }
        }
    };
}
