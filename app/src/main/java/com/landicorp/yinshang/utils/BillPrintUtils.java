package com.landicorp.yinshang.utils;

/**
 * Created by u on 2017/1/18.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.LoginRespBean;
import com.landicorp.yinshang.db.LoginRespBeanDao;
import com.landicorp.yinshang.zfbj.moudle.ShiftRoom;
import com.ums.AppHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.type;

/**
 *
 *
 * @author
 */
public class BillPrintUtils {

    /**
     * 小票图片的宽度
     */
    final static public int WIDTH = 380;

    final static public int LINE_WIDTH = 400;
    static SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * ERP购物小票
     * @param orderNo 订单号
     * @param scanCodeUrl 二维码信息
     * @author wxtang 2016-3-28下午4:37:45
     */
    static public Boolean printBill(Activity context, double dingdanMoney, double shishouMoney, double jifenMony, double youhuiMoney, double backAmt, String orderNo, Bitmap scanCode, int payType) {
        String type ="";
        switch (payType) {
            case 1:
                type = "支付宝";
                break;
            case 3:
                type = "微信";
                break;
            case 4:
                type = "百付宝";
                break;
            case 5:
                type = "京东";
                break;
            case 6:
                type = "刷卡";
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
                break;
            case 14:
                type = "钱包撤销";
                break;
        }
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        LoginRespBean bean = null;
        if(list != null && list.size() > 0) {
            bean = list.get(0);
        }
        try {

            int height = 680;

            // 打印高度计算：每多一行加30
            Bitmap bitmap = Bitmap
                    .createBitmap(WIDTH, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);

            String content = "";

            content = "POS签购单";
            paint.setTextSize(35);
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_CENTER, 0, 0, content);

            // 打印交易时间
            paint.setTextSize(24);

            float nextTransY = 50;

            content = "商户名称:" + bean.getTerminalName() + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, nextTransY, content);

            // 打印单号
            content = "商户编号:"+bean.getMerchantNo()+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 打印单号
            content = "终端号:"+bean.getTerminalNo()+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

//            // 打印收银员
//            content = "操作员:"+"\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 打印收银员
            content = "日期时间:"+sf.format(new Date()) + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "商户订单号:" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = orderNo +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "订单金额:"+dingdanMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "实收金额:"+shishouMoney + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "积分抵扣金额:"+jifenMony+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "优惠券抵扣金额:"+youhuiMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "返利金额:"+backAmt +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "支付方式:"+ type +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = " \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "持卡人签名:"+"\r\n"+"\r\n"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = " \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 60, content);

            // 分割线
            content = "------------------------------------------------------\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 列名称
//            content = "商品名        单价   数量    金额     折扣\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "本人确认以上交易， 同意将其计入\n 本卡账户 \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = " \r\n" ;
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 60, content);
            if(scanCode != null) {
                content = "使用微信扫一扫 获取更多优惠信息 \r\n";
                canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            }
            // 分割线
