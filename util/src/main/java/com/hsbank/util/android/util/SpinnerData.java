package com.hsbank.util.android.util;

public class SpinnerData {
	String text;
	String value;
	
	public SpinnerData(String text, String value) {
		this.text = text;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
