package com.landicorp.yinshang.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.landicorp.yinshang.R;
import com.landicorp.yinshang.utils.MyToast;


/**
 * Created by u on 2017/1/10.
 */

public class InputTraceNumDialog extends Dialog {

    public interface OnInputTraceDialogListener {
        public void trace(String trace);
    }

    private Context context;
    private OnInputTraceDialogListener customDialogListener;
    private CheckBox ipNormalUseCkb;
    private ClearEditText edtMemberNo;

    public InputTraceNumDialog(Context context, OnInputTraceDialogListener customDialogListener) {
        super(context);
        this.context = context;
        this.customDialogListener = customDialogListener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_tracenum);
//		setTitle(name);
        edtMemberNo = (ClearEditText) findViewById(R.id.edt_memberNo);
        edtMemberNo.setHint("输入原凭证号");
        Button confirmBtn = (Button) findViewById(R.id.dialog_confirm_btn);
        Button cancelBtn = (Button) findViewById(R.id.dialog_cancel_btn);
        confirmBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
        initConfig();
    }

    private void initConfig() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ConstValue.SCREEN_WIDTH * 0.96);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.dialog_confirm_btn) {
                if("".equals(edtMemberNo.getText().toString())) {
                    MyToast.showText("请输入原凭证号");
                    return;
                }
                customDialogListener.trace(edtMemberNo.getText().toString());
                InputTraceNumDialog.this.dismiss();
            } else if(v.getId() == R.id.dialog_cancel_btn) {
                InputTraceNumDialog.this.dismiss();
            }
        }
    };
}
