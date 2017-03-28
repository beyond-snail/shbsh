package com.landicorp.yinshang.zfbj.core;

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

import android.content.Context;

import com.landicorp.yinshang.myokhttp.MyOkHttp;
import com.landicorp.yinshang.myokhttp.response.GsonResponseHandler;
import com.landicorp.yinshang.myokhttp.util.LogUtils;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MD5Util;
import com.landicorp.yinshang.zfbj.moudle.ApiResponse;
import com.landicorp.yinshang.zfbj.moudle.ShiftRoom;
import com.landicorp.yinshang.zfbj.myInterface.ActionCallbackListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**********************************************************
 *                                                        *
 *                  Created by wucongpeng on 2017/3/14.        *
 **********************************************************/


public class SbsAction {

    private static final String TAG = "SbsAction";

    public void shift_room(final Context context, int sid, long start_time, long end_time,
                           final ActionCallbackListener<ShiftRoom> listener) {


        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("sid", sid);
        paramsMap.put("start_time", start_time);
        paramsMap.put("end_time", end_time);
        String data = getJsonStr("shift_time", paramsMap, "verify", Constant.KEY);
        LogUtils.e(TAG, "request: " + data);
        String url = Constant.BASE_URL+"/api/index.php";
        LogUtils.e(TAG, "URL: " + url);
        MyOkHttp.get().postJson(context, url, data, new GsonResponseHandler<ApiResponse<ShiftRoom>>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                listener.onFailure("" + statusCode, error_msg);
            }

            @Override
            public void onSuccess(int statusCode, ApiResponse<ShiftRoom> response) {
                LogUtils.e(TAG, "response:" + response.getResult().toString());
                if (response != null) {
                    if (response.getCode().equals("A00006")) {
                        listener.onSuccess(response.getResult());
                    } else {
                        listener.onFailure(response.getCode(), response.getMsg());
                    }
                } else {
                    listener.onFailure("", "链接服务器异常");
                }
            }
        }, new MyOkHttp.CallBackActivity() {
            @Override
            public void onCallBack() {
                listener.onLogin();
            }
        }, false);
    }


    /**
     * 排序
     *
     * @param a
     * @return
     */
    public static ArrayList<String> getSortAsc(ArrayList<String> a) {
        String temp = "";
        int i;
        for (i = 0; i < a.size(); i++) {
            for (int j = a.size() - 1; j > i; j--) {
                if (a.get(j).compareTo(a.get(j - 1)) < 0) {
                    temp = a.get(j);
                    a.set(j, a.get(j - 1));
                    a.set(j - 1, temp);
                }
            }
        }
        return a;
    }


    /**
     * 获取到JSON字串
     *
     * @param cmd
     * @param paramsMap
     * @return
     */
    public static String getJsonStr(String cmd, Map<String, Object> paramsMap, String sign, String key) {
        JSONObject jsonParams = null;

        ArrayList<String> keys = new ArrayList<String>();
        for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext(); ) {
            keys.add(it.next());
        }
        String tmp = "";
        keys = getSortAsc(keys);
        for (int i = 0; i < keys.size(); i++) {
            Object obj = paramsMap.get(keys.get(i));
            if (obj != null) {
                tmp = tmp + obj.toString();
            } else {
                LogUtils.e("getJsonStr", "getSortAsc obj is null");
            }
        }
        LogUtils.e(tmp);
        String verify = MD5Util.md5(tmp + key);
        paramsMap.put(sign, verify);


        Map<String, Object> final_params = new HashMap<String, Object>();
        final_params.put("cmd", cmd);
        final_params.put("params", paramsMap);

        jsonParams = new JSONObject(final_params);

        return jsonParams.toString();
    }
}
