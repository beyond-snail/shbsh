package com.landicorp.yinshang;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.landicorp.yinshang.adapter.TransRecordAdapter;
import com.landicorp.yinshang.data.ReqCallBack;
import com.landicorp.yinshang.data.response.UndoResponse;
import com.landicorp.yinshang.db.DBManager;
import com.landicorp.yinshang.db.TransactionReqSubBean;
import com.landicorp.yinshang.db.TransactionReqSubBeanDao;
import com.landicorp.yinshang.utils.MyToast;
import com.landicorp.yinshang.view.ClearEditText;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by u on 2017/1/9.交易记录界面
 */
public class TransRecordActivity extends BaseActivity implements ReqCallBack<UndoResponse> {

    private LinearLayout lv_left;
    private ListView listview_trans_record;
    private ClearEditText check_stock_search_edt;
    private TransRecordAdapter recordAdapter;
    private List<TransactionReqSubBean> transList;
    private List<TransactionReqSubBean> transSearchList = new ArrayList<TransactionReqSubBean>();
    private Date startDate;
    private Date endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_record);
        startDate = dayStartDate(new Date());
        endDate = dayEndDate(new Date());
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
        Query query = dao.queryBuilder().where(
                TransactionReqSubBeanDao.Properties.T.between(startDate.getTime()/1000,endDate.getTime()/1000)).orderDesc(TransactionReqSubBeanDao.Properties.T)
                .build();
        transList = query.list();
        check_stock_search_edt.setText("");
    }

    private Date dayStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -7);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();

    }

    private Date dayEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 59);
        return c.getTime();

    }

    private void init() {
        listview_trans_record = (ListView) findViewById(R.id.listview_trans_record);
        lv_left = (LinearLayout) findViewById(R.id.lv_left);
        check_stock_search_edt = (ClearEditText) findViewById(R.id.check_stock_search_edt);
        TransactionReqSubBeanDao dao = DBManager.getInstance().getSession().getTransactionReqSubBeanDao();
                                Query query = dao.queryBuilder().where(
                                TransactionReqSubBeanDao.Properties.T.between(startDate.getTime()/1000,endDate.getTime()/1000)).orderDesc(TransactionReqSubBeanDao.Properties.T)
                                .build();
        transList = query.list();

//        transList = dao.loadAll();
        transSearchList.addAll(transList);
        recordAdapter = new TransRecordAdapter(this, transSearchList);
        listview_trans_record.setAdapter(recordAdapter);
        lv_left.setOnClickListener(clickListener);
        check_stock_search_edt.addTextChangedListener(textWatcher);
        listview_trans_record.setOnItemClickListener(itemClickListener);
        check_stock_search_edt.setOnKeyListener(onKeyListener);
    }

    @Override
    public void onReqSuccess(UndoResponse result) {
        MyToast.showText(result.code+ "-----------" );
    }

    @Override
    public void onReqFailed(String errorMsg) {
        MyToast.showText("请求失败" );
    }

    View.OnKeyListener onKeyListener = new View.OnKeyListener() {// 输入完后按键盘上的搜索键【回车键改为了搜索键】

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {// 修改回车键功能
                if(check_stock_search_edt.getText() == null || check_stock_search_edt.getText().toString().equals("")) {
                    return false;
                }
                recordAdapter.notifyDataSetChanged();
            }
            return false;
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            transSearchList.clear();
            if (s != null && !s.toString().trim().equals("")) {
                if(transList != null) {
                    for (TransactionReqSubBean bean : transList) {
                        if(bean.getClientOrderNo().startsWith(s.toString())) {
                            transSearchList.add(bean);
                        }
                    }
                }
            } else {
                transSearchList.addAll(transList);
            }
            recordAdapter.notifyDataSetChanged();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
        }
    };

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Bundle bundle = new Bundle();
            bundle.putString("clientOrderNo", transSearchList.get(position).getClientOrderNo());
            Intent intent = new Intent(TransRecordActivity.this, TransDetailActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,1);
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lv_left:
                    finish();
                    break;

            }
        }
    };
}
