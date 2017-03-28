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
import com.landicorp.yinshang.data.model.TransactionRespBean;
import com.landicorp.yinshang.data.model.UndoPacketReqSubBean;
import com.landicorp.yinshang.data.model.UndoReqSubBean;
import com.landicorp.yinshang.data.model.UndoRespBean;
import com.landicorp.yinshang.data.response.TransactionResponse;
import com.landicorp.yinshang.data.response.UndoResponse;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.LoginRespBean;
import com.landicorp.yinshang.db.LoginRespBeanDao;
import com.landicorp.yinshang.db.PayInfoBean;
import com.landicorp.yinshang.db.TransactionReqSubBean;
import com.landicorp.yinshang.db.TransactionReqSubBeanDao;
import com.landicorp.yinshang.utils.AmountUtil;
import com.landicorp.yinshang.utils.BillPrintUtils;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.ums.AppHelper;

import org.greenrobot.greendao.query.Query;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by u on 2017/1/9.交易记录界面
 */
public class TransDetailActivity extends BaseActivity {

    private LinearLayout lv_left;
    private TextView txt_merchantName;//商户名称
    private TextView txt_merchantNo;//商户号
    private TextView txt_terminal;//终端号
    private TextView txt_transNo;//流水号
    private TextView txt_clientOrderNo;//订单号
    private TextView txt_time;//交易时间
    private TextView txt_type;//交易类型
    private TextView txt_dingdanAmount;//订单金额
    private TextView txt_zhifuAmount;//支付金额
    private TextView txt_pointCoverAmount;//积分抵扣金额
    private TextView txt_couponCoverAmount;//优惠券抵扣金额
    private Button btn_chexiao;//
    private Button btn_upload;//
    private Button btn_reprint;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
    private TransactionReqSubBean bean;
    private int currentPayType;
    private PayInfoBean payInfoBean;
    private boolean isReprint;

