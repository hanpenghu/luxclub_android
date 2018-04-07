package com.hsbank.luxclub.mywidget.setting_item.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.setting_item.entity.SettingData;

public class SwitchItemView extends FrameLayout {

	private LayoutInflater mInflater = null;

	private LinearLayout mItemViewContainer = null;

	private TextView mTitle = null;

	private SwitchView mSwitch = null;
	private ImageView mDrawable = null;

	private View mItemView = null;
	private onSwitchItemChangedListener mChangedListener = null;

	public SwitchItemView(Context context) {
		super(context);
		init(context);
	}

	public SwitchItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		readAttrs(context, attrs);
	}

	private void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mItemView = mInflater.inflate(R.layout.setting_view_switch_item, null);
		addView(mItemView);

		mTitle = (TextView) mItemView.findViewById(R.id.setting_view_switch_item_title);
		mDrawable = (ImageView) mItemView.findViewById(R.id.setting_view_switch_item_icon);
		mSwitch = (SwitchView) mItemView.findViewById(R.id.setting_view_switch_item_switch);
		mItemViewContainer = (LinearLayout) mItemView.findViewById(R.id.setting_view_switch_item_container);

		mItemViewContainer.setClickable(false);

		mSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
			@Override
			public void toggleToOn(View view) {
				if (null != mChangedListener) {
					mChangedListener.onSwitchItemChanged(true);
				}
			}

			@Override
			public void toggleToOff(View view) {
				if (null != mChangedListener) {
					mChangedListener.onSwitchItemChanged(false);
				}
			}
		});
//		mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if (null != mChangedListener) {
//					mChangedListener.onSwitchItemChanged(isChecked);
//				}
//			}
//		});
	}

	private void readAttrs(Context context, AttributeSet attrs) {
		if (null != attrs) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewItem);

			if (a.hasValue(R.styleable.SettingViewItem__background)) {
				Drawable drawable = a.getDrawable(R.styleable.SettingViewItem__background);
				if (null != drawable) {
					mItemViewContainer.setBackgroundDrawable(drawable);
				} else {
					mItemViewContainer.setBackgroundResource(R.drawable.setting_view_item_selector);
				}
			}

			if (a.hasValue(R.styleable.SettingViewItem_drawable)) {
				Drawable drawable = a.getDrawable(R.styleable.SettingViewItem_drawable);
				if (null != drawable) {
					mDrawable.setImageDrawable(drawable);
				} else {
					mDrawable.setVisibility(View.GONE);
				}
			} else {
				mDrawable.setVisibility(View.GONE);
			}

			if (a.hasValue(R.styleable.SettingViewItem__title)) {
				String title = a.getString(R.styleable.SettingViewItem__title);
				if (!TextUtils.isEmpty(title)) {
					mTitle.setText(title);
				}
			}

			if (a.hasValue(R.styleable.SettingViewItem_titleColor)) {
				ColorStateList colors = a.getColorStateList(R.styleable.SettingViewItem_titleColor);
				if (null != colors) {
					mTitle.setTextColor(colors);
				}
			}

			if (a.hasValue(R.styleable.SettingViewItem_titleSize)) {
				int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.SettingViewItem_titleSize, 16), getResources()
						.getDisplayMetrics());
				mTitle.setTextSize(textSize);
			}

			if (a.hasValue(R.styleable.SettingViewItem_clickable)) {
				mItemViewContainer.setClickable(a.getBoolean(R.styleable.SettingViewItem_clickable, false));
			} else {
				mItemViewContainer.setClickable(true);
			}

			if (a.hasValue(R.styleable.SettingViewItem_checked)) {
				mSwitch.setOpened(false);
//				mSwitch.setChecked(a.getBoolean(R.styleable.SettingViewItem_checked, false));
			}

			a.recycle();
		}
	}

	public void fillData(SettingData data) {
		if (null != data) {
			if (!TextUtils.isEmpty(data.getTitle())) {
				mTitle.setText(data.getTitle());
			} else {
				mTitle.setVisibility(View.GONE);
			}

			if (null != data.getDrawable()) {
				mDrawable.setImageDrawable(data.getDrawable());
			} else {
				mDrawable.setVisibility(View.GONE);
			}

			if (null != data.getBackground()) {
				mItemViewContainer.setBackgroundDrawable(data.getBackground());
			} else {
				mItemViewContainer.setBackgroundResource(R.drawable.setting_view_item_selector);
			}

			mSwitch.setOpened(data.isChecked());
//			mSwitch.setChecked(data.isChecked());

			if (data.getTitleColor() > 0) {
				mTitle.setTextColor(data.getTitleColor());
			}

			if (data.getTitleSize() > 0) {
				int titleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, data.getTitleSize(), getResources().getDisplayMetrics());
				mTitle.setTextSize(titleSize);
			}
		}
	}

	public interface onSwitchItemChangedListener {
		public void onSwitchItemChanged(boolean isChecked);
	}

	public void setOnSwitchItemChangedListener(onSwitchItemChangedListener listener) {
		mChangedListener = listener;
	}

	public TextView getmTitle() {
		return mTitle;
	}

	public SwitchView getmSwitch() {
		return mSwitch;
	}

	public ImageView getmDrawable() {
		return mDrawable;
	}
}
