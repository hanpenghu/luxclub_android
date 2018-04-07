package com.hsbank.util.android.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.hsbank.util.java.type.NumberUtil;

import java.lang.ref.WeakReference;

/**
 * 监听器：点击按钮实现 “ - 1” 功能
 * 2016-04-11
 */
public class BtJianClickListener implements OnClickListener {
	WeakReference<EditText> _wr = null;
	
	public BtJianClickListener(EditText _targetET){
		_wr = new WeakReference<EditText>(_targetET);
	}
	
	@Override
	public void onClick(View v) {
		int count;
		EditText targetET = _wr.get();
		if (targetET.getText() == null) {
			targetET.setText("0");
		} else {
			count = NumberUtil.toInt(targetET.getText().toString(), 0);
			count -= 1;
			count = count < 0 ? 0 : count;
			targetET.setText(String.valueOf(count));
		}
	}
}
