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
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.view.ClearEditText;
import com.landicorp.yinshang.view.InputAmt;
import com.landicorp.yinshang.view.InputAmt1;
import com.landicorp.yinshang.zxing.android.CaptureActivity;

import java.math.BigDecimal;

/**
 * Created by u on 2017/1/9.输入金额界面
 */
public class InputAmtActivity1 extends BaseActivity {

    private LinearLayout lv_left;
    private InputAmt1 inputAmt;
    private RadioGroup radgoup_member;
    private RadioButton radBtn_member;
    private RadioButton radBtn_not_member;
    private EditText editText_amount;// 元
    private EditText editText_amount00;// 角和分
    private LinearLayout linearLayout;
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
    private EditText  textView_amount1;

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
        textView_amount1= (EditText) findViewById(R.id.textView_amount);
        radBtn_member.setChecked(true);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        inputAmt = (InputAmt1) findViewById(R.id.input_amt);
        editText_amount = (EditText) findViewById(R.id.textView_amount);
        editText_amount.setCursorVisible(false);
        editText_amount.setFocusable(false);
        editText_amount.setFocusableInTouchMode(false);
        editText_amount00 = (EditText) findViewById(R.id.textView_amount00);
        editText_amount00.setCursorVisible(false);
        editText_amount00.setFocusable(false);
        editText_amount00.setFocusableInTouchMode(false);
        // inputAmt.init(editText_amount);
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout_input_amt);
        inputAmt.init(textView_amount1);
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

}
