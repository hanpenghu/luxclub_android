package com.hsbank.luxclub.mywidget.setting_item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.setting_item.entity.SettingViewItemData;
import com.hsbank.luxclub.mywidget.setting_item.item.BasicItemViewH;
import com.hsbank.luxclub.mywidget.setting_item.item.BasicItemViewV;
import com.hsbank.luxclub.mywidget.setting_item.item.CheckItemViewH;
import com.hsbank.luxclub.mywidget.setting_item.item.CheckItemViewV;
import com.hsbank.luxclub.mywidget.setting_item.item.ImageItemView;
import com.hsbank.luxclub.mywidget.setting_item.item.SwitchItemView;

/**
 * 设置条目组合控件，大大简化设置页面布局
 * 支持XML手动布局，XML属性设置
 * 支持动态添加
 * 源代码地址：https://github.com/SkillCollege/SettingView
 */
public class SettingButton extends LinearLayout {

	private View mTopDivider;
	private View mBottomDivider;

	private onSettingButtonClickListener mSettingButtonClickListener;
	private onSettingButtonSwitchListener mSettingButtonSwitchListener;

	public SettingButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		mTopDivider = new View(context);
		mTopDivider.setBackgroundColor(getResources().getColor(R.color.line_divider));

		mBottomDivider = new View(context);
		mBottomDivider.setBackgroundColor(getResources().getColor(R.color.line_divider));
	}

	public void setAdapter(SettingViewItemData data) {
		LayoutParams dividerLps = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
		// Add Top Divider
		addView(mTopDivider, dividerLps);
		// Add Content
		initItemView(data);
		// Add Bottom Divider
		addView(mBottomDivider, dividerLps);
	}

	private void initItemView(SettingViewItemData data) {
		FrameLayout itemView = data.getItemView();

		if (itemView instanceof SwitchItemView) {
			((SwitchItemView) itemView).fillData(data.getData());
			itemView.setClickable(false);
			((SwitchItemView) itemView).setOnSwitchItemChangedListener(new SwitchItemView.onSwitchItemChangedListener() {

				@Override
				public void onSwitchItemChanged(boolean isChecked) {
					if (null != mSettingButtonSwitchListener) {
						mSettingButtonSwitchListener.onSwitchChanged(isChecked);
					}
				}
			});

		} else {
			itemView.setClickable(data.isClickable());
			itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != mSettingButtonClickListener) {
						mSettingButtonClickListener.onSettingButtonClick();
					}
				}
			});

			if (itemView instanceof BasicItemViewH) {
				((BasicItemViewH) itemView).fillData(data.getData());
			} else if (itemView instanceof BasicItemViewV) {
				((BasicItemViewV) itemView).fillData(data.getData());
			} else if (itemView instanceof ImageItemView) {
				((ImageItemView) itemView).fillData(data.getData());
			} else if (itemView instanceof CheckItemViewH) {
				((CheckItemViewH) itemView).fillData(data.getData());
			} else if (itemView instanceof CheckItemViewV) {
				((CheckItemViewV) itemView).fillData(data.getData());
			}
		}

		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.setting_view_item_height), getResources().getDisplayMetrics());
		LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, height);

		addView(itemView, lps);
	}

	public interface onSettingButtonClickListener {
		void onSettingButtonClick();
	}

	public void setOnSettingButtonClickListener(onSettingButtonClickListener listener) {
		this.mSettingButtonClickListener = listener;
	}

	public interface onSettingButtonSwitchListener {
		public void onSwitchChanged(boolean isChecked);
	}

	public void setOnSettingButtonSwitchListener(onSettingButtonSwitchListener listener) {
		this.mSettingButtonSwitchListener = listener;
	}

	public FrameLayout getItemView() {
		return (FrameLayout) getChildAt(1);
	}

	public void modifyTitle(String title) {
		FrameLayout itemView = getItemView();
		if (itemView instanceof SwitchItemView) {
			((SwitchItemView) itemView).getmTitle().setText(title);
		} else {
			if (itemView instanceof BasicItemViewH) {
				((BasicItemViewH) itemView).getTitle().setText(title);
			} else if (itemView instanceof BasicItemViewV) {
				((BasicItemViewV) itemView).getmTitle().setText(title);
			} else if (itemView instanceof ImageItemView) {
				((ImageItemView) itemView).getmTitle().setText(title);
			} else if (itemView instanceof CheckItemViewH) {
				((CheckItemViewH) itemView).getmTitle().setText(title);
			} else if (itemView instanceof CheckItemViewV) {
				((CheckItemViewV) itemView).getmTitle().setText(title);
			}
		}
	}

	public void modifySubTitle(String subTitle) {
		FrameLayout itemView = getItemView();
		if (itemView instanceof BasicItemViewH) {
			((BasicItemViewH) itemView).getSubTitle().setText(subTitle);
		} else if (itemView instanceof BasicItemViewV) {
			((BasicItemViewV) itemView).getmSubTitle().setText(subTitle);
		} else if (itemView instanceof CheckItemViewV) {
			((CheckItemViewV) itemView).getmSubTitle().setText(subTitle);
		}
	}

	public void modifyDrawable(Drawable drawable) {
		FrameLayout itemView = getItemView();
		if (itemView instanceof SwitchItemView) {
			((SwitchItemView) itemView).getmDrawable().setImageDrawable(drawable);
		} else {
			if (itemView instanceof BasicItemViewH) {
				((BasicItemViewH) itemView).getmDrawable().setImageDrawable(drawable);
			} else if (itemView instanceof BasicItemViewV) {
				((BasicItemViewV) itemView).getmDrawable().setImageDrawable(drawable);
			} else if (itemView instanceof ImageItemView) {
				((ImageItemView) itemView).getmDrawable().setImageDrawable(drawable);
			} else if (itemView instanceof CheckItemViewH) {
				((CheckItemViewH) itemView).getmDrawable().setImageDrawable(drawable);
			} else if (itemView instanceof CheckItemViewV) {
				((CheckItemViewV) itemView).getmDrawable().setImageDrawable(drawable);
			}
		}
	}

	public void modifyInfo(Drawable drawable) {
		FrameLayout itemView = getItemView();
		if (itemView instanceof ImageItemView) {
			((ImageItemView) itemView).getmImage().setImageDrawable(drawable);
		}
	}
}
