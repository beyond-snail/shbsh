package com.landicorp.nuoxin;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.landicorp.nuoxin.activity.LoginActivity;

public class MainActivity extends BaseActivity {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog","请求结果为-->" + val);

            Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }
    };
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            //
            // TODO: http request.
            //
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value","请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.postDelayed(runnable, 3000);
//        new Thread(runnable).start();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                /* Create an Intent that will start the Main WordPress Activity. */
//                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
//                MainActivity.this.startActivity(mainIntent);
//                MainActivity.this.finish();
//            }
//        }, 3000);
    }

}
