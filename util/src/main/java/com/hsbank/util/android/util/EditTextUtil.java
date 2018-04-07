package com.hsbank.util.android.util;

import android.view.View;
import android.widget.EditText;

import com.hsbank.util.java.type.StringUtil;

/**
 * EditText_公共类
 * @author Arthur.Xie
 * 2015-02-04
 */
public class EditTextUtil {
	
	public static String getText(View view, int id) {
		EditText et = (EditText)view.findViewById(id);
		return getText(et);
	}
	
	public static String getText(EditText et) {
		return et.getText() == null ? "" : StringUtil.dealString(et.getText().toString());
	}
	
}
