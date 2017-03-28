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

public class InputMemberDialog extends Dialog {

    public interface OnInputMemberDialogListener {
        public void reqMemberInfo(String member, String password);
    }

    private Context context;
    private OnInputMemberDialogListener customDialogListener;
    private CheckBox ipNormalUseCkb;
    private ClearEditText edtMemberNo;
    private ClearEditText edt_memberPassword;
    private boolean isInputPassword;//是否输入密码
    private boolean isInputName;//是否输入用户名

    public InputMemberDialog(Context context, OnInputMemberDialogListener customDialogListener, boolean isInputName, boolean isInputPassword) {
        super(context);
        this.context = context;
        this.isInputPassword = isInputPassword;
        this.isInputName = isInputName;
        this.customDialogListener = customDialogListener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_member);
        edtMemberNo = (ClearEditText) findViewById(R.id.edt_memberNo);
        Button confirmBtn = (Button) findViewById(R.id.dialog_confirm_btn);
        Button cancelBtn = (Button) findViewById(R.id.dialog_cancel_btn);
        edt_memberPassword = (ClearEditText) findViewById(R.id.edt_memberPassword);
        if (isInputPassword) {
            edt_memberPassword.setVisibility(View.VISIBLE);
        } else {
            edt_memberPassword.setVisibility(View.GONE);
        }
        if(isInputName) {
            edtMemberNo.setVisibility(View.VISIBLE);
        } else {
            edtMemberNo.setVisibility(View.GONE);
        }
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
                if(isInputName && "".equals(edtMemberNo.getText().toString())) {
                    MyToast.showText("请输入会员名");
                    return;
                }
                if(isInputPassword && "".equals(edt_memberPassword.getText().toString())) {
                    MyToast.showText("请输入密码");
                    return;
                }
                customDialogListener.reqMemberInfo(edtMemberNo.getText().toString(), edt_memberPassword.getText().toString());
                InputMemberDialog.this.dismiss();
            } else if(v.getId() == R.id.dialog_cancel_btn) {
                InputMemberDialog.this.dismiss();
            }
        }
    };
}
