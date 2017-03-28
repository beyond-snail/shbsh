package com.landicorp.yinshang;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
public class VentorActivity extends BaseActivity implements ReqCallBack<SaleResponse> {

    private LinearLayout lv_left;
    private TextView txt_merchantNo;//商户号
    private TextView txt_merchantName;//商户名称
    private TextView txt_terminalNo;//商户终端号
    private TextView txt_licenseNo;//营业执照号
    private TextView txt_fyMerchantNo;//扫码商户号
    private TextView txt_accountName;//收款账户
    private TextView txt_accountBank;//收款银行
    private TextView txt_accountNo;//收款帐号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventor);
        init();
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        if(list != null && list.size() > 0) {
            LoginRespBean bean = list.get(0);
            txt_merchantNo.setText(bean.getMerchantNo());
            txt_terminalNo.setText(bean.getTerminalNo());
            txt_merchantName.setText(bean.getTerminalName());
            txt_licenseNo.setText(bean.getLicenseNo());
            txt_fyMerchantNo.setText(bean.getFyMerchantNo());
            txt_accountName.setText(bean.getAccountName());
            txt_accountBank.setText(bean.getAccountBank());
            txt_accountNo.setText(bean.getAccountNo());
        }
    }

    private void init() {
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        txt_merchantName = (TextView) findViewById(R.id.txt_merchantName);
        txt_merchantNo = (TextView) findViewById(R.id.txt_merchantNo);
        txt_terminalNo = (TextView) findViewById(R.id.txt_terminalNo);
        txt_licenseNo = (TextView) findViewById(R.id.txt_licenseNo);
        txt_fyMerchantNo = (TextView) findViewById(R.id.txt_fyMerchantNo);
        txt_accountName = (TextView) findViewById(R.id.txt_accountName);
        txt_accountBank = (TextView) findViewById(R.id.txt_accountBank);
        txt_accountNo = (TextView) findViewById(R.id.txt_accountNo);
        lv_left.setOnClickListener(clickListener);
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

            }
        }
    };
}
