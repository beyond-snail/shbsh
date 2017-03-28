package com.landicorp.yinshang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.RequestManager;
import com.landicorp.yinshang.data.model.BaseReqBean;
import com.landicorp.yinshang.data.model.ReprintReqSubBean;
import com.landicorp.yinshang.data.model.SaleRespBean;
import com.landicorp.yinshang.data.model.TransactionRespBean;
import com.landicorp.yinshang.data.response.TransactionResponse;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.LoginRespBean;
import com.landicorp.yinshang.db.LoginRespBeanDao;
import com.landicorp.yinshang.db.TransactionReqSubBean;
import com.landicorp.yinshang.db.TransactionReqSubBeanDao;
import com.landicorp.yinshang.utils.AmountUtil;
import com.landicorp.yinshang.utils.Base64;
import com.landicorp.yinshang.utils.BillPrintUtils;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.view.InputAmt;
import com.landicorp.yinshang.view.InputAmt1;
import com.ums.AppHelper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by u on 2017/1/9. 现金界面
 */
public class CashAccountActivity extends BaseActivity {

    private LinearLayout lv_left;
    private LinearLayout layout_xianjin;
    private LinearLayout layout_finish;
    private InputAmt1 inputAmt;
    private EditText editText_amount;// 元
    private EditText editText_amount00;// 角和分
    private LinearLayout linearLayout;
    private Button btnConfirm;
    private TextView textView;
    private String hint = "请输入金额";
    private int outputAmount = 0;//分 （单位）
    private TextView otherTextView;
    private TextView txt_zhaoling;
    private TextView txt_yingfu;

    private TextView txt_sale_total;
    private TextView txt_sale;
    private TextView txt_integral;
    private TextView txt_coupon_deduction;
    private Button btn_reprint;
    private Button btn_success;
    private int shouldOutputAmount;
    private SaleRespBean saleRespBean;
    private String memberCardNo;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    private String password;
    private int point;

    double dingdanMoney = 0.0;
    double shishouMoney = 0.0;
    double jifenMoney = 0.0;
    double youhuiMoney = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_account);
        Bundle bd = getIntent().getExtras();
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        layout_xianjin = (LinearLayout) findViewById(R.id.layout_xianjin);
        layout_finish = (LinearLayout) findViewById(R.id.layout_finish);
        txt_sale_total = (TextView) findViewById(R.id.txt_sale_total);
        txt_sale = (TextView) findViewById(R.id.txt_sale);
        txt_integral = (TextView) findViewById(R.id.txt_integral);
        txt_coupon_deduction = (TextView) findViewById(R.id.txt_coupon_deduction);
        txt_zhaoling = (TextView) findViewById(R.id.txt_zhaoling);
        txt_yingfu = (TextView) findViewById(R.id.txt_yingfu);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btn_reprint = (Button) findViewById(R.id.btn_reprint);
        btn_success = (Button) findViewById(R.id.btn_success);
        inputAmt = (InputAmt1) findViewById(R.id.input_amt);
        editText_amount = (EditText) findViewById(R.id.textView_amount);
        btn_success.setOnClickListener(clickListener);
        btn_reprint.setOnClickListener(clickListener);
        if(bd != null) {
            saleRespBean = bd.getParcelable("SaleRespBean");
            memberCardNo = bd.getString("memberCardNo");
            shouldOutputAmount = bd.getInt("outputAmount");
            password = bd.getString("password");
            point = bd.getInt("point");
            if (saleRespBean != null) {
                shouldOutputAmount = saleRespBean.realMoney;
            } else {
                shouldOutputAmount = bd.getInt("outputAmount");
            }
            txt_yingfu.setText("¥ " + AmountUtil.divide((double) (shouldOutputAmount), (double) (100), 2) + "");
        }
        inputAmt.init(editText_amount, txt_zhaoling, shouldOutputAmount);
        inputAmt.setOnPayClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                outputAmount = inputAmt.getAmount();
                if (outputAmount == 0) {
                    MyToast.showText(hint);
                    return;
                }
                if (outputAmount > 1000000000) {
                    MyToast.showText("金额过大，超过系统处理范围");
                    return;
                }
                if(outputAmount < shouldOutputAmount) {
                    MyToast.showText("顾客付款不够");
                    return;
                }
                uplaodFlowing();
            }
        });

        lv_left.setOnClickListener(clickListener);
