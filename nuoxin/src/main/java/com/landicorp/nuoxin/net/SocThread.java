package com.landicorp.nuoxin.net;

/**
 * Created by u on 2016/12/14.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.landicorp.nuoxin.utils.MyLog;

public class SocThread extends Thread {

    private static final String SHAER_IP = "share_ip";
    private static final String SHAER_PORT = "share_port";
    private String ip = "116.236.224.54";
    private int port = 21102;
    private String TAG = "socket thread";
    private int timeout = 10000;

    public Socket client = null;
    private PrintWriter out;
    private BufferedReader in;
    public boolean isRun = true;
    Handler receiveHandler;//接收服务器的处理
    Handler sendHandler;//发送服务器的处理
    Context ctx;
    private String TAG1 = "===Send===";
    SharedPreferences sp;

    public SocThread(Handler receiveHandler, Handler sendHandler, Context context) {
        receiveHandler = receiveHandler;
        sendHandler = sendHandler;
        ctx = context;
        MyLog.i(TAG, "创建线程socket");
    }

    /**
     * 连接socket服务器
     */
    public void conn() {
        try {
//            initdate();
            Log.i(TAG, "连接中……");
            client = new Socket(ip, port);
            client.setSoTimeout(timeout);// 设置阻塞时间
            MyLog.i(TAG, "连接成功");
            in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    client.getOutputStream())), true);
            MyLog.i(TAG, "输入输出流获取成功");
        } catch (UnknownHostException e) {
            MyLog.i(TAG, "连接错误UnknownHostException 重新获取");
            e.printStackTrace();
            conn();
        } catch (IOException e) {
            MyLog.i(TAG, "连接服务器io错误");
            e.printStackTrace();
        } catch (Exception e) {
            MyLog.i(TAG, "连接服务器错误Exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initdate() {
        sp = ctx.getSharedPreferences("SP", ctx.MODE_PRIVATE);
        ip = sp.getString(SHAER_IP, ip);
        port = Integer.parseInt(sp.getString(SHAER_PORT, String.valueOf(port)));
        MyLog.i(TAG, "获取到ip端口:" + ip + ";" + port);
    }

    /**
     * 实时接受数据
     */
    @Override
    public void run() {
        MyLog.i(TAG, "线程socket开始运行");
        conn();
        MyLog.i(TAG, "1.run开始");
        String line = "";
        while (isRun) {
            try {
                if (client != null) {
                    MyLog.i(TAG, "2.检测数据");
                    while ((line = in.readLine()) != null) {
                        MyLog.i(TAG, "3.getdata" + line + " len=" + line.length());
                        MyLog.i(TAG, "4.start set Message");
                        Message msg = receiveHandler.obtainMessage();
                        msg.obj = line;
                        receiveHandler.sendMessage(msg);// 结果返回给UI处理
                        MyLog.i(TAG1, "5.send to handler");
                    }

                } else {
                    MyLog.i(TAG, "没有可用连接");
                    conn();
                }
            } catch (Exception e) {
                MyLog.i(TAG, "数据接收错误" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送数据
     *
     * @param mess
     */
    public void send(String mess) {
        try {
            if (client != null) {
                MyLog.i(TAG1, "发送" + mess + "至"
                        + client.getInetAddress().getHostAddress() + ":"
                        + String.valueOf(client.getPort()));
                out.println(mess);
                out.flush();
                MyLog.i(TAG1, "发送成功");
                Message msg = sendHandler.obtainMessage();
                msg.obj = mess;
                msg.what = 1;
                sendHandler.sendMessage(msg);// 结果返回给UI处理
            } else {
                MyLog.i(TAG, "client 不存在");
                Message msg = sendHandler.obtainMessage();
                msg.obj = mess;
                msg.what = 0;
                sendHandler.sendMessage(msg);// 结果返回给UI处理
                MyLog.i(TAG, "连接不存在重新连接");
                conn();
            }

        } catch (Exception e) {
            MyLog.i(TAG1, "send error");
            e.printStackTrace();
        } finally {
            MyLog.i(TAG1, "发送完毕");

        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            if (client != null) {
                MyLog.i(TAG, "close in");
                in.close();
                MyLog.i(TAG, "close out");
                out.close();
                MyLog.i(TAG, "close client");
                client.close();
            }
        } catch (Exception e) {
            MyLog.i(TAG, "close err");
            e.printStackTrace();
        }

    }
}
