package com.landicorp.yinshang;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.RequestManager;
import com.landicorp.yinshang.data.model.BaseReqBean;
import com.landicorp.yinshang.data.model.CouponBean;
import com.landicorp.yinshang.data.model.MemberRespBean;
import com.landicorp.yinshang.data.model.SaleReqSubBean;
import com.landicorp.yinshang.data.response.SaleResponse;
import com.landicorp.yinshang.utils.AmountUtil;
import com.landicorp.yinshang.utils.Base64;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.view.ClearEditText;
import com.landicorp.yinshang.view.InputMemberDialog;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by u on 2017/1/9.
 */
public class MemberActivity extends BaseActivity implements ReqCallBack<SaleResponse> {

    private LinearLayout lv_left;
    private TextView txt_member_name;//会员名
    private TextView txt_memberNo;//会员卡号
    private TextView txt_member_mobile;//手机号
    private TextView txt_member_integral;//可用积分
    private TextView txt_deduction_amt;//可抵扣
    private ClearEditText edt_deduction_amt;
    private CheckBox ckb_deduction_amt;
    private TextView txt_coupons;//可使用券
    private Button btn_coupons;//使用券列表按钮
    private TextView txt_used_coupons;//当前使用的优惠券
    private Button btn_next;//下一步
    private Button btn_not_use;//不使用权益
    private ArrayList<CouponBean> coupons;
    private MemberRespBean memberRespBean;
    private int outputAmount;//分（单位）
    public static int SELECT = 11;
    private ArrayList<CouponBean> selectCoupons = new ArrayList<CouponBean>();//选择的优惠券
    private String password;
    private boolean mianfei;

