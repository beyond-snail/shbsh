package com.landicorp.yinshang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.RequestManager;
import com.landicorp.yinshang.data.model.BaseReqBean;
import com.landicorp.yinshang.data.model.MemberReqSubBean;
import com.landicorp.yinshang.data.response.MemberResponse;
import com.landicorp.yinshang.myokhttp.util.LogUtils;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.view.ClearEditText;
import com.landicorp.yinshang.view.InputAmt1;
import com.landicorp.yinshang.zxing.android.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by u on 2017/1/9.输入金额界面
 */
public class InputAmtActivity extends BaseActivity implements ReqCallBack<MemberResponse> {

    private LinearLayout lv_left;
    private InputAmt1 inputAmt;
    private RadioGroup radgoup_member;
    private RadioButton radBtn_member;
    private RadioButton radBtn_not_member;
    private EditText editText_amount;
//    private EditText editText_amount;// 元
//    private EditText editText_amount00;// 角和分
//    private LinearLayout linearLayout;
    private Button btnConfirm;
    private TextView textView;
    private String hint = "请输入金额";
    private int outputAmount = 0;//分 （单位）
    private TextView otherTextView;
    private ClearEditText edt_memberNo;
    private Button btn_scan;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

    boolean isScanSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_amt1);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        edt_memberNo = (ClearEditText) findViewById(R.id.edt_memberNo);
        btn_scan = (Button) findViewById(R.id.btn_scan);
        radgoup_member = (RadioGroup) findViewById(R.id.radgoup_member);
        radBtn_member = (RadioButton) findViewById(R.id.radBtn_member);
        radBtn_not_member = (RadioButton) findViewById(R.id.radBtn_not_member);
        radBtn_member.setChecked(true);
        lv_left.setOnClickListener(clickListener);
        btn_scan.setOnClickListener(clickListener);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        inputAmt = (InputAmt1) findViewById(R.id.input_amt);
        editText_amount = (EditText) findViewById(R.id.textView_amount);
//        editText_amount.setCursorVisible(false);
//        editText_amount.setFocusable(false);
//        editText_amount.setFocusableInTouchMode(false);
//        editText_amount00 = (EditText) findViewById(R.id.textView_amount00);
//        editText_amount00.setCursorVisible(false);
//        editText_amount00.setFocusable(false);
//        editText_amount00.setFocusableInTouchMode(false);
        // inputAmt.init(editText_amount);
//        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout_input_amt);
        inputAmt.init(editText_amount);
//        inputAmt.setAttri(true, 9);
        inputAmt.setOnPayClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(SharePreferenceHelper.getInstance(InputAmtActivity.this).getString("Date") != null) {
                    String date = SharePreferenceHelper.getInstance(InputAmtActivity.this).getString("Date");
                    if(!sf.format(new Date()).equals(date)) {
                        MyToast.showText("请先签到");
                        return;
                    }
                }
                outputAmount = inputAmt.getAmount();
//                String amount = inputAmt.getAmount();
//                final double amt = Double.parseDouble(amount);
                if (outputAmount == 0) {
//                    inputAmt.Reset_InputAmt();
                    MyToast.showText(hint);
                    return;
                }
                if (outputAmount > 1000000000) {
                    MyToast.showText("金额过大，超过系统处理范围");
                    return;
                }
//                BigDecimal a1 = new BigDecimal(Double.toString(amt));
//                BigDecimal b1 = new BigDecimal(Double.toString(100));
//                BigDecimal result1 = a1.multiply(b1);
//                inputAmt.text = result1.intValue() + "";
//                outputAmount = result1.intValue();
                if(radBtn_member.isChecked()) {
                    if(edt_memberNo.getText() == null || "".equals(edt_memberNo.getText().toString())) {
                        MyToast.showText("请输入您的会员卡号");
                    } else {
                        getMemberInfo(edt_memberNo.getText().toString(),outputAmount);
                    }
                } else {
//                    inputAmt.resetAmount();
//                    edt_memberNo.setText("");
//                    radBtn_member.setChecked(true);
                    isScanSuccess = false;
                    Intent intent = new Intent(InputAmtActivity.this, GatheringActivity.class);
                    Bundle bd = new Bundle();
                    bd.putInt("outputAmount", outputAmount);//分
                    bd.putBoolean("isMember", false);//是否会员
                    intent.putExtras(bd);
                    startActivity(intent);
                }

            }
        });