//            content = "-----------------------小计-----------------------\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 分割线
//            content = "============================\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            if(scanCode != null) {
//                Bitmap bitmapQR = CodeCreator.createQRCode(scanCodeUrl);
                Bitmap bitmapQR = scanCode;
                Bitmap dist = Bitmap.createBitmap(bitmapQR.getWidth(), bitmapQR.getHeight(), Config.ARGB_8888);
                Canvas canvas1 = new Canvas(dist);
                canvas1.drawColor(Color.WHITE);
                canvas1.drawBitmap(bitmapQR, 0, 0, new Paint());
                System.out.println(dist.getHeight());
                Bitmap mergMap = mergeBitmap_TB(bitmap, dist, false);

                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                System.out.println(path + "  " + mergMap.getHeight());
                FileOutputStream os = new FileOutputStream(new File(path));
                mergMap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            } else {
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                System.out.println(path);
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ERP购物小票
     * @param orderNo 订单号
     * @param scanCodeUrl 二维码信息
     * @author wxtang 2016-3-28下午4:37:45
     */
    static public Boolean printBill(Activity context, double dingdanMoney, double shishouMoney, double jifenMony, double youhuiMoney, double backAmt, String orderNo,
                                    Bitmap scanCode, int payType, Bitmap couponBitmap, int point ,String title_url, int titleMoney) {
        String type ="";
        switch (payType) {
            case 1:
                type = "支付宝";
                break;
            case 3:
                type = "微信";
                break;
            case 4:
                type = "百付宝";
                break;
            case 5:
                type = "京东";
                break;
            case 6:
                type = "刷卡";
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
                break;
            case 14:
                type = "钱包撤销";
                break;
        }
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        LoginRespBean bean = null;
        if(list != null && list.size() > 0) {
            bean = list.get(0);
        }
        try {

            int height = 680;

            // 打印高度计算：每多一行加30
            Bitmap bitmap = Bitmap
                    .createBitmap(WIDTH, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);

            String content = "";

            content = "POS签购单";
            paint.setTextSize(35);
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_CENTER, 0, 0, content);

            // 打印交易时间
            paint.setTextSize(24);

            float nextTransY = 50;

            content = "商户名称:" + bean.getTerminalName() + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, nextTransY, content);

            // 打印单号
            content = "商户编号:"+bean.getMerchantNo()+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 打印单号
            content = "终端号:"+bean.getTerminalNo()+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

//            // 打印收银员
//            content = "操作员:"+"\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 打印收银员
            content = "日期时间:"+sf.format(new Date()) + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "商户订单号:" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = orderNo +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "订单金额:"+dingdanMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "实收金额:"+shishouMoney + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "积分抵扣金额:"+jifenMony+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "优惠券抵扣金额:"+youhuiMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "返利金额:"+backAmt +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "支付方式:"+ type +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = " \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "持卡人签名:"+"\r\n"+"\r\n"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = " \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 60, content);

            // 分割线
            content = "------------------------------------------------------\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 列名称
//            content = "商品名        单价   数量    金额     折扣\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "本人确认以上交易， 同意将其计入\n 本卡账户 \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = " \r\n" ;
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 60, content);
            if(scanCode != null) {
                content = "使用微信扫一扫 获取更多优惠信息 \r\n";
                canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            }
            // 分割线
