package com.hsbank.luxclub.mywidget.setting_item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.setting_item.entity.SettingViewItemData;
import com.hsbank.luxclub.mywidget.setting_item.item.BasicItemViewH;
import com.hsbank.luxclub.mywidget.setting_item.item.BasicItemViewV;
import com.hsbank.luxclub.mywidget.setting_item.item.CheckItemViewH;
import com.hsbank.luxclub.mywidget.setting_item.item.CheckItemViewV;
import com.hsbank.luxclub.mywidget.setting_item.item.ImageItemView;
import com.hsbank.luxclub.mywidget.setting_item.item.SwitchItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置条目组合控件，大大简化设置页面布局
 * 支持XML手动布局，XML属性设置
 * 支持动态添加
 * 源代码地址：https://github.com/SkillCollege/SettingView
 */
public class SettingView extends LinearLayout {

//	private final View mDivider;
	private Context mContext;
	private View mTopDivider;
	private View mBottomDivider;

	private boolean iOSStyleable = true;
	private List<SettingViewItemData> mItemViews = null;

	private onSettingViewItemClickListener mItemClickListener;
	private onSettingViewItemSwitchListener mItemSwitchListener;

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mItemViews = new ArrayList<SettingViewItemData>();
		setOrientation(LinearLayout.VERTICAL);

		mTopDivider = new View(context);
		mTopDivider.setBackgroundColor(getResources().getColor(R.color.line_divider));

		mBottomDivider = new View(context);
		mBottomDivider.setBackgroundColor(getResources().getColor(R.color.line_divider));

		readAttrs(attrs);
	}

	private void readAttrs(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.SettingView);

		if (a.hasValue(R.styleable.SettingView_iOSStyle)) {
			iOSStyleable = a.getBoolean(R.styleable.SettingView_iOSStyle, false);
		}
		a.recycle();
	}

	public void setAdapter(List<SettingViewItemData> listData) {
		if (!listData.isEmpty()) {
			mItemViews = listData;

			int size = mItemViews.size();

			LayoutParams dividerLps = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
			// Add Top Divider
			addView(mTopDivider, dividerLps);
			// Add Content
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					SettingViewItemData item = mItemViews.get(i);
					initItemView(item, i);
					if (i != (size - 1)) {
						addDivider(iOSStyleable);
					}
				}
			}
			// Add Bottom Divider
			addView(mBottomDivider, dividerLps);
		}
	}

	private void addDivider(boolean iOSStylable) {
		ImageView divider = new ImageView(mContext);
		divider.setScaleType(ScaleType.FIT_XY);
		divider.setBackgroundColor(getResources().getColor(R.color.line_divider));

		LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
		lps.gravity = Gravity.RIGHT;

		if (iOSStyleable) {
			int paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.setting_view_item_height), getResources().getDisplayMetrics())
					+ (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.setting_view_lr_padding), getResources().getDisplayMetrics());
			divider.setPadding(paddingLeft, 0, 0, 0);
		}

		addView(divider, lps);
	}

	private void initItemView(SettingViewItemData data, final int index) {
		FrameLayout itemView = data.getItemView();

		if (itemView instanceof SwitchItemView) {
			((SwitchItemView) itemView).fillData(data.getData());
			((SwitchItemView) itemView).setOnSwitchItemChangedListener(new SwitchItemView.onSwitchItemChangedListener() {

				@Override
				public void onSwitchItemChanged(boolean isChecked) {
					if (null != mItemSwitchListener) {
						mItemSwitchListener.onSwitchChanged(index, isChecked);
					}
				}
			});
			itemView.setClickable(false);
		} else {
			itemView.setClickable(data.isClickable());
			itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != mItemClickListener) {
						mItemClickListener.onItemClick(index);
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

	public interface onSettingViewItemClickListener {
		void onItemClick(int index);
	}

	public void setOnSettingViewItemClickListener(onSettingViewItemClickListener listener) {
		this.mItemClickListener = listener;
	}

	public interface onSettingViewItemSwitchListener {
		public void onSwitchChanged(int index, boolean isChecked);
	}

	public void setOnSettingViewItemSwitchListener(onSettingViewItemSwitchListener listener) {
		this.mItemSwitchListener = listener;
	}

	public FrameLayout getItemView(int index) {
		return (FrameLayout) getChildAt(2 * index + 1);
	}

	public void modifyTitle(String title, int index) {
		FrameLayout itemView = getItemView(index);
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

	public void modifySubTitle(String subTitle, int index) {
		FrameLayout itemView = getItemView(index);
		if (itemView instanceof BasicItemViewH) {
			((BasicItemViewH) itemView).getSubTitle().setText(subTitle);
		} else if (itemView instanceof BasicItemViewV) {
			((BasicItemViewV) itemView).getmSubTitle().setText(subTitle);
		} else if (itemView instanceof CheckItemViewV) {
			((CheckItemViewV) itemView).getmSubTitle().setText(subTitle);
		}
	}

	public void modifyDrawable(Drawable drawable, int index) {
		FrameLayout itemView = getItemView(index);
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

	public void modifyInfo(Drawable drawable, int index) {
		FrameLayout itemView = getItemView(index);
		if (itemView instanceof ImageItemView) {
			((ImageItemView) itemView).getmImage().setImageDrawable(drawable);
		}
	}
}
