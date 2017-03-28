package com.landicorp.yinshang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.landicorp.yinshang.data.model.WalletReqSubBean;
import com.landicorp.yinshang.data.model.WalletRespBean;
import com.landicorp.yinshang.data.response.TransactionResponse;
import com.landicorp.yinshang.data.response.WalletResponse;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.LoginRespBean;
import com.landicorp.yinshang.db.LoginRespBeanDao;
import com.landicorp.yinshang.db.PayInfoBean;
import com.landicorp.yinshang.db.PayInfoBeanDao;
import com.landicorp.yinshang.db.TransactionReqSubBean;
import com.landicorp.yinshang.db.TransactionReqSubBeanDao;
import com.landicorp.yinshang.utils.AmountUtil;
import com.landicorp.yinshang.utils.Base64;
import com.landicorp.yinshang.utils.BillPrintUtils;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.zxing.android.CaptureActivity;
import com.ums.AppHelper;

import org.greenrobot.greendao.query.Query;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by u on 2017/1/9. 收款界面
 */
public class GatheringActivity extends BaseActivity {

    private LinearLayout lv_left;
    private LinearLayout layout_paytype;
    private LinearLayout layout_trans_success;
    private TextView txt_sale_total;//消费总额
    private TextView txt_sale;//支付金额
    private TextView txt_integral;//积分抵扣
    private TextView txt_coupon_deduction;//优惠券抵扣
    private Button btn_swiping_card;//刷卡
    private Button btn_cash_account;//现金
    private Button btn_zfb;//支付宝
    private Button btn_weixin;//微信
    private Button btn_qianbao;//钱包
    private Button btn_reprint;//重新打印
    private Button btn_success;//交易完成
    private Button btn_confirm_shouyin;//确认收银
    private SaleRespBean saleRespBean;//上送金额计算返回的对象
    private String memberCardNo;
    private int outputAmount;
    private String password;
    private int point;//抵扣积分数
    private int jifenPoint;//积分抵扣金额(只有 mianfei==true时 会有值)
    private int youhuiquanPoint;//优惠券抵扣金额只有 mianfei==true时 会有值)
    private String couponStr;//优惠券序列号组只有 mianfei==true时 会有值)
    private boolean mianfei;//如果是true 表明用的优惠券和积分已经足够支付商品
    private PayInfoBean payInfoBean;//刷卡 或者pos通支付返回的对象
    private int payType;//1:支付宝 3:微信 4:百付宝 5:京东 6;刷卡 7;现金，10：刷卡撤销；11：微信撤销； 12：支付撤销；13：钱包

    private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private WalletRespBean walletRespBean = null;
    private boolean isReprint = false;

    public static GatheringActivity instance = null;