//        edt_memberNo.setOnKeyListener(onKeyListener);
        radgoup_member.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.radBtn_member) {
                    btn_scan.setEnabled(true);
                    edt_memberNo.setEnabled(true);
                } else if(checkedId == R.id.radBtn_not_member) {
                    edt_memberNo.setText("");
                    btn_scan.setEnabled(false);
                    edt_memberNo.setEnabled(false);
                }
            }
        });


    }

    private void deleteTransRecord() {
//        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
//        Query query = dao.queryBuilder().where(
//                TransactionReqSubBeanDao.Properties.T.between(startDate.getTime()/1000,endDate.getTime()/1000))
//                .build();
//        transList = query.list();
    }

    private void getMemberInfo(String memberNo, int amount) {
        BaseReqBean<MemberReqSubBean> baseReqBean = new BaseReqBean<MemberReqSubBean>();
        baseReqBean.setCmd("benefits");
        MemberReqSubBean memberReqSubBean = new MemberReqSubBean();
        StringBuffer sb = new StringBuffer();

        sb.append(memberNo);
        sb.append(SharePreferenceHelper.getInstance(InputAmtActivity.this).getString("username"));
        sb.append(((BaseApplication)getApplication()).getSystemInfo().getSN());
        sb.append(SharePreferenceHelper.getInstance(InputAmtActivity.this).getInt("SID"));
        sb.append(amount);
        sb.append(Constant.KEY);
        LogUtils.e(sb.toString());
        String verify = MD5Util.md5(sb.toString());
        memberReqSubBean.setVerify(verify);
//        memberReqSubBean.setSid(11);
        memberReqSubBean.setSid(SharePreferenceHelper.getInstance(InputAmtActivity.this).getInt("SID"));
        memberReqSubBean.setMobile(memberNo);
        memberReqSubBean.setTradeMoney(amount);
        memberReqSubBean.setSerialNum(((BaseApplication)getApplication()).getSystemInfo().getSN());
        memberReqSubBean.setOperator_num(SharePreferenceHelper.getInstance(InputAmtActivity.this).getString("username"));
        baseReqBean.setParams(memberReqSubBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.getMemberInfo(getBody(postInfoStr)), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isScanSuccess) {
            inputAmt.resetAmount();
            edt_memberNo.setText("");
            radBtn_member.setChecked(true);
        }
    }

    @Override
    public void onReqSuccess(MemberResponse result) {
//        MyToast.showText(result.code+ "-----------" );
        if (result.code.equals(Constant.SUCCESS_CODE)) {
//            inputAmt.resetAmount();
//            edt_memberNo.setText("");
//            radBtn_member.setChecked(true);
            isScanSuccess = false;
            Intent intent = new Intent(InputAmtActivity.this, MemberActivity.class);
            Bundle bd = new Bundle();
            bd.putParcelable("MemberResp", result.result);
            bd.putParcelableArrayList("coupons", result.result.coupons);
            bd.putInt("outputAmount", outputAmount);
            intent.putExtras(bd);
            startActivity(intent);
        } else {
            MyToast.showText(result.msg);
        }
    }

    @Override
    public void onReqFailed(String errorMsg) {
        MyToast.showText("请求失败" );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                edt_memberNo.setText("" + content);
                edt_memberNo.setSelection(content.length());
                isScanSuccess = true;
//                qrCodeImage.setImageBitmap(bitmap);
            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_scan:
                    Intent intent = new Intent(InputAmtActivity.this,
                            CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                    break;
                case R.id.lv_left:
                    finish();
                    break;
            }
        }
    };

    View.OnKeyListener onKeyListener = new View.OnKeyListener() {// 输入完后按键盘上的搜索键【回车键改为了搜索键】

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {// 修改回车键功能
                outputAmount = inputAmt.getAmount();
//                String amount = inputAmt.getAmount();
//                final double amt = Double.parseDouble(amount);
                if (outputAmount == 0) {
//                    inputAmt.Reset_InputAmt();
                    MyToast.showText(hint);
                    return false;
                }
                if (outputAmount > 1000000000) {
                    MyToast.showText("金额过大，超过系统处理范围");
                    return false;
                }
//                BigDecimal a1 = new BigDecimal(Double.toString(amt));
//                BigDecimal b1 = new BigDecimal(Double.toString(100));
//                BigDecimal result1 = a1.multiply(b1);
//                inputAmt.text = result1.intValue() + "";
//                outputAmount = result1.intValue();
                if(radBtn_member.isChecked()) {
                    if(edt_memberNo.getText() == null || "".equals(edt_memberNo.getText().toString())) {
                        MyToast.showText("请输入您的会员卡号");
                    } else {
                        getMemberInfo(edt_memberNo.getText().toString(),outputAmount);
                    }
                } else {
                    Intent intent = new Intent(InputAmtActivity.this, GatheringActivity.class);
                    Bundle bd = new Bundle();
                    bd.putInt("outputAmount", outputAmount);//分
                    bd.putBoolean("isMember", false);//是否会员
                    intent.putExtras(bd);
                    startActivity(intent);
                }
            }
            return false;
        }
    };
}