//            content = "-----------------------小计-----------------------\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 分割线
//            content = "============================\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            if(scanCode != null) {
                Bitmap bitmapPrint = null;
                Bitmap bitmapQR = scanCode;
                Bitmap dist = Bitmap.createBitmap(bitmapQR.getWidth(), bitmapQR.getHeight(), Config.ARGB_8888);
                Canvas canvas1 = new Canvas(dist);
                canvas1.drawColor(Color.WHITE);
                canvas1.drawBitmap(bitmapQR, 0, 0, new Paint());
                System.out.println(dist.getHeight());
                bitmapPrint = mergeBitmap_TB(bitmap, dist, false);
                if(couponBitmap != null) {
                    Bitmap bitmap2 = Bitmap
                            .createBitmap(WIDTH, 100, Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmap2);
                    canvas2.drawColor(Color.WHITE);
                    TextPaint paint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint2.setStrokeWidth(2);
                    paint2.setColor(Color.BLACK);
                    paint2.setTypeface(Typeface.SANS_SERIF);
                    paint2.setTextSize(24);
                    String content2 = "";
                    content2 = "------------------------------------------------------\r\n";
                    canvas2 = printSingleLine(canvas2, paint2, Alignment.ALIGN_NORMAL, 0, 30, content2);
                    content2 = "本次扫码积分：" + point + "\r\n";
                    canvas2 = printSingleLine(canvas2, paint2, Alignment.ALIGN_NORMAL, 0, 30, content2);
                    canvas2.save(Canvas.ALL_SAVE_FLAG);
                    canvas2.restore();
                    bitmapPrint = mergeBitmap_TEXT(bitmapPrint, bitmap2, false);
                    bitmapPrint = mergeBitmap_TB(bitmapPrint, couponBitmap, false);

                    Bitmap bitmap3 = Bitmap
                            .createBitmap(WIDTH, 150, Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(bitmap3);
                    canvas3.drawColor(Color.WHITE);
                    TextPaint paint3 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint3.setStrokeWidth(2);
                    paint3.setColor(Color.BLACK);
                    paint3.setTypeface(Typeface.SANS_SERIF);
                    paint3.setTextSize(24);
                    String content3 = "";
                    content3 = "------------------------------------------------------\r\n";
                    canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                    if (title_url != null) {
                        content3 = "优惠券名称：" + title_url+"\r\n";
                        canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                        content3 = "优惠券金额：" + AmountUtil.divide((double) (titleMoney), (double) (100), 2)+"\r\n";
                        canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                    }
                    canvas3.save(Canvas.ALL_SAVE_FLAG);
                    canvas3.restore();
                    bitmapPrint = mergeBitmap_TEXT(bitmapPrint, bitmap3, false);
                }
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            } else {
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                System.out.println(path);
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 钱包独有的打印
     * @param orderNo 订单号
     * @param scanCodeUrl 二维码信息
     * @author wxtang 2016-3-28下午4:37:45
     */
    static public Boolean printPakectBill(Activity context, double dingdanMoney, double shishouMoney, double jifenMony, double youhuiMoney, double backAmt,String orderNo, String merchantNo, String sn,
                                    Bitmap scanCode, int payType, Bitmap couponBitmap, int point ,String title_url, int titleMoney) {
        String type ="";
        switch (payType) {
            case 1:
                type = "支付宝";
                break;
            case 3:
                type = "微信";
                break;
            case 4:
                type = "百付宝";
                break;
            case 5:
                type = "京东";
                break;
            case 6:
                type = "刷卡";
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
                break;
            case 14:
                type = "钱包撤销";
                break;
        }
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        LoginRespBean bean = null;
        if(list != null && list.size() > 0) {
            bean = list.get(0);
        }
        try {

            int height = 650;

            // 打印高度计算：每多一行加30
            Bitmap bitmap = Bitmap
                    .createBitmap(WIDTH, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);

            String content = "";

            content = "POS签购单";
            paint.setTextSize(35);
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_CENTER, 0, 0, content);

            // 打印交易时间
            paint.setTextSize(24);

            float nextTransY = 50;

            content = "商户名称:" + bean.getTerminalName() + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, nextTransY, content);

            // 打印单号
            content = "商户编号:"+merchantNo+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 打印单号
            content = "终端号:"+sn+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

//            // 打印收银员
//            content = "操作员:"+"\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 打印收银员
            content = "日期时间:"+sf.format(new Date()) + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "商户订单号:" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = orderNo +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "订单金额:"+dingdanMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "实收金额:"+shishouMoney + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "积分抵扣金额:"+jifenMony+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "优惠券抵扣金额:"+youhuiMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "返利金额:"+backAmt +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "支付方式:"+ type +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = " \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "持卡人签名:"+"\r\n"+"\r\n"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = " \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 60, content);

            // 分割线
            content = "------------------------------------------------------\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 列名称
//            content = "商品名        单价   数量    金额     折扣\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "本人确认以上交易， 同意将其计入\n 本卡账户 \r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = " \r\n" ;
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 60, content);
            if(scanCode != null) {
                content = "使用微信扫一扫 获取更多优惠信息 \r\n";
                canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            }
            // 分割线