    double dingdanMoney = 0.0;
    double shishouMoney = 0.0;
    double jifenMoney = 0.0;
    double youhuiMoney = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering);
        instance = this;
        init();
    }

    private void init() {
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        layout_paytype = (LinearLayout) findViewById(R.id.layout_paytype);
        layout_trans_success = (LinearLayout) findViewById(R.id.layout_trans_success);
        txt_sale_total = (TextView) findViewById(R.id.txt_sale_total);
        txt_sale = (TextView) findViewById(R.id.txt_sale);
        txt_integral = (TextView) findViewById(R.id.txt_integral);
        txt_coupon_deduction = (TextView) findViewById(R.id.txt_coupon_deduction);
        btn_swiping_card = (Button) findViewById(R.id.btn_swiping_card);
        btn_cash_account = (Button) findViewById(R.id.btn_cash_account);
        btn_reprint = (Button) findViewById(R.id.btn_reprint);
        btn_success = (Button) findViewById(R.id.btn_success);
        btn_zfb = (Button) findViewById(R.id.btn_zfb);
        btn_weixin = (Button) findViewById(R.id.btn_weixin);
        btn_qianbao = (Button) findViewById(R.id.btn_qianbao);
        btn_confirm_shouyin = (Button) findViewById(R.id.btn_confirm_shouyin);
        lv_left.setOnClickListener(clickListener);
        btn_swiping_card.setOnClickListener(clickListener);
        btn_cash_account.setOnClickListener(clickListener);
        btn_zfb.setOnClickListener(clickListener);
        btn_weixin.setOnClickListener(clickListener);
//        btn_qianbao.setOnClickListener(clickListener);
        btn_success.setOnClickListener(clickListener);
        btn_reprint.setOnClickListener(clickListener);
        btn_confirm_shouyin.setOnClickListener(clickListener);
        Bundle bd = getIntent().getExtras();
        if(bd != null) {
            boolean isMember = bd.getBoolean("isMember");//是否使用了会员计算，
            password = bd.getString("password");
            point = bd.getInt("point");
            mianfei = bd.getBoolean("mianfei");
            jifenPoint = bd.getInt("jifenPoint");
            youhuiquanPoint = bd.getInt("youhuiquanPoint");
            memberCardNo = bd.getString("memberCardNo");
            if(isMember) {
                saleRespBean = bd.getParcelable("SaleRespBean");
                outputAmount = saleRespBean.realMoney;
                txt_sale_total.setText("¥ " + AmountUtil.divide((double)(saleRespBean.tradeMoney), (double)100,2));
                txt_sale.setText("¥ " + AmountUtil.divide((double)(saleRespBean.realMoney), (double)100,2));
                txt_integral.setText("¥ " + AmountUtil.divide((double)(saleRespBean.pointCoverMoney), (double)100,2));
                txt_coupon_deduction.setText("¥ " + AmountUtil.divide((double)(saleRespBean.couponCoverMoney), (double)100,2));
                jifenPoint = saleRespBean.pointCoverMoney;
                youhuiquanPoint = saleRespBean.couponCoverMoney;
                couponStr = saleRespBean.couponSns;
            } else {
                outputAmount = bd.getInt("outputAmount");
                txt_sale_total.setText("¥ " + AmountUtil.divide((double)(outputAmount), (double)100,2));
                if(!mianfei) {
                    txt_sale.setText("¥ " + AmountUtil.divide((double) (outputAmount), (double) 100, 2));
                    txt_integral.setText("¥ 0.0");
                    txt_coupon_deduction.setText("¥ 0.0");
                } else {
                    couponStr = bd.getString("couponStr");
                    txt_sale.setText("¥ 0.0");
                    txt_integral.setText("¥ " + AmountUtil.divide((double) jifenPoint, (double) 100, 2));
                    txt_coupon_deduction.setText("¥ " + AmountUtil.divide((double) youhuiquanPoint, (double) 100, 2));
                }
            }

        }
        if(mianfei) {
            layout_paytype.setVisibility(View.GONE);
            btn_confirm_shouyin.setVisibility(View.VISIBLE);
        } else {
            layout_paytype.setVisibility(View.VISIBLE);
            btn_confirm_shouyin.setVisibility(View.GONE);
        }
        if(saleRespBean != null) {
            dingdanMoney = AmountUtil.divide((double) (saleRespBean.tradeMoney), (double) 100, 2);
            shishouMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
            jifenMoney = AmountUtil.divide((double) (saleRespBean.pointCoverMoney), (double) 100, 2);
            youhuiMoney = AmountUtil.divide((double) (saleRespBean.couponCoverMoney), (double) 100, 2);
        } else {
            dingdanMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
            shishouMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
            if(mianfei)
                shishouMoney = 0.0;
        }
    }

    String currentClientOrderNo = null;//为了重新打印和打印
    /**
     *  //流水上传 仅限银行卡和pos通支付
     */
    private void uplaodTransactionFlowing() {
        String activateCode = "";
        if(payType == 1 || payType == 3) {
            activateCode = payInfoBean.getTerminalNo();
        } else if(payType == 6 || payType == 7) {
            activateCode = payInfoBean.getTerminalNo();
        }else if(payType == 13) {
            activateCode = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        }
        String authCode = "";
        if(payType == 1 || payType == 3) {
            authCode = payInfoBean.getTraceNo();
        } else if(payType == 6) {
            authCode = payInfoBean.getTraceNo();
        } else if(payType == 7) {

        }
        int bankAmount = 0;
        if(payType == 1 || payType == 3 || payType == 6) {
            if(saleRespBean != null)
              bankAmount = saleRespBean.realMoney;
            else
              bankAmount= outputAmount;
        } else if(payType == 7) {

        }
        String cardNo = "";
        if(memberCardNo != null)
            cardNo = memberCardNo;
        int cash = 0;
        if(payType == 7) {//现金的数据
            cash = 0;
        }
        String clientOrderNo = "";
        if(payType == 1 || payType == 3) {
            //TODO
            String time = payInfoBean.getDate()+payInfoBean.getTime();
            int cot = 1+(int)(Math.random()*999);
            clientOrderNo = "2" + payType + time.replace("/","").replace(":","") + cot + ((BaseApplication)getApplication()).getSystemInfo().getSN() ;
        } else if(payType == 6 || payType == 7) {
            String time = payInfoBean.getDate()+payInfoBean.getTime();
            int cot = 1+(int)(Math.random()*999);
            clientOrderNo = "2" + payType + time.replace("/","").replace(":","") + cot +payInfoBean.getMerchantNo();
        }
        currentClientOrderNo=clientOrderNo;
        int couponCoverAmount = 0;
        if(saleRespBean != null) {
            couponCoverAmount = saleRespBean.couponCoverMoney;
        }
        String couponSns  = "";
        if(saleRespBean != null) {
            couponSns = saleRespBean.couponSns;
        }
        String merchantNo = "";
        if(payType == 1 || payType == 3) {
            merchantNo = payInfoBean.getMerchantNo();
        } else if(payType == 6 || payType == 7) {
            merchantNo = payInfoBean.getMerchantNo();
        }
        String password = "";
        if(this.password != null) {
            try {
                password = Base64.encryptBASE64(this.password.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final int payType = this.payType;
        int pointAmount = point;
        int pointCoverAmount = 0;
        if(saleRespBean != null) {
            pointCoverAmount = saleRespBean.pointCoverMoney;
        }
        String serialNum = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        int sid = SharePreferenceHelper.getInstance(GatheringActivity.this).getInt("SID");
        long t = 0;
        String time = (payInfoBean.getDate()+payInfoBean.getTime()).replace("/","").replace(":","");
        try {
            t = sf.parse(time).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String transNo = "";
        if(payType == 1 || payType == 3 || payType == 6 || payType == 7) {
            transNo = payInfoBean.getTraceNo();
        }
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
            subBean.setOrderAmount(String.valueOf(outputAmount));
        }
        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
        dao.insert(subBean);
        RequestManager.getInstance(this).postSpecial(this, apiService.uploadTransaction(getBody(postInfoStr)), new ReqCallBack<TransactionResponse>() {

            @Override
            public void onReqSuccess(TransactionResponse result) {
//                MyToast.showText(result.code+ "-----------" );
                if(GatheringActivity.this.payType == 1 || GatheringActivity.this.payType == 3 || GatheringActivity.this.payType == 6) {
                    if (result.code.equals(Constant.SUCCESS_CODE)) {
                        MyToast.showText("流水上传成功");
                        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                        subBean.setIsUploadSuccess("1");
                        dao.insertOrReplace(subBean);
//                        Query query = dao.queryBuilder().where(
//                                TransactionReqSubBeanDao.Properties.TransNo.eq(subBean.getTransNo()))
//                                .build();
//                        List<TransactionReqSubBean> list = query.list();
//                        if(list != null && list.size() > 0) {
//                            list.get(0).setIsUploadSuccess(true);
//                            dao.insertOrReplace(list.get(0));
//                        }
                        printOnlyScan(currentClientOrderNo, payType, result.result);
                    } else {
                        MyToast.showText(result.msg);
                    }
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                MyToast.showText("流水上传失败");
            }
        });
    }

    TransactionReqSubBean packetSubBean;//为了重新打印创建的对象
    TransactionRespBean packetResp;//为了重新打印创建的对象（主要是二维码的url）
    /**
     *  //钱包流水上传
     */
    private void uplaodWalletFlowing() {
        String activateCode = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        String authCode = walletRespBean.SystemOrderNo;
        int bankAmount = 0;
        if(saleRespBean != null)
            bankAmount = saleRespBean.realMoney;
        else
            bankAmount= outputAmount;
        String cardNo = "";
        if(memberCardNo != null)
            cardNo = memberCardNo;
        int cash = 0;//
        int cot = 1+(int)(Math.random()*999);
//        String clientOrderNo = "2" + payType + sf.format(new Date())+ cot + ((BaseApplication)getApplication()).getSystemInfo().getSN() ;
        String clientOrderNo = orgOrderNum;
        int couponCoverAmount = 0;
        if(saleRespBean != null) {
            couponCoverAmount = saleRespBean.couponCoverMoney;
        }
        String couponSns  = "";
        if(saleRespBean != null) {
            couponSns = saleRespBean.couponSns;
        }
        String merchantNo = walletRespBean.groupId;
        String password = "";
        if(this.password != null) {
            try {
                password = Base64.encryptBASE64(this.password.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int payType = this.payType;
        int pointAmount = point;
        int pointCoverAmount = 0;
        if(saleRespBean != null) {
            pointCoverAmount = saleRespBean.pointCoverMoney;
        }
        String serialNum = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        int sid = SharePreferenceHelper.getInstance(GatheringActivity.this).getInt("SID");
        long t = new Date().getTime() / 1000;
        String transNo = walletRespBean.SystemOrderNo;
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
        packetSubBean = subBean;
        if(saleRespBean != null) {
            subBean.setOrderAmount(String.valueOf(saleRespBean.tradeMoney));
        } else {
            subBean.setOrderAmount(String.valueOf(outputAmount));
        }
        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
        dao.insert(subBean);
        RequestManager.getInstance(this).postSpecial(this, apiService.uploadTransaction(getBody(postInfoStr)), new ReqCallBack<TransactionResponse>() {

            @Override
            public void onReqSuccess(TransactionResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    packetResp = result.result;
                    MyToast.showText("流水上传成功");
//                    layout_paytype.setVisibility(View.GONE);
//                    btn_confirm_shouyin.setVisibility(View.GONE);
//                    layout_trans_success.setVisibility(View.VISIBLE);
                    TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                    subBean.setIsUploadSuccess("1");
                    subBean.setScanUrl(result.result.point_url);
                    dao.insertOrReplace(subBean);
                    printInfo(subBean.getClientOrderNo(), subBean.getPayType(), result.result);
                } else {
                    MyToast.showText(result.msg);
//                    printInfo(subBean.getClientOrderNo(), null, subBean.getPayType());
                    printInfo(subBean.getClientOrderNo(), subBean.getPayType(), null);
                }
                btn_confirm_shouyin.setVisibility(View.GONE);
                layout_paytype.setVisibility(View.GONE);
                layout_trans_success.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                MyToast.showText("流水上传失败");
//                printInfo(subBean.getClientOrderNo(), null, subBean.getPayType());
                printInfo(subBean.getClientOrderNo(), subBean.getPayType(), null);
                btn_confirm_shouyin.setVisibility(View.GONE);
                layout_paytype.setVisibility(View.GONE);
                layout_trans_success.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 现金流水上传
     */
    private void uplaodCashAccountFlowing() {
        String activateCode = ((BaseApplication) getApplication()).getSystemInfo().getSN();
        String authCode = "";
        int bankAmount = 0;
        String cardNo = "";
        if(memberCardNo != null)
            cardNo = memberCardNo;
        int cash = 0;
        int cot = 1 + (int) (Math.random() * 999);
        String clientOrderNo = "2" + "7" + sf.format(new Date()) + cot + ((BaseApplication) getApplication()).getSystemInfo().getSN();
        int couponCoverAmount = youhuiquanPoint;
        String couponSns = this.couponStr != null ? this.couponStr : "";
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        LoginRespBean bean = null;
        if (list != null && list.size() > 0) {
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
        int pointCoverAmount = jifenPoint;
        String serialNum = ((BaseApplication) getApplication()).getSystemInfo().getSN();
        int sid = SharePreferenceHelper.getInstance(GatheringActivity.this).getInt("SID");
        long t = new Date().getTime() / 1000;
        String transNo = "";
        StringBuffer sb = new StringBuffer();
        if (activateCode != null)
            sb.append(activateCode);
        if (authCode != null)
            sb.append(authCode);
        sb.append(bankAmount);
        if (cardNo != null)
            sb.append(cardNo);
        sb.append(cash);
        if (clientOrderNo != null)
            sb.append(clientOrderNo);
        sb.append(couponCoverAmount);
        if (couponSns != null)
            sb.append(couponSns);
        if (merchantNo != null)
            sb.append(merchantNo);
        if (password != null)
            sb.append(password);
        sb.append(payType);
        sb.append(pointAmount);
        sb.append(pointCoverAmount);
        sb.append(serialNum);
        sb.append(sid);
        sb.append(t);
        if (transNo != null)
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
        packetSubBean = subBean;
        if(saleRespBean != null) {
            subBean.setOrderAmount(String.valueOf(saleRespBean.tradeMoney));
        } else {
            subBean.setOrderAmount(String.valueOf(outputAmount));
        }
        TransactionReqSubBeanDao transactionReqSubBeanDao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
        transactionReqSubBeanDao.insert(subBean);
        RequestManager.getInstance(this).postSpecial(this, apiService.uploadTransaction(getBody(postInfoStr)), new ReqCallBack<TransactionResponse>() {

            @Override
            public void onReqSuccess(TransactionResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    packetResp = result.result;
                    MyToast.showText("流水上传成功");
                    TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                    subBean.setIsUploadSuccess("1");
                    subBean.setScanUrl(result.result.point_url);
                    dao.insertOrReplace(subBean);
                    printInfo(subBean.getClientOrderNo(), subBean.getPayType(), result.result);
//                    printInfo(subBean.getClientOrderNo(), result.result.point_url, subBean.getPayType());

                } else {
                    MyToast.showText(result.msg);
                    printInfo(subBean.getClientOrderNo(), subBean.getPayType(), null);
//                    printInfo(subBean.getClientOrderNo(), null, subBean.getPayType());
                }
                btn_confirm_shouyin.setVisibility(View.GONE);
                layout_paytype.setVisibility(View.GONE);
                layout_trans_success.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                MyToast.showText("流水上传失败");
                printInfo(subBean.getClientOrderNo(), subBean.getPayType(), null);
                btn_confirm_shouyin.setVisibility(View.GONE);
                layout_paytype.setVisibility(View.GONE);
                layout_trans_success.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * //钱包支付
     * @param qrCode 二维码
     */
    String orgOrderNum = "";
    private void uploadWalletPay(String qrCode) {
        String merchantId = SharePreferenceHelper.getInstance(GatheringActivity.this).getInt("SID") + "";
        String orderCurrency = "156";

        String time = sf.format(new Date());
        int cot = 1+(int)(Math.random()*999);
        orgOrderNum = "2" + payType + time + cot + ((BaseApplication)getApplication()).getSystemInfo().getSN() ;
        String orgTranDateTime = time;
        int cot5 = 1+(int)(Math.random()*99999);
        String sysTraceNum = time + cot5;
        String terminalNo = ((BaseApplication)getApplication()).getSystemInfo().getSN();
        String tranAmt = "" + outputAmount;
        String tranCode = "9448";
        StringBuffer sb = new StringBuffer();
        sb.append(merchantId);
        sb.append(orderCurrency);
        sb.append(orgOrderNum);
        sb.append(orgTranDateTime);
        sb.append(qrCode);
        sb.append(sysTraceNum);
        sb.append(terminalNo);
        sb.append(tranAmt);
        sb.append(tranCode);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        BaseReqBean<WalletReqSubBean> baseReqBean = new BaseReqBean<WalletReqSubBean>();
        baseReqBean.setCmd("qbPay");
        WalletReqSubBean subBean = new WalletReqSubBean();
        subBean.setMerchantId(merchantId);
        subBean.setOrderCurrency(orderCurrency);
        subBean.setOrgOrderNum(orgOrderNum);
        subBean.setOrgTranDateTime(orgTranDateTime);
        subBean.setQrCode(qrCode);
        subBean.setSysTraceNum(sysTraceNum);
        subBean.setTerminalNo(terminalNo);
        subBean.setTranAmt(tranAmt);
        subBean.setTranCode(tranCode);
        subBean.setVerify(verify);
        baseReqBean.setParams(subBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.uploadWallet(getBody(postInfoStr)), new ReqCallBack<WalletResponse>() {

            @Override
            public void onReqSuccess(WalletResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    WalletResponse rs = (WalletResponse) result;
                    walletRespBean = rs.result;
//                        MyToast.showText("钱包支付成功");

                    uplaodWalletFlowing();
                    btn_confirm_shouyin.setVisibility(View.GONE);
                    layout_paytype.setVisibility(View.GONE);
                    layout_trans_success.setVisibility(View.VISIBLE);
                } else {
                    MyToast.showText(result.msg);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    /**
     * //重新打印
     * @param
     */
    private void reprintScanOnlyReq(final String clientOrderNo, final int payType) {
        int sid = SharePreferenceHelper.getInstance(GatheringActivity.this).getInt("SID");
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
                    printOnlyScan(clientOrderNo, payType, result.result);
                } else {
                    MyToast.showText(result.msg);
                    printOnlyScan(clientOrderNo, payType, null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                printInfo(clientOrderNo, payType, null);
            }
        });
    }

    /**
     * //重新打印
     * @param
     */
    private void reprint(final String clientOrderNo, final int payType) {
        int sid = SharePreferenceHelper.getInstance(GatheringActivity.this).getInt("SID");
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

//    private void printInfo(final String clientOrderNo, String scanCodeUrl, final int payType) {
//
//        Glide.with(this).load(scanCodeUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//
////                double dingdanMoney = 0.0;
////                double shishouMoney = 0.0;
////                double jifenMoney = 0.0;
////                double youhuiMoney = 0.0;
////                if(saleRespBean != null) {
////                    dingdanMoney = AmountUtil.divide((double) (saleRespBean.tradeMoney), (double) 100, 2);
////                    shishouMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
////                    jifenMoney = AmountUtil.divide((double) (saleRespBean.pointCoverMoney), (double) 100, 2);
////                    youhuiMoney = AmountUtil.divide((double) (saleRespBean.couponCoverMoney), (double) 100, 2);
////                } else {
////                    dingdanMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
////                    shishouMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
////                }
//                BillPrintUtils.printBill(GatheringActivity.this,dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, clientOrderNo, resource, payType);
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
////                double dingdanMoney = 0.0;
////                double shishouMoney = 0.0;
////                double jifenMoney = 0.0;
////                double youhuiMoney = 0.0;
////                if(saleRespBean != null) {
////                    dingdanMoney = AmountUtil.divide((double) (saleRespBean.tradeMoney), (double) 100, 2);
////                    shishouMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
////                    jifenMoney = AmountUtil.divide((double) (saleRespBean.pointCoverMoney), (double) 100, 2);
////                    youhuiMoney = AmountUtil.divide((double) (saleRespBean.couponCoverMoney), (double) 100, 2);
////                } else {
////                    dingdanMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
////                    shishouMoney = AmountUtil.divide((double) (outputAmount), (double) 100, 2);
////                }
//                BillPrintUtils.printBill(GatheringActivity.this,dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, clientOrderNo, null, payType);
//            }
//        }); //方法中设置asBitmap可以设置回调类型
//
//
//    }
    private void printOnlyScan(final String clientOrderNo, final int payType, final TransactionRespBean result) {

        if(result != null && result.point_url != null) {
            Glide.with(this).load(result.point_url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    final Bitmap resourceYouhui = resource;
                    if(result.coupon != null && !result.coupon.equals("")) {
                        Glide.with(GatheringActivity.this).load(result.coupon).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                BillPrintUtils.printOnlySacnBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,
                                        AmountUtil.divide((double) result.backAmt, (double) 100, 2), resourceYouhui,  resource, result.point, result.title_url, result.money);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                BillPrintUtils.printOnlySacnBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), resourceYouhui,  null, result.point, result.title_url, result.money);
                            }
                        });
                    } else {
                        BillPrintUtils.printOnlySacnBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), resourceYouhui,  null, result.point, result.title_url, result.money);
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {

                }
            }); //方法中设置asBitmap可以设置回调类型
        } else {
        }
    }

    private void printInfo(final String clientOrderNo, final int payType, final TransactionRespBean result) {

        if(result != null && result.point_url != null) {
            Glide.with(this).load(result.point_url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    final Bitmap resourceYouhui = resource;
                    if(result.coupon != null && !result.coupon.equals("")) {
                        Glide.with(GatheringActivity.this).load(result.coupon).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if(payType == 13) {
                                    BillPrintUtils.printPakectBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, walletRespBean.groupId,
                                            ((BaseApplication)getApplication()).getSystemInfo().getSN(), resourceYouhui, payType,
                                            resource, result.point, result.title_url, result.money);
                                } else {
                                    BillPrintUtils.printBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resourceYouhui, payType,
                                            resource, result.point, result.title_url, result.money);
                                }
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                if(payType == 13) {
                                    BillPrintUtils.printPakectBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, walletRespBean.groupId,
                                            ((BaseApplication)getApplication()).getSystemInfo().getSN(), resourceYouhui, payType,
                                            null, result.point, result.title_url, result.money);
                                } else
                                BillPrintUtils.printBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resourceYouhui, payType);
                            }
                        });
                    } else {
                        if(payType == 13) {
                            BillPrintUtils.printPakectBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,  AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, walletRespBean.groupId,
                                    ((BaseApplication)getApplication()).getSystemInfo().getSN(), resourceYouhui, payType,
                                    null, result.point, result.title_url, result.money);
                        } else
                            BillPrintUtils.printBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, resource, payType);
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    if(payType == 13) {
                        BillPrintUtils.printPakectBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, walletRespBean.groupId,
                                ((BaseApplication)getApplication()).getSystemInfo().getSN(), null, payType,
                                null, result.point, result.title_url, result.money);
                    } else
                        BillPrintUtils.printBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,  AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, null, payType);
                }
            }); //方法中设置asBitmap可以设置回调类型
        } else {
            if(payType == 13) {
                BillPrintUtils.printPakectBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, 0.0, clientOrderNo, walletRespBean.groupId,
                        ((BaseApplication)getApplication()).getSystemInfo().getSN(), null, payType,
                        null, result.point, result.title_url, result.money);
            } else
                BillPrintUtils.printBill(GatheringActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, 0.0, clientOrderNo, null, payType);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(AppHelper.TRANS_REQUEST_CODE == requestCode){
            if (Activity.RESULT_OK == resultCode) {
                if (null != data) {
                    StringBuilder result = new StringBuilder();
                    Map<String,String> map = AppHelper.filterTransResult(data);
                    result.append(AppHelper.TRANS_APP_NAME + ":" +map.get(AppHelper.TRANS_APP_NAME) + "\r\n");
                    result.append(AppHelper.TRANS_BIZ_ID + ":" +map.get(AppHelper.TRANS_BIZ_ID) + "\r\n");
                    result.append(AppHelper.RESULT_CODE + ":" +map.get(AppHelper.RESULT_CODE) + "\r\n");
                    result.append(AppHelper.RESULT_MSG + ":" +map.get(AppHelper.RESULT_MSG) + "\r\n");
                    result.append(AppHelper.TRANS_DATA + ":" +map.get(AppHelper.TRANS_DATA) + "\r\n");
                    if(map.get(AppHelper.RESULT_CODE).equals("0") && !isReprint) {
                        payInfoBean = gson.fromJson(map.get(AppHelper.TRANS_DATA), PayInfoBean.class);
                        if(payInfoBean.getResCode().equals("00")) {//成功
//                            if(payType == 1 || payType == 3 || payType == 6) {
//                                PayInfoBeanDao dao = DBManager.getInstance().getSession().getPayInfoBeanDao();

//                                dao.insert(payInfoBean);//插入数据库
//                            }
                            btn_confirm_shouyin.setVisibility(View.GONE);
                            layout_paytype.setVisibility(View.GONE);
                            layout_trans_success.setVisibility(View.VISIBLE);
                            uplaodTransactionFlowing();
                        }
                        MyToast.showText(payInfoBean.getResDesc());
                    } else if(map.get(AppHelper.RESULT_CODE).equals("0") && isReprint) {
                        reprintScanOnlyReq(currentClientOrderNo, payType);
                    }
                     else {
                        MyToast.showText(map.get(AppHelper.TRANS_DATA));
                    }
                } else{
//                    textView.setText("Intent is null");
                }
            } else {
//                textView.setText("resultCode is not RESULT_OK");
            }
        } else if(REQUEST_CODE_SCAN == requestCode) {
            if(RESULT_OK == resultCode) {
                if (data != null) {
                    String content = data.getStringExtra(DECODED_CONTENT_KEY);
                    uploadWalletPay(content);
//                qrCodeImage.setImageBitmap(bitmap);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_coupons:
                    break;
                case R.id.btn_swiping_card:
                    isReprint = false;
                    PayInfoBeanDao dao = DBManager.getInstance().getSession().getPayInfoBeanDao();
                    List<PayInfoBean> list = dao.loadAll();
//                    dao.update(user);
//                    dao.deleteAll();
                    payType = 6;
                    StringBuffer sb = new StringBuffer();
                    sb.append("{\"amt\":");
                    sb.append("\"");
                    sb.append(String.valueOf(outputAmount));
                    sb.append("\"}");
//                    String ss = "{\"amt\":" + outputAmount +"}";
                    JSONObject json = null;
                    try {
                        json = new JSONObject(sb.toString());
//                        json = new JSONObject("{\"amt\":\"1\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppHelper.callTrans(GatheringActivity.this, "银行卡收款", "消费", json);
                    break;
                case R.id.btn_cash_account:
                    isReprint = false;
                    payType = 7;
                    Intent intent2 = new Intent(GatheringActivity.this, CashAccountActivity.class);
                    Bundle bd2 = new Bundle();
                    bd2.putString("password", password);
                    bd2.putInt("point", point);
                    bd2.putString("memberCardNo", memberCardNo);
                    if(saleRespBean != null) {
                        bd2.putParcelable("SaleRespBean", saleRespBean);
                    } else {
                        bd2.putInt("outputAmount", outputAmount);
                    }
                    intent2.putExtras(bd2);
                    startActivity(intent2);
                    break;
                case R.id.btn_zfb:
                    isReprint = false;
                    payType = 1;
//                    String ss1 = "{\"amt\":" + outputAmount +"}";
                    StringBuffer sb1 = new StringBuffer();
                    sb1.append("{\"amt\":");
                    sb1.append("\"");
                    sb1.append(String.valueOf(outputAmount));
                    sb1.append("\"}");
                    String ss1 = "{\"amt\":" + 1 +"}";
                    JSONObject json1 = null;
                    try {
//                        json1 = new JSONObject(ss1);
                        json1 = new JSONObject(sb1.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppHelper.callTrans(GatheringActivity.this, "POS 通", "POS通", json1);
                    break;
                case R.id.btn_weixin:
                    isReprint = false;
                    payType = 3;
//                    String ss2 = "{\"amt\":" + outputAmount +"}";
                    String ss2 = "{\"amt\":" + 1 +"}";
                    StringBuffer sb2 = new StringBuffer();
                    sb2.append("{\"amt\":");
                    sb2.append("\"");
                    sb2.append(String.valueOf(outputAmount));
                    sb2.append("\"}");
                    JSONObject json2 = null;
                    try {
//                        json2 = new JSONObject(ss2);
                        json2 = new JSONObject(sb2.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppHelper.callTrans(GatheringActivity.this, "POS 通", "POS通", json2);
                    break;
                case R.id.btn_qianbao:
                    isReprint = false;
                    payType = 13;
                    //先注释掉功能
//                    Intent intent = new Intent(GatheringActivity.this,
//                            CaptureActivity.class);
//                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                    break;
                case R.id.lv_left:
                    finish();
                    break;
                case R.id.btn_reprint:
                    isReprint = true;
//                    String rep = "{\"traceNo\":\"000000\"}";
                    StringBuffer sb3 = new StringBuffer();
                    JSONObject jsonrep = null;
                    if(payType == 6) {
                        sb3.append("{\"traceNo\":");
                        sb3.append("\"");
                        sb3.append(payInfoBean.getTraceNo());
                        sb3.append("\"}");
                        try {
                            jsonrep = new JSONObject(sb3.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppHelper.callTrans(GatheringActivity.this, "银行卡收款", "交易明细", jsonrep);
                    } else if(payType == 1 || payType == 3) {
                        sb3.append("{\"traceNo\":");
                        sb3.append("\"");
                        sb3.append(payInfoBean.getTraceNo());
                        sb3.append("\"}");
                        try {
                            jsonrep = new JSONObject(sb3.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppHelper.callTrans(GatheringActivity.this, "POS 通", "交易明细", jsonrep);
                    } else if(payType == 7)
                        reprint(packetSubBean.getClientOrderNo(), 7);
//                        printInfo(packetSubBean.getClientOrderNo(), packetResp == null ? null : packetResp.point_url, packetSubBean.getPayType());
                    else if(payType == 13)
                        reprint(packetSubBean.getClientOrderNo(), 13);
//                        printInfo(packetSubBean.getClientOrderNo(), packetResp == null ? null : packetResp.point_url, packetSubBean.getPayType());
                    break;
                case R.id.btn_success:
                    if(MemberActivity.instance != null)
                        MemberActivity.instance.finish();
                    finish();
                    break;
                case R.id.btn_confirm_shouyin:
                    payType = 7;
                    uplaodCashAccountFlowing();
                    break;

            }
        }
    };
}
