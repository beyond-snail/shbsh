package com.landicorp.yinshang.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.landicorp.yinshang.R;


/*
 * 金额输入控件
 */
public class InputAmt extends FrameLayout implements OnClickListener {
	private Context parentContext;
	public String text = "";
	private EditText textView;
	private EditText textView00;
	private LinearLayout linearLayout;
	private int lenth = 10;
	public boolean isPrice = false;
	private OnClickListener onPayClickListener;
	//本人新增
	private TextView txt_zhaoling;

	public void setOnPayClickListener(OnClickListener onPayClickListener) {
		this.onPayClickListener = onPayClickListener;

	}


	public InputAmt(Context context) {
		super(context);
		parentContext = context;
	}

	public InputAmt(Context context, AttributeSet attrs) {
		super(context, attrs);
		parentContext = context;
	}

	public InputAmt(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		parentContext = context;
	}

	public void init(EditText Etext) {

		LayoutInflater.from(parentContext).inflate(R.layout.layout_input_amt,
				this);

		this.findViewById(R.id.btn_dot).setOnClickListener(this);
		this.findViewById(R.id.btn_0).setOnClickListener(this);
		this.findViewById(R.id.btn_1).setOnClickListener(this);
		this.findViewById(R.id.btn_2).setOnClickListener(this);
		this.findViewById(R.id.btn_3).setOnClickListener(this);
		this.findViewById(R.id.btn_4).setOnClickListener(this);
		this.findViewById(R.id.btn_5).setOnClickListener(this);
		this.findViewById(R.id.btn_6).setOnClickListener(this);
		this.findViewById(R.id.btn_7).setOnClickListener(this);
		this.findViewById(R.id.btn_8).setOnClickListener(this);
		this.findViewById(R.id.btn_9).setOnClickListener(this);
//		this.findViewById(R.id.btn_back).setOnClickListener(this);
		this.findViewById(R.id.btn_cancel).setOnClickListener(this);
		this.findViewById(R.id.btn_confirm).setOnClickListener(this);

		textView = Etext;
	}

	//add by eric 元 和 角分 用两个Edittext显示
	public void init(EditText Etext,EditText Etext00,LinearLayout linearLayout) {

		LayoutInflater.from(parentContext).inflate(R.layout.layout_input_amt,
				this);

		this.findViewById(R.id.btn_dot).setOnClickListener(this);
		this.findViewById(R.id.btn_0).setOnClickListener(this);
		this.findViewById(R.id.btn_1).setOnClickListener(this);
		this.findViewById(R.id.btn_2).setOnClickListener(this);
		this.findViewById(R.id.btn_3).setOnClickListener(this);
		this.findViewById(R.id.btn_4).setOnClickListener(this);
		this.findViewById(R.id.btn_5).setOnClickListener(this);
		this.findViewById(R.id.btn_6).setOnClickListener(this);
		this.findViewById(R.id.btn_7).setOnClickListener(this);
		this.findViewById(R.id.btn_8).setOnClickListener(this);
		this.findViewById(R.id.btn_9).setOnClickListener(this);
//		this.findViewById(R.id.btn_back).setOnClickListener(this);
		this.findViewById(R.id.btn_cancel).setOnClickListener(this);
		this.findViewById(R.id.btn_confirm).setOnClickListener(this);

		this.textView = Etext;
		this.textView00 = Etext00;
		this.linearLayout = linearLayout;
	}

	//add by eric 元 和 角分 用两个Edittext显示
	public void init(EditText Etext,EditText Etext00,LinearLayout linearLayout, TextView txt_zhaoling) {

		LayoutInflater.from(parentContext).inflate(R.layout.layout_input_amt,
				this);

		this.findViewById(R.id.btn_dot).setOnClickListener(this);
		this.findViewById(R.id.btn_0).setOnClickListener(this);
		this.findViewById(R.id.btn_1).setOnClickListener(this);
		this.findViewById(R.id.btn_2).setOnClickListener(this);
		this.findViewById(R.id.btn_3).setOnClickListener(this);
		this.findViewById(R.id.btn_4).setOnClickListener(this);
		this.findViewById(R.id.btn_5).setOnClickListener(this);
		this.findViewById(R.id.btn_6).setOnClickListener(this);
		this.findViewById(R.id.btn_7).setOnClickListener(this);
		this.findViewById(R.id.btn_8).setOnClickListener(this);
		this.findViewById(R.id.btn_9).setOnClickListener(this);
//		this.findViewById(R.id.btn_back).setOnClickListener(this);
		this.findViewById(R.id.btn_cancel).setOnClickListener(this);
		this.findViewById(R.id.btn_confirm).setOnClickListener(this);

		this.textView = Etext;
		this.textView00 = Etext00;
		this.linearLayout = linearLayout;
		this.txt_zhaoling = txt_zhaoling;
	}