    double dingdanMoney = 0.0;
    double shishouMoney = 0.0;
    double jifenMoney = 0.0;
    double youhuiMoney = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_detail);
        init();
        Bundle bd = getIntent().getExtras();
        String clientOrderNo = bd.getString("clientOrderNo");
        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
        Query query = dao.queryBuilder().where(
        TransactionReqSubBeanDao.Properties.ClientOrderNo.eq(clientOrderNo)).build();
        List<TransactionReqSubBean> list = query.list();
        if(list != null && list.size() > 0) {
            bean = list.get(0);
            LoginRespBeanDao daoLogin = DBManager.getInstance().getSession().getLoginRespBeanDao();
            List<LoginRespBean> listLogin = daoLogin.loadAll();
            if(listLogin != null && listLogin.size() > 0) {
                txt_merchantName.setText(listLogin.get(0).getTerminalName());
                txt_merchantNo.setText(bean.getMerchantNo());
                if(bean.getPayType() == 1 || bean.getPayType() == 3) {
                    txt_terminal.setText(listLogin.get(0).getTerminalNo());
                } else if(bean.getPayType() == 13) {
                    txt_terminal.setText(((BaseApplication)getApplication()).getSystemInfo().getSN());
                } else {
                    txt_terminal.setText(listLogin.get(0).getTerminalNo());
                }
            }

//            txt_merchantNo.setText(bean.getMerchantNo());
            txt_transNo.setText(bean.getTransNo());
            txt_clientOrderNo.setText(bean.getClientOrderNo());
            txt_time.setText(sf.format(new Date(bean.getT() * 1000)));
//            int dingdanAmt = bean.getBankAmount() + bean.getCash() + bean.getCouponCoverAmount() + bean.getPointCoverAmount();
            txt_dingdanAmount.setText("¥ " + AmountUtil.divide(Double.valueOf(bean.getOrderAmount()), (double) 100, 2));
            if(bean.getPayType() != 7)
                txt_zhifuAmount.setText("¥ " +  AmountUtil.divide((double) (bean.getBankAmount()), (double) 100, 2));
            else
                txt_zhifuAmount.setText("¥ " +  AmountUtil.divide((double) (bean.getCash()), (double) 100, 2));
            txt_pointCoverAmount.setText("¥ " +  AmountUtil.divide((double) (bean.getPointCoverAmount()), (double) 100, 2));
            txt_couponCoverAmount.setText("¥ " +  AmountUtil.divide((double) (bean.getCouponCoverAmount()), (double) 100, 2));
            String type ="";
            switch (bean.getPayType()) {
                case 1:
                    type = "支付宝";
                    if(bean.getIsUndo() != null)
                        type = "支付宝（已撤销）";
                    break;
                case 3:
                    type = "微信";
                    if(bean.getIsUndo() != null)
                        type = "微信（已撤销）";
                    break;
                case 4:
                    type = "百付宝";
                    break;
                case 5:
                    type = "京东";
                    break;
                case 6:
                    type = "刷卡";
                    if(bean.getIsUndo() != null)
                        type = "刷卡（已撤销）";
                    break;
                case 7:
                    type = "现金";
                    break;
                case 10:
                    type = "刷卡撤销";
                    break;
                case 11:
                    type = "微信撤销";
                    break;
                case 12:
                    type = "支付撤销";
                    break;
                case 13:
                    type = "钱包";
                    if(bean.getIsUndo() != null)
                        type = "钱包（已撤销）";
                    break;
                case 14:
                    type = "钱包撤销";
                    break;
            }
            txt_type.setText(type);
            if(bean.getPayType() == 1 || bean.getPayType() == 3 || bean.getPayType() == 4 || bean.getPayType() == 5 || bean.getPayType() == 6 || bean.getPayType() == 13) {
//                btn_reprint.setEnabled(true);
                btn_chexiao.setVisibility(View.VISIBLE);
                if(bean.getIsUndo() == null)
                    btn_chexiao.setEnabled(true);
                else
                    btn_chexiao.setEnabled(false);
            } else if(bean.getPayType() == 7) {
//                btn_reprint.setEnabled(true);
                btn_chexiao.setVisibility(View.GONE);
            } else {
                btn_chexiao.setVisibility(View.GONE);
//                btn_reprint.setEnabled(false);
//                btn_chexiao.setEnabled(false);
            }
            if(bean.getIsUploadSuccess() != null) {
                btn_upload.setVisibility(View.GONE);
            } else {
                btn_upload.setVisibility(View.VISIBLE);
            }
        }
        dingdanMoney = AmountUtil.divide(Double.valueOf(bean.getOrderAmount()), (double) 100, 2);
        if (bean.getPayType() == 7)
            shishouMoney = AmountUtil.divide((double) (bean.getCash()), (double) 100, 2);
        else
            shishouMoney = AmountUtil.divide((double) bean.getBankAmount(), (double) 100, 2);
        jifenMoney = AmountUtil.divide((double) bean.getPointCoverAmount(), (double) 100, 2);
        youhuiMoney = AmountUtil.divide((double) bean.getCouponCoverAmount(), (double) 100, 2);
    }

    private void init() {
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        txt_merchantName = (TextView) findViewById(R.id.txt_merchantName);
        txt_merchantNo = (TextView) findViewById(R.id.txt_merchantNo);
        txt_terminal = (TextView) findViewById(R.id.txt_terminal);
        txt_transNo = (TextView) findViewById(R.id.txt_transNo);
        txt_clientOrderNo = (TextView) findViewById(R.id.txt_clientOrderNo);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_dingdanAmount = (TextView) findViewById(R.id.txt_dingdanAmount);
        txt_zhifuAmount= (TextView) findViewById(R.id.txt_zhifuAmount);
        txt_pointCoverAmount = (TextView) findViewById(R.id.txt_pointCoverAmount);
        txt_couponCoverAmount = (TextView) findViewById(R.id.txt_couponCoverAmount);
        btn_chexiao = (Button) findViewById(R.id.btn_chexiao);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_reprint = (Button) findViewById(R.id.btn_reprint);
        lv_left.setOnClickListener(clickListener);
        btn_chexiao.setOnClickListener(clickListener);
        btn_upload.setOnClickListener(clickListener);
        btn_reprint.setOnClickListener(clickListener);
    }

    /**
     * 仅限银行卡和pos通撤销
     */
    private void undo() {
        int cot = 1+(int)(Math.random()*999);
        String clientOrderNo = "2" + currentPayType + sf1.format(new Date())+ cot + ((BaseApplication)getApplication()).getSystemInfo().getSN() ;
        BaseReqBean<UndoReqSubBean> baseReqBean = new BaseReqBean<UndoReqSubBean>();
        baseReqBean.setCmd("trade_cancel");
        UndoReqSubBean undoReqSubBean = new UndoReqSubBean();
        String action = "2";
        String authCode = payInfoBean.getTraceNo();
        String new_trade_order_num = clientOrderNo;
        String old_trade_order_num = bean.getClientOrderNo();
        String payType = currentPayType + "";
        long t = new Date().getTime() / 1000;
        StringBuffer sb = new StringBuffer();
        sb.append(action);//action 1为查询订单 2为交易撤销
        sb.append(authCode);//auth
        sb.append(new_trade_order_num);//new_trade_order_num
        sb.append(old_trade_order_num);//old_trade_order_num
        sb.append(payType);//payType
        sb.append(t);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        undoReqSubBean.setVerify(verify);
        undoReqSubBean.setAction(action);
        undoReqSubBean.setAuthCode(authCode);
        undoReqSubBean.setNew_trade_order_num(new_trade_order_num);
        undoReqSubBean.setOld_trade_order_num(old_trade_order_num);
        undoReqSubBean.setPayType(payType);
        undoReqSubBean.setT(t);
        baseReqBean.setParams(undoReqSubBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.undo(getBody(postInfoStr)), new ReqCallBack<UndoResponse>() {

            @Override
            public void onReqSuccess(UndoResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    UndoRespBean undoRespBean = result.result;
                    MyToast.showText("撤销成功");
                    txt_type.setText(txt_type.getText().toString()+"( 已撤销)");
                    btn_chexiao.setEnabled(false);
//                    TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
//                    bean.setIsUndo(true);
//                    dao.insertOrReplace(bean);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    /**
     * 钱包撤销
     */
    private void undoPacket() {
        int cot = 1+(int)(Math.random()*999);
        String clientOrderNo = "2" + currentPayType + sf1.format(new Date())+ cot + ((BaseApplication)getApplication()).getSystemInfo().getSN() ;
        BaseReqBean<UndoPacketReqSubBean> baseReqBean = new BaseReqBean<UndoPacketReqSubBean>();
        baseReqBean.setCmd("trade_packet_cancel");
        UndoPacketReqSubBean undoReqSubBean = new UndoPacketReqSubBean();
        String action = "2";
        String new_trade_order_num = clientOrderNo;
        String old_trade_order_num = bean.getClientOrderNo();
        String payType = currentPayType + "";
        int sid = SharePreferenceHelper.getInstance(TransDetailActivity.this).getInt("SID");
        StringBuffer sb = new StringBuffer();
        sb.append(action);//action 1为查询订单 2为交易撤销
        sb.append(new_trade_order_num);//new_trade_order_num
        sb.append(old_trade_order_num);//old_trade_order_num
        sb.append(payType);//payType
        sb.append(sid);
        sb.append(Constant.KEY);
        String verify = MD5Util.md5(sb.toString());
        undoReqSubBean.setVerify(verify);
        undoReqSubBean.setAction(action);
        undoReqSubBean.setNew_trade_order_num(new_trade_order_num);
        undoReqSubBean.setOld_trade_order_num(old_trade_order_num);
        undoReqSubBean.setPayType(payType);
        undoReqSubBean.setSid(String.valueOf(sid));
        baseReqBean.setParams(undoReqSubBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).post(this, apiService.undo(getBody(postInfoStr)), new ReqCallBack<UndoResponse>() {

            @Override
            public void onReqSuccess(UndoResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                    bean.setIsUndo("1");
//                    bean.setPayType(currentPayType);
                    dao.insertOrReplace(bean);
                    MyToast.showText("撤销成功");
                    txt_type.setText(txt_type.getText().toString()+"( 已撤销)");
                    btn_chexiao.setEnabled(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    private void uplaodFlowing() {
        BaseReqBean<TransactionReqSubBean> baseReqBean = new BaseReqBean<TransactionReqSubBean>();
        baseReqBean.setCmd("tranUploadPos");
        final TransactionReqSubBean subBean = new TransactionReqSubBean();
        subBean.setActivateCode(bean.getActivateCode());
        subBean.setAuthCode(bean.getAuthCode());
        subBean.setBankAmount(bean.getBankAmount());
        subBean.setCardNo(bean.getCardNo());
        subBean.setCash(bean.getCash());
        subBean.setClientOrderNo(bean.getClientOrderNo());
        subBean.setCouponCoverAmount(bean.getCouponCoverAmount());
        subBean.setCouponSns(bean.getCouponSns());
        subBean.setMerchantNo(bean.getMerchantNo());
        subBean.setPassword(bean.getPassword());
        subBean.setPayType(bean.getPayType());
        subBean.setPointAmount(bean.getPointAmount());
        subBean.setPointCoverAmount(bean.getPointCoverAmount());
        subBean.setSerialNum(bean.getSerialNum());
        subBean.setSid(bean.getSid());
        subBean.setT(bean.getT());
        subBean.setTransNo(bean.getTransNo());
        subBean.setVerify(bean.getVerify());
        baseReqBean.setParams(subBean);
        String postInfoStr = gson.toJson(baseReqBean);
        RequestManager.getInstance(this).postSpecial(this, apiService.uploadTransaction(getBody(postInfoStr)), new ReqCallBack<TransactionResponse>() {

            @Override
            public void onReqSuccess(TransactionResponse result) {
                if (result.code.equals(Constant.SUCCESS_CODE)) {
                    MyToast.showText("流水上传成功");
                    TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                    bean.setIsUploadSuccess("1");
                    dao.insertOrReplace(bean);
                    btn_upload.setVisibility(View.GONE);
                } else {
                    MyToast.showText("流水上传失败");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                MyToast.showText("流水上传失败");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(AppHelper.TRANS_REQUEST_CODE == requestCode) {
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
                            TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
//                            bean.setPayType(currentPayType);
                            bean.setIsUndo("1");
                            dao.insertOrReplace(bean);
                            btn_chexiao.setEnabled(false);
                            undo();
//                            btn_chexiao.setEnabled(false);
//                            btn_upload.setEnabled(false);
                        }
                        MyToast.showText(payInfoBean.getResDesc());
                    } else if(map.get(AppHelper.RESULT_CODE).equals("0") && isReprint) {
                        reprintScanOnlyReq(bean.getClientOrderNo(), bean.getPayType());
                    } else {
                        MyToast.showText(map.get(AppHelper.TRANS_DATA));
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * //重新打印
     * @param
     */
    private void reprintReq(final String clientOrderNo, final int payType) {
        int sid = SharePreferenceHelper.getInstance(TransDetailActivity.this).getInt("SID");
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

    /**
     * //重新打印
     * @param
     */
    private void reprintScanOnlyReq(final String clientOrderNo, final int payType) {
        int sid = SharePreferenceHelper.getInstance(TransDetailActivity.this).getInt("SID");
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

    private void printOnlyScan(final String clientOrderNo, final int payType, final TransactionRespBean result) {

        if(result != null && result.point_url != null) {
            Glide.with(this).load(result.point_url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    final Bitmap resourceYouhui = resource;
                    if(result.coupon != null && !result.coupon.equals("")) {
                        Glide.with(TransDetailActivity.this).load(result.coupon).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                BillPrintUtils.printOnlySacnBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,
                                        AmountUtil.divide((double) result.backAmt, (double) 100, 2), resourceYouhui,  resource, result.point, result.title_url, result.money);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                BillPrintUtils.printOnlySacnBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), resourceYouhui,  null, result.point, result.title_url, result.money);
                            }
                        });
                    } else {
                        BillPrintUtils.printOnlySacnBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2), resourceYouhui,  null, result.point, result.title_url, result.money);
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
                        Glide.with(TransDetailActivity.this).load(result.coupon).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if(payType == 13) {
                                    BillPrintUtils.printPakectBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, bean.getMerchantNo(),
                                            ((BaseApplication)getApplication()).getSystemInfo().getSN(), resourceYouhui, payType,
                                            resource, result.point, result.title_url, result.money);
                                } else
                                    BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resourceYouhui, payType,
                                        resource, result.point, result.title_url, result.money);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                if(payType == 13) {
                                    BillPrintUtils.printPakectBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, bean.getMerchantNo(),
                                            ((BaseApplication)getApplication()).getSystemInfo().getSN(), resourceYouhui, payType,
                                            null, result.point, result.title_url, result.money);
                                } else
                                    BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney,AmountUtil.divide((double) result.backAmt, (double) 100, 2), clientOrderNo, resourceYouhui, payType);
                            }
                        });
                    } else {
                        if(payType == 13) {
                            BillPrintUtils.printPakectBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, bean.getMerchantNo(),
                                    ((BaseApplication)getApplication()).getSystemInfo().getSN(), resourceYouhui, payType,
                                    null, result.point, result.title_url, result.money);
                        } else
                            BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, resource, payType);
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    if(payType == 13) {
                        BillPrintUtils.printPakectBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, bean.getMerchantNo(),
                                ((BaseApplication)getApplication()).getSystemInfo().getSN(), null, payType,
                                null, result.point, result.title_url, result.money);
                    } else
                        BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, AmountUtil.divide((double) result.backAmt, (double) 100, 2),clientOrderNo, null, payType);
                }
            }); //方法中设置asBitmap可以设置回调类型
        } else {
            if(payType == 13) {
                BillPrintUtils.printPakectBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, 0.0, clientOrderNo, bean.getMerchantNo(),
                        ((BaseApplication)getApplication()).getSystemInfo().getSN(), null, payType,
                        null, result.point, result.title_url, result.money);
            } else
                BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, 0.0,clientOrderNo, null, payType);
        }
    }