//        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFv8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAydi1zU3NQWjFhclAxRzNOVGhvMUgAAgQDd65YAwSAOgkA";
        if (saleRespBean != null) {
            dingdanMoney = AmountUtil.divide((double) (saleRespBean.tradeMoney), (double) 100, 2);
            shishouMoney = AmountUtil.divide((double) (saleRespBean.realMoney), (double) 100, 2);
            jifenMoney = AmountUtil.divide((double) (saleRespBean.pointCoverMoney), (double) 100, 2);
            youhuiMoney = AmountUtil.divide((double) (saleRespBean.couponCoverMoney), (double) 100, 2);

            txt_sale_total.setText("¥ " + AmountUtil.divide((double)(saleRespBean.tradeMoney), (double)100,2));
            txt_sale.setText("¥ " + AmountUtil.divide((double)(saleRespBean.realMoney), (double)100,2));
            txt_integral.setText("¥ " + AmountUtil.divide((double)(saleRespBean.pointCoverMoney), (double)100,2));
            txt_coupon_deduction.setText("¥ " + AmountUtil.divide((double)(saleRespBean.couponCoverMoney), (double)100,2));
        } else {
            dingdanMoney = AmountUtil.divide((double) (shouldOutputAmount), (double) 100, 2);
            shishouMoney = AmountUtil.divide((double) (shouldOutputAmount), (double) 100, 2);

            txt_sale_total.setText("¥ " + AmountUtil.divide((double)(shouldOutputAmount), (double)100,2));
            txt_sale.setText("¥ " + AmountUtil.divide((double) (shouldOutputAmount), (double) 100, 2));
            txt_integral.setText("¥ 0.0");
            txt_coupon_deduction.setText("¥ 0.0");
        }
    }

    String currentClientOrderNo = "";
    private void uplaodFlowing() {
        String activateCode = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        String authCode = "";
        int bankAmount = 0;
        String cardNo = "";
        if(memberCardNo != null)
            cardNo = memberCardNo;
        int cash = shouldOutputAmount;
        int cot = 1+(int)(Math.random()*999);
        String clientOrderNo = "2" + "7" + sf.format(new Date())+ cot + ((BaseApplication)getApplication()).getSystemInfo().getSN();
        currentClientOrderNo = clientOrderNo;
        int couponCoverAmount = 0;
        if(saleRespBean != null) {
            couponCoverAmount = saleRespBean.couponCoverMoney;
        }
        String couponSns  = "";
        if(saleRespBean != null) {
            couponSns = saleRespBean.couponSns;
        }
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        LoginRespBean bean = null;
        if(list != null && list.size() > 0) {
            bean = list.get(0);
        }
        String merchantNo = bean.merchantNo;
        String password = "";
        if(this.password != null) {
            try {
                password = Base64.encryptBASE64(this.password.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int payType = 7;
        int pointAmount = point;
        int pointCoverAmount = 0;
        if(saleRespBean != null) {
            pointCoverAmount = saleRespBean.pointCoverMoney;
        }
        String serialNum = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        int sid = SharePreferenceHelper.getInstance(CashAccountActivity.this).getInt("SID");
        long t = new Date().getTime() / 1000;
        String transNo = "";
        StringBuffer sb = new StringBuffer();
        if(activateCode != null)
            sb.append(activateCode);
        if(authCode != null)
            sb.append(authCode);
        sb.append(bankAmount);
        if(cardNo != null)
            sb.append(cardNo);
        sb.append(cash);
        if(clientOrderNo != null)
            sb.append(clientOrderNo);
        sb.append(couponCoverAmount);
        if(couponSns != null)
            sb.append(couponSns);
        if(merchantNo != null)
            sb.append(merchantNo);
        if(password != null)
            sb.append(password);
        sb.append(payType);
        sb.append(pointAmount);
        sb.append(pointCoverAmount);
        sb.append(serialNum);
        sb.append(sid);
        sb.append(t);
        if(transNo != null)
            sb.append(transNo);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        BaseReqBean<TransactionReqSubBean> baseReqBean = new BaseReqBean<TransactionReqSubBean>();
        baseReqBean.setCmd("tranUploadPos");
        final TransactionReqSubBean subBean = new TransactionReqSubBean();
        subBean.setActivateCode(activateCode);
        subBean.setAuthCode(authCode);
        subBean.setBankAmount(bankAmount);
        subBean.setCardNo(cardNo);
        subBean.setCash(cash);
        subBean.setClientOrderNo(clientOrderNo);
        subBean.setCouponCoverAmount(couponCoverAmount);
        subBean.setCouponSns(couponSns);
        subBean.setMerchantNo(merchantNo);
        subBean.setPassword(password);
        subBean.setPayType(payType);
        subBean.setPointAmount(pointAmount);
        subBean.setPointCoverAmount(pointCoverAmount);
        subBean.setSerialNum(serialNum);
        subBean.setSid(sid);
        subBean.setT(t);
        subBean.setTransNo(transNo);
        subBean.setVerify(verify);
        baseReqBean.setParams(subBean);
        String postInfoStr = gson.toJson(baseReqBean);
        if(saleRespBean != null) {
            subBean.setOrderAmount(String.valueOf(saleRespBean.tradeMoney));
        } else {
            subBean.setOrderAmount(String.valueOf(shouldOutputAmount));
        }
        TransactionReqSubBeanDao transactionReqSubBeanDao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
        transactionReqSubBeanDao.insert(subBean);
        RequestManager.getInstance(this).postSpecial(this, apiService.uploadTransaction(getBody(postInfoStr)), new ReqCallBack<TransactionResponse>() {

            @Override
            public void onReqSuccess(TransactionResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                    subBean.setIsUploadSuccess("1");
                    subBean.setScanUrl(result.result.point_url);
                    dao.insertOrReplace(subBean);
//                    printInfo(subBean.getClientOrderNo(), result.result.point_url, subBean.getPayType());
                    printInfo(subBean.getClientOrderNo(), subBean.getPayType(), result.result);
                } else {
//                    printInfo(subBean.getClientOrderNo(), null, subBean.getPayType());
                    printInfo(subBean.getClientOrderNo(),  subBean.getPayType(), null);
                }
                MyToast.showText("交易完成");
            }

            @Override
            public void onReqFailed(String errorMsg) {
                MyToast.showText("交易完成");
                printInfo(subBean.getClientOrderNo(),  subBean.getPayType(), null);
            }
        });

    }

    /**
     * //重新打印
     * @param
     */
    private void reprint(final String clientOrderNo, final int payType) {
        int sid = SharePreferenceHelper.getInstance(CashAccountActivity.this).getInt("SID");
        StringBuffer sb = new StringBuffer();
        sb.append(clientOrderNo);
        sb.append(sid);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        BaseReqBean<ReprintReqSubBean> baseReqBean = new BaseReqBean<ReprintReqSubBean>();
        baseReqBean.setCmd("getPrintData");
        ReprintReqSubBean subBean = new ReprintReqSubBean();
        subBean.setClientOrderNo(clientOrderNo);
        subBean.setSid(sid);
        subBean.setVerify(verify);
        baseReqBean.setParams(subBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.reprint(getBody(postInfoStr)), new ReqCallBack<TransactionResponse>() {

            @Override
            public void onReqSuccess(TransactionResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    printInfo(clientOrderNo, payType, result.result);
                } else {
                    MyToast.showText(result.msg);
                    printInfo(clientOrderNo, payType, null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                printInfo(clientOrderNo, payType, null);
            }
        });
    }

    private void printInfo(final String clientOrderNo, final int payType, final TransactionRespBean result) {

        if(result != null && result.point_url != null) {
            Glide.with(this).load(result.point_url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    final Bitmap resource1 = resource;
                    if(result.coupon != null && !result.coupon.equals("")) {
                        Glide.with(CashAccountActivity.this).load(result.coupon).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                BillPrintUtils.printBill(CashAccountActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resource1, payType,
                                         resource, result.point, result.title_url, result.money);
                                layout_xianjin.setVisibility(View.GONE);
                                layout_finish.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                BillPrintUtils.printBill(CashAccountActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resource1, payType);
                                layout_xianjin.setVisibility(View.GONE);
                                layout_finish.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        BillPrintUtils.printBill(CashAccountActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resource, payType);
                        layout_xianjin.setVisibility(View.GONE);
                        layout_finish.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    BillPrintUtils.printBill(CashAccountActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, null, payType);
                    layout_xianjin.setVisibility(View.GONE);
                    layout_finish.setVisibility(View.VISIBLE);
                }
            }); //方法中设置asBitmap可以设置回调类型
        } else {
            BillPrintUtils.printBill(CashAccountActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, 0.0, clientOrderNo, null, payType);
            layout_xianjin.setVisibility(View.GONE);
            layout_finish.setVisibility(View.VISIBLE);
        }
    }

    private void transFinish() {
        if (MemberActivity.instance != null)
            MemberActivity.instance.finish();
        if (GatheringActivity.instance != null)
            GatheringActivity.instance.finish();
        CashAccountActivity.this.finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(AppHelper.PRINT_REQUEST_CODE == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lv_left:
                    finish();
                    break;
                case R.id.btn_success:
                    transFinish();
                    break;
                case R.id.btn_reprint:
                    reprint(currentClientOrderNo, 7);
                    break;

            }
        }
    };
}
