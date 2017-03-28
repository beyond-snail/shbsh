package com.landicorp.nuoxin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.landicorp.nuoxin.BaseActivity;
import com.landicorp.nuoxin.R;
import com.landicorp.nuoxin.net.SocThread;
import com.landicorp.nuoxin.utils.MyLog;
import com.landicorp.nuoxin.views.ClearEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lixin on 2016/12/9.
 */

public class LoginActivity extends BaseActivity {
    private String TAG = "===Client===";
    private String TAG_SEND = "===Send===";
    private TextView tv1 = null;
    private Handler receiveHandler;//接收服务器数据的handler
    private Handler sendHandler;//发送服务器的handler（可以不做处理）
    private ClearEditText usernameEdt;
    private ClearEditText passwordEdt;
    private Button login_btn;
    SocThread socketThread;
    private Context ctx;
    StringBuffer sb = new StringBuffer();//test data
//    boolean isRun = true;
//    Socket socket;
//    PrintWriter out;
//    BufferedReader in;
//    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = LoginActivity.this;
        initViews();
        receiveHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    MyLog.i(TAG, "connHandler接收到msg=" + msg.what);
                    if (msg.obj != null) {
                        String s = msg.obj.toString();
                        if (s.trim().length() > 0) {
                            MyLog.i(TAG, "connHandler接收到obj=" + s);
                            MyLog.i(TAG, "开始更新UI");
                            tv1.append("Server:" + s);
                            MyLog.i(TAG, "更新UI完毕");
                        } else {
                            tv1.append("没有连接到服务器");
                            Log.i(TAG, "没有数据返回不更新");
                        }
                    }
                } catch (Exception e) {
                    MyLog.i(TAG, "加载过程出现异常");
                    e.printStackTrace();
                }
            }
        };
        sendHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    MyLog.i(TAG_SEND, "sendHandler收到msg.what=" + msg.what);
                    String s = msg.obj.toString();
                    if (msg.what == 1) {
                        tv1.append("\n ME: " + s + "      发送成功");
                    } else {
                        tv1.append("\n ME: " + s + "     发送失败");
                    }
                } catch (Exception e) {
                    MyLog.i(TAG_SEND, "加载过程出现异常");
                    e.printStackTrace();
                }
            }
        };
        testData();
        startSocket();
    }

    private void startSocket() {
        socketThread = new SocThread(receiveHandler, sendHandler, ctx);
        socketThread.start();
    }

    private void initViews() {
        tv1 = (TextView) findViewById(R.id.tv1);
        login_btn = (Button) findViewById(R.id.login_btn);
        usernameEdt = (ClearEditText) findViewById(R.id.login_username_edt);
        passwordEdt = (ClearEditText) findViewById(R.id.login_password_edt);
        login_btn.setOnClickListener(clickListener);
    }

    /**
     * 测试数据
     */
    private void testData() {
        sb.append("<? xml version='1.0'  encoding='UTF-8' ?>");
        sb.append("<transaction>");
        sb.append("<transaction_header>");
        sb.append("<version>1.0</version>");
        sb.append("<transtype>P001</transtype>");
        sb.append("<userCode>0720001</userCode>");
        sb.append("<terminal>12345678</terminal>");
        sb.append("<request_time>20120913140101</request_time>");
        sb.append("<mac>0123456789ABCDEF0123456789ABCDEF </mac>");
        sb.append("<transaction_body>");
        sb.append("<userPassword>0123456789ABCDEF0123456789ABCDEF</userPassword>");
        sb.append("<transaction>");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client, menu);
        return true;
    }

    private void stopSocket() {
        socketThread.isRun = false;
        socketThread.close();
        socketThread = null;
        MyLog.i(TAG, "Socket已终止");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "start onStart~~~");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "start onRestart~~~");
        startSocket();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "start onResume~~~");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "start onPause~~~");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "start onStop~~~");
        stopSocket();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "start onDestroy~~~");

    }
    
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_btn:
                    // 发送数据
                    MyLog.i(TAG, "准备发送数据");
                    socketThread.send(sb.toString());
                    break;
            }
        }
    };
}
