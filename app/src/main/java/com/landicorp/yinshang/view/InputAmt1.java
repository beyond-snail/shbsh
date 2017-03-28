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
import com.landicorp.yinshang.utils.AmountUtil;


/*
 * 金额输入控件
 */
public class InputAmt1 extends FrameLayout implements OnClickListener {

	private int receiveAmount = 0;//输入金额
	private int payAmount = 0;//应付金额
	private Context parentContext;
	private EditText textView;
	private EditText textView00;
	private OnClickListener onPayClickListener;
	//本人新增
	private TextView txt_zhaoling;

	public void setOnPayClickListener(OnClickListener onPayClickListener) {
		this.onPayClickListener = onPayClickListener;

	}


	public InputAmt1(Context context) {
		super(context);
		parentContext = context;
	}

	public InputAmt1(Context context, AttributeSet attrs) {
		super(context, attrs);
		parentContext = context;
	}

	public InputAmt1(Context context, AttributeSet attrs, int defStyle) {
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
		this.findViewById(R.id.btn_back_delete).setOnClickListener(this);
		this.findViewById(R.id.btn_cancel).setOnClickListener(this);
		this.findViewById(R.id.btn_confirm).setOnClickListener(this);

		textView = Etext;
		textView.setText("¥ 0.00");
	}

	//add by eric 元 和 角分 用两个Edittext显示
	public void init(EditText Etext, TextView txt_zhaoling, int payAmount) {

		LayoutInflater.from(parentContext).inflate(R.layout.layout_input_amt1,
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
		this.findViewById(R.id.btn_back_delete).setOnClickListener(this);
		this.findViewById(R.id.btn_cancel).setOnClickListener(this);
		this.findViewById(R.id.btn_confirm).setOnClickListener(this);

		this.textView = Etext;
		this.txt_zhaoling = txt_zhaoling;
		this.payAmount = payAmount;

		textView.setText("¥ 0.00");
	}

	@Override
	public void onClick(final View v) {
		int id = v.getId();

		if (id == R.id.btn_0) {
			setTextAmount(0);
		} else if (id == R.id.btn_dot) {
//			changetext((char) 0x00);
		} else if (id == R.id.btn_1) {
			setTextAmount(1);
		} else if (id == R.id.btn_2) {
			setTextAmount(2);
		} else if (id == R.id.btn_3) {
			setTextAmount(3);
		} else if (id == R.id.btn_4) {
			setTextAmount(4);
		} else if (id == R.id.btn_5) {
			setTextAmount(5);
		} else if (id == R.id.btn_6) {
			setTextAmount(6);
		} else if (id == R.id.btn_7) {
			setTextAmount(7);
		} else if (id == R.id.btn_8) {
			setTextAmount(8);
		} else if (id == R.id.btn_9) {
			setTextAmount(9);
		} else if (id == R.id.btn_back_delete) {
			setAmountToKeyBack();
		} else if (id == R.id.btn_cancel) {
			receiveAmount=0;
			textView.setText("¥ 0.00");
		} else if (id == R.id.btn_confirm) {
			if (onPayClickListener != null)
				onPayClickListener.onClick(v);
		}
	}

	private void setTextAmount(int digital) {
		if (receiveAmount < 100000000) {
			receiveAmount = receiveAmount * 10 + digital;
			textView.setText("¥ " + returnAmoutString(receiveAmount));
			if(txt_zhaoling != null) {
				int zhangling = receiveAmount - payAmount;
				if(zhangling <= 0) {
					txt_zhaoling.setText("");
				} else {
					txt_zhaoling.setText("¥ " + returnAmoutString(zhangling));
				}
			}
		}
	}

	private void setAmountToKeyBack() {
		receiveAmount = receiveAmount / 10;
		textView.setText("¥ " + returnAmoutString(receiveAmount));

		if(txt_zhaoling != null) {
			int zhangling = receiveAmount - payAmount;
			if(zhangling <= 0) {
				txt_zhaoling.setText("");
			} else {
				txt_zhaoling.setText("¥ " + returnAmoutString(zhangling));
			}
		}
//		if (receiveAmount > 0) {
//			if (receiveAmount - amount > 0) {
//				tOddChange.setText(StringUtils.formatIntMoney(receiveAmount - amount) + "");
//			} else {
//				tOddChange.setText(StringUtils.formatIntMoney(0) + "");
//			}
//		} else {
//			tOddChange.setText(StringUtils.formatIntMoney(0) + "");
//		}
	}

	private String returnAmoutString(int amount) {
		String amt = String.valueOf(AmountUtil.divide((double)amount, (double)100,2));
		String[] arr = amt.split("\\.");
		if(arr[1].length() == 1) {
			amt = amt + "0";
		}
		return amt;
	}

	public int getAmount() {
		return receiveAmount;
	}

	public void resetAmount() {
		receiveAmount = 0;
		textView.setText("¥ 0.00");
		if(txt_zhaoling != null) {
			txt_zhaoling.setText("");
		}
	}

	/**
	 * Method Format string
	 *
	 * @param The
	 *            string to be format.
	 *
	 */
//	public static String fillZero(String formatString, int length) {
//		return fillString(formatString, length, '0', true);
//	}
//
//	public static String formatAmount(String amount, boolean separator) {
//		if (amount == null) {
//			amount = "";
//		}
//		if (amount.length() < 3) {
//			amount = fillZero(amount, 3);
//		}
//		StringBuffer s = new StringBuffer();
//		int strLen = amount.length();
//		for (int i = 1; i <= strLen; i++) {
//			s.insert(0, amount.charAt(strLen - i));
//			if (i == 2)
//				s.insert(0, '.');
//			if (i > 3 && ((i % 3) == 0)) {
//				if (separator) {
//					s.insert(1, ',');
//				}
//			}
//		}
//		return s.toString();
//	}
//
//	/**
//	 * prepare long value used as amount for display (implicit 2 decimals)
//	 *
//	 * @param amount
//	 *            value
//	 * @return formated field
//	 * @exception RuntimeException
//	 */
//	public static String formatAmount(long amount) {
//		return formatAmount("" + amount, false);
//	}



}
