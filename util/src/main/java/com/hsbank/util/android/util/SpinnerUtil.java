package com.hsbank.util.android.util;

import android.view.View;
import android.widget.Spinner;

import com.hsbank.util.java.type.StringUtil;

/**
 * Spinner_公共类
 * @author Arthur.Xie
 * 2015-02-04
 */
public class SpinnerUtil {
	
	public static String getSelectedValue(View view, int id) {
		Spinner s = (Spinner)view.findViewById(id);
		return getSelectedValue(s);
	}
	
	public static String getSelectedValue(Spinner s) {
		return s.getSelectedItem() == null ? "" : StringUtil.dealString(((SpinnerData) s.getSelectedItem()).getValue());
	}
	
}