    public static MemberActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        instance = this;
        init();
        if(coupons != null && coupons.size() > 0) {
            CouponBean comparator = new CouponBean();
            Collections.sort(coupons, comparator);
            selectCoupons.add(coupons.get(0));
            String styledText1 = "当前使用优惠券<font color='red'>"+selectCoupons.size()+"</font>张,优惠金额<font color='red'>" +AmountUtil.divide((double)(coupons.get(0).getMoney()), 100d,2)+"</font>元";
            txt_used_coupons.setText(Html.fromHtml(styledText1), TextView.BufferType.SPANNABLE);
        }
    }

    private void init() {
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        txt_member_name = (TextView) findViewById(R.id.txt_member_name);
        txt_memberNo = (TextView) findViewById(R.id.txt_memberNo);
        txt_member_mobile = (TextView) findViewById(R.id.txt_member_mobile);
        txt_member_integral = (TextView) findViewById(R.id.txt_member_integral);
        txt_deduction_amt = (TextView) findViewById(R.id.txt_deduction_amt);
        edt_deduction_amt = (ClearEditText) findViewById(R.id.edt_deduction_amt);
        ckb_deduction_amt = (CheckBox) findViewById(R.id.ckb_deduction_amt);
        txt_coupons = (TextView) findViewById(R.id.txt_coupons);
        btn_coupons = (Button) findViewById(R.id.btn_coupons);
        txt_used_coupons = (TextView) findViewById(R.id.txt_used_coupons);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_not_use = (Button) findViewById(R.id.btn_not_use);
        lv_left.setOnClickListener(clickListener);
        btn_coupons.setOnClickListener(clickListener);
        btn_next.setOnClickListener(clickListener);
        btn_not_use.setOnClickListener(clickListener);
        Bundle bd = getIntent().getExtras();
        if(bd != null) {
            memberRespBean = bd.getParcelable("MemberResp");
            coupons = bd.getParcelableArrayList("coupons");
            outputAmount = bd.getInt("outputAmount");
            String ss = "{\"amt\":" + outputAmount +"}";
            if(memberRespBean != null) {
                txt_member_name.setText(memberRespBean.memberName);
                txt_memberNo.setText(memberRespBean.memberCardNo);
                txt_member_mobile.setText(memberRespBean.mobile);
                txt_member_integral.setText(String.valueOf(memberRespBean.point));
                txt_deduction_amt.setText("可抵扣" + 0.0 + "元");
                if(coupons == null || coupons.size() == 0) {
                    String styledText = "当前可用优惠券<font color='red'>"+0+"</font>张";
                    txt_coupons.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    String styledText1 = "当前使用优惠券<font color='red'>"+0+"</font>张";
                    txt_used_coupons.setText(Html.fromHtml(styledText1), TextView.BufferType.SPANNABLE);
                } else {
                    String styledText = "当前可用优惠券<font color='red'>"+coupons.size()+"</font>张";
                    txt_coupons.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
//                    String styledText1 = "当前使用优惠券<font color='red'>"+1+"</font>张";
//                    txt_used_coupons.setText(Html.fromHtml(styledText1), TextView.BufferType.SPANNABLE);
                }

            }
        }
        edt_deduction_amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    int maxPoint = AmountUtil.divide((double)(outputAmount), 100d,2) * memberRespBean.pointChangeRate > memberRespBean.pointUseMax ? memberRespBean.pointUseMax
                            : (int)(AmountUtil.divide((double)(outputAmount), 100d,2) * memberRespBean.pointChangeRate);//实际能使用最大积分
//                    int maxPoint = outputAmount/memberRespBean.pointChangeRate > memberRespBean.pointUseMax ? memberRespBean.pointUseMax : outputAmount/memberRespBean.pointChangeRate;//实际能使用最大积分
                    if(Integer.valueOf(charSequence.toString()) > maxPoint) {
                        edt_deduction_amt.removeTextChangedListener(this);
                        MyToast.showText("最大使用积分" + maxPoint);
                        edt_deduction_amt.setText("" + maxPoint);
                        edt_deduction_amt.setSelection(edt_deduction_amt.getText().toString().length());
                        txt_deduction_amt.setText("可抵扣" + AmountUtil.divide((double)(maxPoint), (double)(memberRespBean.pointChangeRate),2) + "元");
                        edt_deduction_amt.addTextChangedListener(this);
                    } else {
                        txt_deduction_amt.setText("可抵扣" + AmountUtil.divide(Double.valueOf(charSequence.toString()), (double)(memberRespBean.pointChangeRate),2) + "元");
                    }
                } else {
                    txt_deduction_amt.setText("可抵扣0.0元");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ckb_deduction_amt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    edt_deduction_amt.setText("");
                    edt_deduction_amt.setEnabled(true);
                } else {
                    edt_deduction_amt.setText("");
                    edt_deduction_amt.setEnabled(false);
                }
            }
        });
    }

    private void calculate(String memberName, String password) {
        this.password = password;
        StringBuffer couponStr = new StringBuffer();
        if(selectCoupons != null && selectCoupons.size() > 0) {
            int k = 0;
            for (CouponBean bean : selectCoupons) {
                couponStr.append(bean.getSn());
                if (k != selectCoupons.size() - 1)
                    couponStr.append(",");
                k++;
            }
        }
        String memberNo = txt_memberNo.getText().toString();
        String point = edt_deduction_amt.getText().toString().equals("") ? "0" : edt_deduction_amt.getText().toString();
        int sid = SharePreferenceHelper.getInstance(MemberActivity.this).getInt("SID");
        int tradeMoney = outputAmount;
        StringBuffer sb = new StringBuffer();
        sb.append("");
        if(couponStr.length() > 0)
            sb.append(couponStr.toString());
        sb.append(memberNo);
        sb.append(memberName);
        if(password != null && !"".equals(password))
            try {
                sb.append(Base64.encryptBASE64(password.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        sb.append(point);
        sb.append(sid);
        sb.append(tradeMoney);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        BaseReqBean<SaleReqSubBean> baseReqBean = new BaseReqBean<SaleReqSubBean>();
        baseReqBean.setCmd("tranMoney");
        SaleReqSubBean saleReqSubBean = new SaleReqSubBean();
        saleReqSubBean.setCouponSn(couponStr.toString());
        saleReqSubBean.setMemberCardNo(memberNo);
        saleReqSubBean.setMemberName(memberName);
        if(password != null && !"".equals(password)) {
            try {
                saleReqSubBean.setPassword(Base64.encryptBASE64(password.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            saleReqSubBean.setPassword("");
        }
        saleReqSubBean.setPoint(Integer.valueOf(point));
        saleReqSubBean.setSid(sid);
        saleReqSubBean.setTradeMoney(tradeMoney);
        saleReqSubBean.setVerify(verify);
        baseReqBean.setParams(saleReqSubBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.saleMoneyCalculate(getBody(postInfoStr)), this);
    }

    @Override
    public void onReqSuccess(SaleResponse result) {
        if(result != null && result.code.equals(Constant.SUCCESS_CODE)) {
            Intent intent = new Intent(this, GatheringActivity.class);
            Bundle bd = new Bundle();
            bd.putParcelable("SaleRespBean", result.result);
            bd.putBoolean("isMember", true);
            bd.putBoolean("mianfei", mianfei);
            bd.putString("password", password);
            bd.putString("memberCardNo", memberRespBean.memberCardNo);//会员卡号
            bd.putInt("point", edt_deduction_amt.getText().toString().equals("") ? 0 : Integer.valueOf(edt_deduction_amt.getText().toString()));//抵扣积分数

            bd.putInt("jifenPoint", jifen);//抵扣金额(分)
            bd.putInt("youhuiquanPoint", youhuiquan);//优惠券抵扣金额(分)
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
        if(SELECT == requestCode) {
            if(resultCode == RESULT_OK) {
                if(data.getExtras() == null)
                    return;
                Bundle bundle = data.getExtras();
                selectCoupons = bundle.getParcelableArrayList("selectCoupons");
                int allAmt = 0;
                for(CouponBean bean : selectCoupons) {
                    allAmt+=bean.getMoney();
                }
                String styledText1 = "当前使用优惠券<font color='red'>"+selectCoupons.size()+"</font>张,优惠金额<font color='red'>" +AmountUtil.divide((double)(allAmt), 100d,2)+"</font>元";
                txt_used_coupons.setText(Html.fromHtml(styledText1), TextView.BufferType.SPANNABLE);
//                txt_used_coupons.setText("当前使用了" + selectCoupons.size() + "张优惠券, ");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    int jifen;//抵扣金额(分)
    int youhuiquan;//优惠券抵扣金额(分)
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_coupons:
                    Intent intent1 = new Intent(MemberActivity.this, CouponActivity.class);
                    Bundle bd1 = new Bundle();
                    bd1.putParcelableArrayList("coupons", coupons);
                    bd1.putParcelableArrayList("selectCoupons", selectCoupons);
                    intent1.putExtras(bd1);
                    startActivityForResult(intent1, SELECT);
                    break;
                case R.id.btn_next:
                    mianfei = false;
                    if(!edt_deduction_amt.getText().toString().equals("")) {
                        if(Integer.valueOf(edt_deduction_amt.getText().toString()) < memberRespBean.frequency_min) {
                            MyToast.showText("最低积分不能少于" + memberRespBean.frequency_min);
                            return;
                        }
                        if(Integer.valueOf(edt_deduction_amt.getText().toString()) > memberRespBean.pointUseMax) {
                            MyToast.showText("抵扣积分超限");
                            return;
                        }
                    }
                    jifen = edt_deduction_amt.getText().toString().equals("") ? 0 : Integer.valueOf(edt_deduction_amt.getText().toString()) * 100 / memberRespBean.pointChangeRate;//积分抵扣金额
                    youhuiquan = 0;//优惠券抵扣金额
                    for(CouponBean bean : selectCoupons) {
                        youhuiquan+=bean.getMoney();
                    }
                    if((youhuiquan+jifen) >= outputAmount) {
                        youhuiquan = outputAmount - jifen;
                        StringBuffer couponStr = new StringBuffer();
                        if(selectCoupons != null && selectCoupons.size() > 0) {
                            int k = 0;
                            for (CouponBean bean : selectCoupons) {
                                couponStr.append(bean.getSn());
                                if (k != selectCoupons.size() - 1)
                                    couponStr.append(",");
                                k++;
                            }
                        }
//                        final Intent intent = new Intent(MemberActivity.this, GatheringActivity.class);
//                        final Bundle bd = new Bundle();
//                        bd.putBoolean("isMember", false);
//                        bd.putString("memberCardNo", memberRespBean.memberCardNo);//会员卡号
//                        bd.putBoolean("mianfei", true);
//                        bd.putInt("outputAmount", outputAmount);//分
//                        bd.putInt("point", edt_deduction_amt.getText().toString().equals("") ? 0 : Integer.valueOf(edt_deduction_amt.getText().toString()));//抵扣积分数
//                        bd.putInt("jifenPoint", jifen);//抵扣金额(分)
//                        bd.putInt("youhuiquanPoint", youhuiquan);//优惠券抵扣金额(分)
//                        bd.putInt("jifenPoint", jifen);
//                        bd.putString("couponStr", couponStr.toString());
                        mianfei = true;
                        if (!memberRespBean.freePassword) {
                            new InputMemberDialog(MemberActivity.this, new InputMemberDialog.OnInputMemberDialogListener() {
                                @Override
                                public void reqMemberInfo(String member, String password) {
                                    calculate(member, password);
//                                    bd.putString("password", password);
//                                    intent.putExtras(bd);
//                                    startActivity(intent);
                                }
                            }, false, true).show();
                        } else {
                            calculate(txt_member_name.getText().toString(), "");
//                            intent.putExtras(bd);
//                            startActivity(intent);
                        }
                    } else {
//                        if (memberRespBean.memberName == null || "".equals(memberRespBean.memberName)) {//输入用户名
//                            new InputMemberDialog(MemberActivity.this, new InputMemberDialog.OnInputMemberDialogListener() {
//                                @Override
//                                public void reqMemberInfo(String member, String password) {
//                                    calculate(member, password);
//                                }
//                            }, true, !memberRespBean.freePassword).show();
//                        } else {
                            if (!memberRespBean.freePassword) {
                                boolean isInputPassword = true;
                                if (edt_deduction_amt.getText().toString().equals("") || Integer.valueOf(edt_deduction_amt.getText().toString()) == 0) {//积分和优惠券都没选择
                                    if (selectCoupons == null || selectCoupons.size() == 0) {
                                        isInputPassword = false;
                                    }
                                }
                                if (isInputPassword) {
                                    new InputMemberDialog(MemberActivity.this, new InputMemberDialog.OnInputMemberDialogListener() {
                                        @Override
                                        public void reqMemberInfo(String member, String password) {
                                            calculate(txt_member_name.getText().toString(), password);
                                        }
                                    }, false, true).show();
                                } else {
                                    calculate(txt_member_name.getText().toString(), "");
                                }
                            } else {
                                calculate(txt_member_name.getText().toString(), "");
                            }
//                        }
                    }
                    break;
                case R.id.btn_not_use:
                    mianfei = false;
                    Intent intent = new Intent(MemberActivity.this, GatheringActivity.class);
                    Bundle bd = new Bundle();
                    bd.putInt("outputAmount", outputAmount);
                    bd.putString("memberCardNo", memberRespBean.memberCardNo);
                    bd.putBoolean("isMember", false);//是否使用会员优惠政策
                    intent.putExtras(bd);
                    startActivity(intent);
                    break;
                case R.id.lv_left:
                    finish();
                    break;

            }
        }
    };
}