//            content = "-----------------------小计-----------------------\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 分割线
//            content = "============================\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            if(scanCode != null) {
                Bitmap bitmapPrint = null;
                Bitmap bitmapQR = scanCode;
                Bitmap dist = Bitmap.createBitmap(bitmapQR.getWidth(), bitmapQR.getHeight(), Config.ARGB_8888);
                Canvas canvas1 = new Canvas(dist);
                canvas1.drawColor(Color.WHITE);
                canvas1.drawBitmap(bitmapQR, 0, 0, new Paint());
                System.out.println(dist.getHeight());
                bitmapPrint = mergeBitmap_TB(bitmap, dist, false);
                if(couponBitmap != null) {
                    Bitmap bitmap2 = Bitmap
                            .createBitmap(WIDTH, 100, Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmap2);
                    canvas2.drawColor(Color.WHITE);
                    TextPaint paint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint2.setStrokeWidth(2);
                    paint2.setColor(Color.BLACK);
                    paint2.setTypeface(Typeface.SANS_SERIF);
                    paint2.setTextSize(24);
                    String content2 = "";
                    content2 = "------------------------------------------------------\r\n";
                    canvas2 = printSingleLine(canvas2, paint2, Alignment.ALIGN_NORMAL, 0, 30, content2);
                    content2 = "本次扫码积分：" + point + "\r\n";
                    canvas2 = printSingleLine(canvas2, paint2, Alignment.ALIGN_NORMAL, 0, 30, content2);
                    canvas2.save(Canvas.ALL_SAVE_FLAG);
                    canvas2.restore();
                    bitmapPrint = mergeBitmap_TEXT(bitmapPrint, bitmap2, false);
                    bitmapPrint = mergeBitmap_TB(bitmapPrint, couponBitmap, false);

                    Bitmap bitmap3 = Bitmap
                            .createBitmap(WIDTH, 150, Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(bitmap3);
                    canvas3.drawColor(Color.WHITE);
                    TextPaint paint3 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint3.setStrokeWidth(2);
                    paint3.setColor(Color.BLACK);
                    paint3.setTypeface(Typeface.SANS_SERIF);
                    paint3.setTextSize(24);
                    String content3 = "";
                    content3 = "------------------------------------------------------\r\n";
                    canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                    if (title_url != null) {
                        content3 = "优惠券名称：" + title_url+"\r\n";
                        canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                        content3 = "优惠券金额：" + AmountUtil.divide((double) (titleMoney), (double) (100), 2)+"\r\n";
                        canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                    }
                    canvas3.save(Canvas.ALL_SAVE_FLAG);
                    canvas3.restore();
                    bitmapPrint = mergeBitmap_TEXT(bitmapPrint, bitmap3, false);
                }
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            } else {
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                System.out.println(path);
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static public Boolean printOnlySacnBill(Activity context, double dingdanMoney, double shishouMoney, double jifenMony, double youhuiMoney, double backAmt, Bitmap scanCode, Bitmap couponBitmap, int point ,String title_url, int titleMoney) {
        LoginRespBeanDao dao = DBManager.getInstance().getSession().getLoginRespBeanDao();
        List<LoginRespBean> list = dao.loadAll();
        LoginRespBean bean = null;
        if(list != null && list.size() > 0) {
            bean = list.get(0);
        }
        try {

            int height = 220;

            // 打印高度计算：每多一行加30
            Bitmap bitmap = Bitmap
                    .createBitmap(WIDTH, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);

            String content = "";
            // 打印交易时间
            paint.setTextSize(24);

            float nextTransY = 50;

            content = "订单金额:"+dingdanMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "实收金额:"+shishouMoney + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "积分抵扣金额:"+jifenMony+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            // 打印收银员
            content = "优惠券抵扣金额:"+youhuiMoney +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "返利金额:"+backAmt +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            if(scanCode != null) {
                content = "使用微信扫一扫 获取更多优惠信息 \r\n";
                canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            }
            // 分割线
//            content = "-----------------------小计-----------------------\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            // 分割线
//            content = "============================\r\n";
//            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            if(scanCode != null) {
                Bitmap bitmapPrint = null;
                Bitmap bitmapQR = scanCode;
                Bitmap dist = Bitmap.createBitmap(bitmapQR.getWidth(), bitmapQR.getHeight(), Config.ARGB_8888);
                Canvas canvas1 = new Canvas(dist);
                canvas1.drawColor(Color.WHITE);
                canvas1.drawBitmap(bitmapQR, 0, 0, new Paint());
                System.out.println(dist.getHeight());
                bitmapPrint = mergeBitmap_TB(bitmap, dist, false);
                if(couponBitmap != null) {
                    Bitmap bitmap2 = Bitmap
                            .createBitmap(WIDTH, 100, Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmap2);
                    canvas2.drawColor(Color.WHITE);
                    TextPaint paint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint2.setStrokeWidth(2);
                    paint2.setColor(Color.BLACK);
                    paint2.setTypeface(Typeface.SANS_SERIF);
                    paint2.setTextSize(24);
                    String content2 = "";
                    content2 = "------------------------------------------------------\r\n";
                    canvas2 = printSingleLine(canvas2, paint2, Alignment.ALIGN_NORMAL, 0, 30, content2);
                    content2 = "本次扫码积分：" + point + "\r\n";
                    canvas2 = printSingleLine(canvas2, paint2, Alignment.ALIGN_NORMAL, 0, 30, content2);
                    canvas2.save(Canvas.ALL_SAVE_FLAG);
                    canvas2.restore();
                    bitmapPrint = mergeBitmap_TEXT(bitmapPrint, bitmap2, false);
                    bitmapPrint = mergeBitmap_TB(bitmapPrint, couponBitmap, false);

                    Bitmap bitmap3 = Bitmap
                            .createBitmap(WIDTH, 150, Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(bitmap3);
                    canvas3.drawColor(Color.WHITE);
                    TextPaint paint3 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint3.setStrokeWidth(2);
                    paint3.setColor(Color.BLACK);
                    paint3.setTypeface(Typeface.SANS_SERIF);
                    paint3.setTextSize(24);
                    String content3 = "";
                    content3 = "------------------------------------------------------\r\n";
                    canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                    if (title_url != null) {
                        content3 = "优惠券名称：" + title_url+"\r\n";
                        canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                        content3 = "优惠券金额：" + AmountUtil.divide((double) (titleMoney), (double) (100), 2)+"\r\n";
                        canvas3 = printSingleLine(canvas3, paint3, Alignment.ALIGN_NORMAL, 0, 30, content3);
                    }
                    canvas3.save(Canvas.ALL_SAVE_FLAG);
                    canvas3.restore();
                    bitmapPrint = mergeBitmap_TEXT(bitmapPrint, bitmap3, false);
                }
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            } else {
                String path = Environment.getExternalStorageDirectory()
                        + "/image.png";
                System.out.println(path);
                FileOutputStream os = new FileOutputStream(new File(path));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                AppHelper.callPrint(context, path);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void printerShiftRoom(Activity context, ShiftRoom data, long start_time, long end_time, int type){
        if (Constant.PRINTER_SHIFT_ROOM == type){
            printShiftRoom(context, data, start_time, end_time, "班结统计");
        }else if (Constant.PRINTER_SHIFT_ROOM_DAY == type){
            printShiftRoom(context, data, start_time, end_time, "当日统计");
        }
    }

    private static Boolean printShiftRoom(Activity context, ShiftRoom data, long start_time, long end_time, String title) {


        try {

            int height = 650*3;

            // 打印高度计算：每多一行加30
            Bitmap bitmap = Bitmap
                    .createBitmap(WIDTH, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);

            String content = "";

            content = title;
            paint.setTextSize(35);
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_CENTER, 0, 0, content);

            // 打印交易时间
            paint.setTextSize(24);

            float nextTransY = 50;

            content = "起始时间：" + StringUtils.timeStamp2Date1(start_time + "")+":00" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, nextTransY, content);

            content = "结束时间：" + StringUtils.timeStamp2Date1(end_time + "")+":00"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "刷卡："+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_swipe() != null) ? data.getPay_swipe().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "实收金额："+ ((data.getPay_swipe() != null) ? data.getPay_swipe().getReal_pay_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "优惠券抵扣金额："+ ((data.getPay_swipe() != null) ? data.getPay_swipe().getCoupon_deduct() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "积分抵扣金额："+ ((data.getPay_swipe() != null) ? data.getPay_swipe().getIntergral_deduct() : "0")+"元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "现金："+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_cash() != null) ? data.getPay_cash().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "实收金额："+ ((data.getPay_cash() != null) ? data.getPay_cash().getReal_pay_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "优惠券抵扣金额："+ ((data.getPay_cash() != null) ? data.getPay_cash().getCoupon_deduct() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "积分抵扣金额："+ ((data.getPay_cash() != null) ? data.getPay_cash().getIntergral_deduct() : "0")+"元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "微信：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_wx() != null) ? data.getPay_wx().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "实收金额："+ ((data.getPay_wx() != null) ? data.getPay_wx().getReal_pay_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "优惠券抵扣金额："+ ((data.getPay_wx() != null) ? data.getPay_wx().getCoupon_deduct() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "积分抵扣金额："+ ((data.getPay_wx() != null) ? data.getPay_wx().getIntergral_deduct() : "0")+"元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);




            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "支付宝：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_aly() != null) ? data.getPay_aly().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "实收金额："+ ((data.getPay_aly() != null) ? data.getPay_aly().getReal_pay_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "优惠券抵扣金额："+ ((data.getPay_aly() != null) ? data.getPay_aly().getCoupon_deduct() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "积分抵扣金额："+ ((data.getPay_aly() != null) ? data.getPay_aly().getIntergral_deduct() : "0")+"元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "钱包：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_qb() != null) ? data.getPay_qb().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "实收金额："+ ((data.getPay_qb() != null) ? data.getPay_qb().getReal_pay_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "优惠券抵扣金额："+ ((data.getPay_qb() != null) ? data.getPay_qb().getCoupon_deduct() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);// 打印收银员
            content = "积分抵扣金额："+ ((data.getPay_qb() != null) ? data.getPay_qb().getIntergral_deduct() : "0")+"元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "刷卡撤销：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_unswipe() != null) ? data.getPay_unswipe().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "撤销金额："+ ((data.getPay_unswipe() != null) ? data.getPay_unswipe().getReal_pay_money() : "0") +"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "微信撤销：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_unwx() != null) ? data.getPay_unwx().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "撤销金额："+ ((data.getPay_unwx() != null) ? data.getPay_unwx().getReal_pay_money() : "0") +"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "支付宝撤销：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_unaly() != null) ? data.getPay_unaly().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "撤销金额："+ ((data.getPay_unaly() != null) ? data.getPay_unaly().getReal_pay_money() : "0") +"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "钱包撤销：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易笔数：" +((data.getPay_unqb() != null) ? data.getPay_unqb().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "撤销金额："+ ((data.getPay_unqb() != null) ? data.getPay_unqb().getReal_pay_money() : "0") +"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "--------------------------------"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易统计：" + "\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "交易总笔数："+ ((data.getTotal() != null) ? data.getTotal().getTrade_num() : "0")+"笔"+"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);
            content = "实收总金额："+ ((data.getTotal() != null) ? data.getTotal().getReal_pay_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "交易撤销总金额："+ ((data.getTotal() != null) ? data.getTotal().getReal_undo_money() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "优惠券抵扣总金额："+ ((data.getTotal() != null) ? data.getTotal().getCoupon_deduct() : "0")+"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);

            content = "积分抵扣总金额："+ ((data.getTotal() != null) ? data.getTotal().getIntergral_deduct() : "0") +"元" +"\r\n";
            canvas = printSingleLine(canvas, paint, Alignment.ALIGN_NORMAL, 0, 30, content);




            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();

            String path = Environment.getExternalStorageDirectory()
                    + "/image.png";
            System.out.println(path);
            FileOutputStream os = new FileOutputStream(new File(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            AppHelper.callPrint(context, path);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }
        int width = 0;
        width = topBitmap.getWidth();
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

//        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();
        int height = tempBitmapT.getHeight() + 300;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect  = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());
//        Rect bottomRect  = new Rect(0, 0, tempBitmapB.getWidth(), 300);

//        Rect bottomRectT  = new Rect(0, tempBitmapT.getHeight(), width, height);
        Rect bottomRectT  = new Rect(30, tempBitmapT.getHeight(), 330, tempBitmapT.getHeight()+300);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);// 第一个参数是图片原来的大小，第二个参数是 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片，第三个参数表示该图片绘画的位置
        return bitmap;
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TEXT(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }
        int width = 0;
        width = topBitmap.getWidth();
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect  = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT  = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);// 第一个参数是图片原来的大小，第二个参数是 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片，第三个参数表示该图片绘画的位置
        return bitmap;
    }

    public static void saveMyBitmap(String path,Bitmap mBitmap){
        File f = new File(path);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean flag = mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单独打印二维码
     * @param context
     */
    public static void printBitmap(Activity context, Bitmap scanCode){
        if(scanCode != null) {
            Bitmap bitmap = scanCode;
//            try {
//                bitmap = CodeCreator.createQRCode(url);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
            Bitmap dist = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(dist);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/UmsTssMaster/" + "test" + ".png";
            saveMyBitmap(path, dist);
            AppHelper.callPrint(context, path);
            dist.recycle();
            bitmap.recycle();
        }
    }

    /**
     * 打印一行
     * @param canvas
     * @param paint
     * @param alignment
     * @param transX
     * @param transY
     * @param content
     * @return
     */
    private static Canvas printSingleLine(Canvas canvas, TextPaint paint, Alignment alignment, float transX, float transY, String content){
        StaticLayout layout = new StaticLayout(content, paint, LINE_WIDTH,
                alignment, 1F, 0, false);
        canvas.translate(transX, transY);
        layout.draw(canvas);
        return canvas;
    }

}
