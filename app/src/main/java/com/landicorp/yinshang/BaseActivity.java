package com.landicorp.yinshang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;


import com.google.gson.Gson;
import com.landicorp.yinshang.data.ApiService;
import com.landicorp.yinshang.data.model.SystemInfoBean;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.Util;
import com.landicorp.yinshang.view.ConstValue;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.ums.AppHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by u on 2017/1/9.
 */

public class BaseActivity extends AppCompatActivity {

    public ApiService apiService;
    public Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏toolbar
        //activity添加到栈中
        Util.addToStack(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ConstValue.SCREEN_WIDTH = dm.widthPixels;
        ConstValue.SCREEN_HEIGHT = dm.heightPixels ;
        ConstValue.SCREEN_DENSITY = dm.density;
        Util.addToStack(this);
        if(isRoot()){
            try {
                DataInputStream stream = terminal("ping -c 2 www.pocketdigi.com");
                //其实ping并不需要root权限 ,这里是ping 2次后才停止，所以启动后需要一点时间才会有显示
                //你可以自己换成需要root权限的命令试试
                String temp;
                while((temp=stream.readLine())!=null){
                    System.out.println(temp);
                    //循环输出返回值
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public DataInputStream terminal(String command) throws Exception
    {
        Process process = Runtime.getRuntime().exec("su");
        //执行到这，Superuser会跳出来，选择是否允许获取最高权限
        OutputStream outstream = process.getOutputStream();
        DataOutputStream DOPS = new DataOutputStream(outstream);
        InputStream instream = process.getInputStream();
        DataInputStream DIPS = new DataInputStream(instream);
        String temp = command + "\n";
        //加回车
        DOPS.writeBytes(temp);
        //执行
        DOPS.flush();
        //刷新，确保都发送到outputstream
        DOPS.writeBytes("exit\n");
        //退出
        DOPS.flush();
        process.waitFor();
        return DIPS;
    }

    /**
     * 判断当前手机是否有ROOT权限
     * @return
     */
    public boolean isRoot(){
        boolean bool = false;

        try{
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())){
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {

        }
        return bool;
    }

    public RequestBody getBody(String body) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.popFromStack(this);
    }
}