	public void setAttri(boolean isprice, int len) {
		this.isPrice = isprice;
		lenth = len;
		if (isPrice) {
			textView.setText("0");
			if(textView00 != null)
				textView00.setText(".00");
		}else{
			if(textView00 != null)
				textView00.setVisibility(View.GONE);
		}
	}

	public void changetext(char w) {
		if(txt_zhaoling != null)
			txt_zhaoling.setText("");
		// 退格
		if (w == 0x0f) {
			if (text.length() >= 1) {
				text = text.substring(0, text.length() - 1);
			}
		}

		if (w == 0xff) {
			if (text.length() >= 1) {
				text = "";
			}
		}

		if (w == 0x00) {
			if (text.length() >= lenth) {
				return;
			}
			if (isPrice) {
				if (text.length() != 0 & text.length() < lenth - 1)
					text += "00";
				if (text.length() == lenth - 1) {
					text += "0";
				}
			} else {
				text += "00";
			}
		}

		if (w >= '0' && w <= '9') {
			// 最大长度
			if (text.length() >= lenth) {
				return;
			}
			if (isPrice) {
				// 金额不为0
				if (w != '0' || text.length() != 0) {
					text += w;
				}
			} else {
				text += w;
			}
		}

		if (isPrice) {
			String sa = new String();
			String sa00 = new String();
			if (text.length() > 2) {
				sa = text.substring(0, text.length() - 2);
				//sa += ".";
				sa00 = "."+text.substring(text.length() - 2);
			} else if (text.length() == 2) {
				sa = "0";
				sa00 = "."+text;
			} else if (text.length() == 1) {
				sa = "0";
				sa00 = ".0"+text;
			} else {
				sa = "0";
				sa00 = ".00";
			}

			Log.d("eric","sa="+sa+"   sa00="+sa00);
//			textView.invalidate();
			textView.setText(sa);
			if(textView00 != null){
//				textView00.invalidate();
				textView00.setText(sa00);
				linearLayout.setBackgroundColor(getResources().getColor(R.color.input_amt_background));
				linearLayout.invalidate();
			}

		} else {
			textView.setText(text);
		}
	}

	@Override
	public void onClick(final View v) {
		int id = v.getId();

		if (id == R.id.btn_0) {
			changetext('0');
		} else if (id == R.id.btn_dot) {
//			changetext((char) 0x00);
		} else if (id == R.id.btn_1) {
			changetext('1');
		} else if (id == R.id.btn_2) {
			changetext('2');
		} else if (id == R.id.btn_3) {
			changetext('3');
		} else if (id == R.id.btn_4) {
			changetext('4');
		} else if (id == R.id.btn_5) {
			changetext('5');
		} else if (id == R.id.btn_6) {
			changetext('6');
		} else if (id == R.id.btn_7) {
			changetext('7');
		} else if (id == R.id.btn_8) {
			changetext('8');
		} else if (id == R.id.btn_9) {
			changetext('9');
		} else if (id == R.id.btn_cancel) {
			changetext((char) 0xff);
		} else if (id == R.id.btn_confirm) {
			if (onPayClickListener != null)
				onPayClickListener.onClick(v);
		}
	}
	/**
	 * 获取金额 **.**
	 * @return
	 */
	public String getAmount() {

		//return textView.getText().toString().trim();
		if(isPrice==true){
			if (text.length() > 2) {
				if(!text.contains("."))
					text=text.substring(0, text.length() - 2)+"."+text.substring(text.length() - 2);
			} else if (text.length() == 2) {
				text="0."+text;
			} else if (text.length() == 1) {
				text="0.0"+text;
			} else {
				text="0.00";
			}
			Log.d("eric","-----------------------------------getAmount:   text="+text);
			return text.toString().trim();
		}else{
			return textView.getText().toString().trim();
		}
	}

	public void Reset_InputAmt() {//清除之前的数值

		if (text.length() >= 1) {
			text = "";
		}
	}


}
