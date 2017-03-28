package com.landicorp.yinshang.zfbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.landicorp.yinshang.BaseActivity;
import com.landicorp.yinshang.R;
import com.landicorp.yinshang.myokhttp.util.LogUtils;
import com.landicorp.yinshang.utils.Constant;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.utils.SharePreferenceHelper;
import com.landicorp.yinshang.utils.StringUtils;
import com.landicorp.yinshang.utils.Util;
import com.landicorp.yinshang.zfbj.core.SbsAction;
import com.landicorp.yinshang.zfbj.moudle.ShiftRoom;
import com.landicorp.yinshang.zfbj.myInterface.ActionCallbackListener;
import com.landicorp.yinshang.zfbj.utils.SPUtils;

import static com.landicorp.yinshang.zfbj.common.CommonFunc.startAction;


public class ShiftRoomActivity extends BaseActivity  {

    private LinearLayout btnShitRoom;
    private LinearLayout btnShitRoomDay;
    private LinearLayout btnShitRoomRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnShitRoom = (LinearLayout) findViewById(R.id.id_ll_shiftroom);
        btnShitRoomDay = (LinearLayout) findViewById(R.id.id_ll_shiftroom_day);
        btnShitRoomRecord = (LinearLayout) findViewById(R.id.id_ll_shiftroom_record);


        btnShitRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShiftRoom();
            }
        });

        btnShitRoomDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShiftRoomDay();
            }
        });

        btnShitRoomRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShiftRoomActivity.this,
                        ShiftRoomRecordActivity.class));
            }
        });

    }




    /**
     * 班接
     */
    private void getShiftRoom() {

        int sid = SharePreferenceHelper.getInstance(this).getInt("SID");
        final long start_time = StringUtils.getdate2TimeStamp((String) SPUtils.get(this, Constant.SHIFT_ROOM_TIME, Constant.DEFAULT_SHIFT_ROOM_TIME));
        LogUtils.e("start_time=", (String) SPUtils.get(this, Constant.SHIFT_ROOM_TIME, Constant.DEFAULT_SHIFT_ROOM_TIME));
        final long end_time = StringUtils.getdate2TimeStamp(StringUtils.getCurTime());
        LogUtils.e("end_time", StringUtils.getCurTime());


        Util.showProgress(this);
        SbsAction action = new SbsAction();
        action.shift_room(this, sid, start_time, end_time, new ActionCallbackListener<ShiftRoom>() {
            @Override
            public void onSuccess(final ShiftRoom data) {
                LogUtils.e("onSucess"+data.toString());
                Util.dismissProgress();

                Bundle bundle = new Bundle();
                bundle.putSerializable("ShiftRoom", data);
                bundle.putLong("start_time", start_time);
                bundle.putLong("end_time", end_time);
                bundle.putInt("type", Constant.PRINTER_SHIFT_ROOM);
                startAction(ShiftRoomActivity.this, ShiftRoomShowActivity.class, bundle, false);

            }

            @Override
            public void onFailure(String errorEvent, String message) {
                MyToast.showText(errorEvent+message);
                Util.dismissProgress();
            }

            @Override
            public void onFailurTimeOut(String s, String error_msg) {
                Util.dismissProgress();
            }

            @Override
            public void onLogin() {
                Util.dismissProgress();
            }
        });
    }

    /**
     * 班接
     */
    private void getShiftRoomDay() {

        int sid = SharePreferenceHelper.getInstance(this).getInt("SID");
        final long start_time = StringUtils.getdate2TimeStamp(StringUtils.formatTime(StringUtils.getCurDate()+"000000"));
        LogUtils.e("start_time=", StringUtils.formatTime(StringUtils.getCurDate()+"000000"));
        final long end_time = StringUtils.getdate2TimeStamp(StringUtils.getCurTime());
        LogUtils.e("end_time", StringUtils.getCurTime());

        Util.showProgress(this);
        SbsAction action = new SbsAction();
        action.shift_room(this, sid, start_time, end_time, new ActionCallbackListener<ShiftRoom>() {
            @Override
            public void onSuccess(final ShiftRoom data) {
                LogUtils.e("onSuccess"+data.toString());
                Util.dismissProgress();

                Bundle bundle = new Bundle();
                bundle.putSerializable("ShiftRoom", data);
                bundle.putLong("start_time", start_time);
                bundle.putLong("end_time", end_time);
                bundle.putInt("type", Constant.PRINTER_SHIFT_ROOM_DAY);
                startAction(ShiftRoomActivity.this, ShiftRoomShowActivity.class, bundle, false);

            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Util.dismissProgress();
                MyToast.showText(errorEvent+message);
            }

            @Override
            public void onFailurTimeOut(String s, String error_msg) {
                Util.dismissProgress();
            }

            @Override
            public void onLogin() {

            }
        });
    }


}