//    private void printInfo(final String clientOrderNo, String scanCodeUrl, final int payType) {
//
//        Glide.with(this).load(scanCodeUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                double dingdanMoney = 0.0;
//                double shishouMoney = 0.0;
//                double jifenMoney = 0.0;
//                double youhuiMoney = 0.0;
//                dingdanMoney = AmountUtil.divide(Double.valueOf(bean.getOrderAmount()), (double) 100, 2);
//                if (bean.getPayType() == 7)
//                    shishouMoney = AmountUtil.divide((double) (bean.getCash()), (double) 100, 2);
//                else
//                    shishouMoney = AmountUtil.divide((double) bean.getBankAmount(), (double) 100, 2);
//                jifenMoney = AmountUtil.divide((double) bean.getPointCoverAmount(), (double) 100, 2);
//                youhuiMoney = AmountUtil.divide((double) bean.getCouponCoverAmount(), (double) 100, 2);
//                BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, clientOrderNo, resource, bean.getPayType());
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                double dingdanMoney = 0.0;
//                double shishouMoney = 0.0;
//                double jifenMoney = 0.0;
//                double youhuiMoney = 0.0;
//                dingdanMoney = AmountUtil.divide(Double.valueOf(bean.getOrderAmount()), (double) 100, 2);
//                if (bean.getPayType() == 7)
//                    shishouMoney = AmountUtil.divide((double) (bean.getCash()), (double) 100, 2);
//                else
//                    shishouMoney = AmountUtil.divide((double) bean.getBankAmount(), (double) 100, 2);
//                jifenMoney = AmountUtil.divide((double) bean.getPointCoverAmount(), (double) 100, 2);
//                youhuiMoney = AmountUtil.divide((double) bean.getCouponCoverAmount(), (double) 100, 2);
//                BillPrintUtils.printBill(TransDetailActivity.this, dingdanMoney, shishouMoney, jifenMoney, youhuiMoney, clientOrderNo, null, bean.getPayType());
//            }
//        }); //方法中设置asBitmap可以设置回调类型
//    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lv_left:
                    finish();
                    break;
                case R.id.btn_chexiao:
                    isReprint = false;
                    if(SharePreferenceHelper.getInstance(TransDetailActivity.this).getString("Date") != null) {
                        String date = SharePreferenceHelper.getInstance(TransDetailActivity.this).getString("Date");
                        if(!sf2.format(new Date()).equals(date)) {
                            MyToast.showText("请先签到");
                            return;
                        }
                    }
                    if(bean.getPayType() == 6) {
                        currentPayType = 10;
                        JSONObject json = null;
                            try {
//                        json = new JSONObject(ss);
                                StringBuffer sb = new StringBuffer();
                                sb.append("{\"orgTraceNo\":");
                                sb.append("\"");
                                sb.append(bean.getTransNo());
                                sb.append("\"}");
                                json = new JSONObject(sb.toString());
//                        json = new JSONObject("{\"orgTraceNo\":\"000107\"}");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AppHelper.callTrans(TransDetailActivity.this, "银行卡收款", "撤销", json);
                    } else if(bean.getPayType() == 1) {
                        currentPayType = 12;
                        JSONObject json = null;
                            try {
                                StringBuffer sb = new StringBuffer();
                                sb.append("{\"oldTraceNo\":");
                                sb.append("\"");
                                sb.append(bean.getTransNo());
                                sb.append("\"}");
                                json = new JSONObject(sb.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AppHelper.callTrans(TransDetailActivity.this, "POS 通", "消费撤销", json);
                    }else if(bean.getPayType() == 3) {
                        currentPayType = 11;
                        JSONObject json = null;
                        try {
                            StringBuffer sb = new StringBuffer();
                            sb.append("{\"oldTraceNo\":");
                            sb.append("\"");
                            sb.append(bean.getTransNo());
                            sb.append("\"}");
                            json = new JSONObject(sb.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppHelper.callTrans(TransDetailActivity.this, "POS 通", "消费撤销", json);
                    } else if(bean.getPayType() == 13) {
                        currentPayType = 14;
                        undoPacket();
                    }

                    break;
                case R.id.btn_upload:
                    isReprint = false;
                    uplaodFlowing();
                    break;
                case R.id.btn_reprint:
                    isReprint = true;
                    StringBuffer sb3 = new StringBuffer();
                    JSONObject jsonrep = null;
                    if(bean.getPayType() == 6) {
                        sb3.append("{\"traceNo\":");
                        sb3.append("\"");
                        sb3.append(bean.getTransNo());
                        sb3.append("\"}");
                        try {
                            jsonrep = new JSONObject(sb3.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppHelper.callTrans(TransDetailActivity.this, "银行卡收款", "交易明细", jsonrep);
                    } else if(bean.getPayType() == 1 || bean.getPayType() == 3) {
                        sb3.append("{\"traceNo\":");
                        sb3.append("\"");
                        sb3.append(bean.getTransNo());
                        sb3.append("\"}");
                        try {
                            jsonrep = new JSONObject(sb3.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppHelper.callTrans(TransDetailActivity.this, "POS 通", "交易明细", jsonrep);
                    }
                    else if(bean.getPayType() == 7)
                        reprintReq(bean.getClientOrderNo(), 7);
//                        printInfo(bean.getClientOrderNo(), bean.getScanUrl(), bean.getPayType());
                    else if(bean.getPayType() == 13)
                        reprintReq(bean.getClientOrderNo(), 13);
//                        printInfo(bean.getClientOrderNo(), bean.getScanUrl(), bean.getPayType());
                    break;

            }
        }
    };
}
