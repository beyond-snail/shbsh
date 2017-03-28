package com.landicorp.yinshang.zfbj.common;

////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//              佛祖保佑       永无BUG     永不修改                  //
//                                                                //
//          佛曰:                                                  //
//                  写字楼里写字间，写字间里程序员；                   //
//                  程序人员写程序，又拿程序换酒钱。                   //
//                  酒醒只在网上坐，酒醉还来网下眠；                   //
//                  酒醉酒醒日复日，网上网下年复年。                   //
//                  但愿老死电脑间，不愿鞠躬老板前；                   //
//                  奔驰宝马贵者趣，公交自行程序员。                   //
//                  别人笑我忒疯癫，我笑自己命太贱；                   //
//                  不见满街漂亮妹，哪个归得程序员？                   //
////////////////////////////////////////////////////////////////////

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.landicorp.yinshang.utils.StringUtils;
import com.landicorp.yinshang.zfbj.moudle.ShiftRoomSave;
import com.landicorp.yinshang.zfbj.utils.LogUtils;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**********************************************************
 *                                                        *
 *                  Created by wucongpeng on 2017/3/14.        *
 **********************************************************/


public class CommonFunc {
    public static void startAction(Activity context, Class<?> cls, boolean flag) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        if (flag){
            context.finish();
        }
    }

    public static void startAction(Activity context, Class<?> cls, Bundle bundle, boolean flag) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (flag){
            context.finish();
        }
    }

    public static void startResultAction(Activity context, Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (bundle != null){
            intent.putExtras(bundle);
        }

        context.startActivityForResult(intent, requestCode);
    }

    // 删除前7天的数据，保存最新7天数据
    public static void ShiftRoomDataDelete() {
        Date t = StringUtils.getDateFromDate(new Date(), -7);
        LogUtils.e(StringUtils.getDateFormate(t));
        List<ShiftRoomSave> list = DataSupport.findAll(ShiftRoomSave.class);
        for (int i = 0; i < list.size(); i++) {

            if (!StringUtils.isEmpty(StringUtils.timeStamp2Date(list.get(i).getEnd_time()+""))) {
                LogUtils.e(StringUtils.timeStamp2Date(list.get(i).getEnd_time()+""));
                Date t1 = StringUtils.getDateFromString(StringUtils.timeStamp2Date(list.get(i).getEnd_time()+""), "yyyy-MM-dd");
                if (t1 != null) {
                    LogUtils.e(StringUtils.getDateFormate(t1));
                    if (t1.compareTo(t) < 0) {
                        int deleteCount = DataSupport.deleteAll(ShiftRoomSave.class, "end_time = ?",
                                list.get(i).getEnd_time()+"");
                        LogUtils.e(deleteCount + "删除数据成功");
                    }
                }

            }

        }
    }
}
