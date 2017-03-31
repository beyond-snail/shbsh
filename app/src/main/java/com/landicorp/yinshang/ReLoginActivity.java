package com.landicorp.yinshang;

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
import com.landicorp.yinshang.db.OperatorList;
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
import java.util.List;

/**
 * Created by u on 2017/1/9.
 */
public class ReLoginActivity extends BaseActivity implements ReqCallBack<LoginResponse> {

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
//        username = SharePreferenceHelper.getInstance(ReLoginActivity.this).getString("username").equals("") ? "01" : SharePreferenceHelper.getInstance(ReLoginActivity.this).getString("username");
//        password = SharePreferenceHelper.getInstance(ReLoginActivity.this).getString("password").equals("") ? "0000" : SharePreferenceHelper.getInstance(ReLoginActivity.this).getString("password");
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

    private void login1(String username, String password) {
        seriesNo = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        BaseReqBean<LoginReqSubBean> baseReqBean = new BaseReqBean<LoginReqSubBean>();
        baseReqBean.setCmd("posSignIn");
        LoginReqSubBean requestSubBean = new LoginReqSubBean();
        StringBuffer sb = new StringBuffer();
        sb.append(username);
        sb.append(password);
        sb.append(seriesNo);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        requestSubBean.setVerify(verify);
        requestSubBean.setPos_number(seriesNo);
        requestSubBean.setOperator_num(username);
        requestSubBean.setOperator_password(password);
        baseReqBean.setParams(requestSubBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.login(getBody(postInfoStr)), this);
    }


    @Override
    public void onReqSuccess(LoginResponse result) {
//        MyToast.showText(result.code+ "-----------" + result.result.sid);
        //sid=11
        if(result != null && result.code.equals(Constant.SUCCESS_CODE)) {

            //判断当前的操作员是否存在返回列表中
            List<OperatorList> lists = result.result.getOperatorLists();

            if (!(checkOpertorUserName(lists, login_username.getText().toString()) && checkOptertorPsw(lists, login_password.getText().toString()))){
                MyToast.showText("操作员账号或者密码错误");
                return;
            }

            LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
            dao.deleteAll();
            dao.insert(result.result);//插入数据库
            SharePreferenceHelper.getInstance(ReLoginActivity.this).putInt("SID", result.result.sid);
            SharePreferenceHelper.getInstance(ReLoginActivity.this).putString("Date", sf.format(new Date()));
            SharePreferenceHelper.getInstance(ReLoginActivity.this).putString("username", login_username.getText().toString());
            SharePreferenceHelper.getInstance(ReLoginActivity.this).putString("password", login_password.getText().toString());

            SPUtils.put(ReLoginActivity.this, Constant.SHIFT_ROOM_TIME, StringUtils.getCurTime());
            MyToast.showText("签到成功");
            finish();
        }
    }

    private boolean checkOpertorUserName(List<OperatorList> lists, String username) {
        for (OperatorList operatorBean : lists) {
            if (StringUtils.isEquals(username, operatorBean.getOperator_num())){
                return true;
            }
        }
        return false;
    }

    private boolean checkOptertorPsw(List<OperatorList> lists, String password){
        for (OperatorList operatorBean : lists){
            if (StringUtils.isEquals(password, operatorBean.getOperator_password())){
                return true;
            }
        }
        return false;
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
                    if (StringUtils.isEmpty(login_username.getText().toString())){
                        MyToast.showText("用户名为空");
                        return;
                    }
                    if (StringUtils.isEmpty(login_password.getText().toString())){
                        MyToast.showText("密码错误");
                        return;
                    }
                    login1(login_username.getText().toString(), login_password.getText().toString());
//                    if(login_username.getText().toString().equals(username) && login_password.getText().toString().equals(password)) {
////                        login();
//                        login1(login_username.getText().toString(), login_password.getText().toString());
//                    } else {
//                        MyToast.showText("用户名或密码错误");
//                        return;
//                    }
                    break;
            }
        }
    };
}
