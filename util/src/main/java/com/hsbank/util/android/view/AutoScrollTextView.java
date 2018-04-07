package com.hsbank.util.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 实现跑马灯效果的TextView组件
 * 2015-12-31
 */
public class AutoScrollTextView extends TextView {
	public final static String TAG = AutoScrollTextView.class.getSimpleName();

	public final static int SCROLL_TYPE_NORMAL = 0;
	public final static int SCROLL_TYPE_TO_RIGHT = 1;
	public final static int SCROLL_TYPE_TO_LEFT = 2;
	public final static int SCROLL_TYPE_TO_LEFT_RIGHT = 3;

	private static final float SPEED = 0.5f;

	private int scrollType = SCROLL_TYPE_TO_LEFT_RIGHT;
	private float textLength = 0f;
	private float viewWidth = 0f;
	private float step = 0f;
	private float y = 0f;
	private float startX = 0.0f;
	private float endX = 0.0f;
	private boolean isStarting = false;
	private boolean isToLeft = true;
	private Paint paint = null;
	private String text = "";
	private int textColor = 0;

	public AutoScrollTextView(Context context) {
		super(context);
		initView();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		paint = getPaint();
		text = super.getText().toString();
		textColor = super.getCurrentTextColor();
	}

	private void prepareScroll() {
		if (viewWidth > 0) {
			textLength = paint.measureText(text);
			if (textLength > viewWidth) {
				switch (scrollType) {
				case SCROLL_TYPE_NORMAL:
					step = 0;
					startX = 0;
					endX = 0;
					y = getTextSize() + getPaddingTop();
					break;

				case SCROLL_TYPE_TO_RIGHT:
					step = 0;
					startX = -textLength;
					endX = viewWidth;
					y = getTextSize() + getPaddingTop();
					break;

				case SCROLL_TYPE_TO_LEFT:
					step = 0;
					startX = viewWidth;
					endX = -textLength;
					y = getTextSize() + getPaddingTop();
					break;

				case SCROLL_TYPE_TO_LEFT_RIGHT:
					float tempWidth = viewWidth * 0.214f;
					step = -tempWidth;
					startX = tempWidth;
					endX = viewWidth - textLength - tempWidth;
					y = getTextSize() + getPaddingTop();
					break;

				default:
					break;
				}
			}
			isStarting = textLength > viewWidth && scrollType != SCROLL_TYPE_NORMAL;
			super.setTextColor(isStarting ? Color.argb(0, 0, 0, 0) : textColor);
		}
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.step = step;
		ss.isStarting = isStarting;
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		step = ss.step;
		isStarting = ss.isStarting;
	}

	public static class SavedState extends BaseSavedState {
		public boolean isStarting = false;
		public float step = 0.0f;

		SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeBooleanArray(new boolean[] { isStarting });
			out.writeFloat(step);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}
		};

		@SuppressWarnings("unused")
		private SavedState(Parcel in) {
			super(in);
			boolean[] b = null;
			in.readBooleanArray(b);
			if (b != null && b.length > 0)
				isStarting = b[0];
			step = in.readFloat();
		}
	}

	public void startScroll() {
		isStarting = true;
		invalidate();
	}

	public void stopScroll() {
		isStarting = false;
		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isStarting) {
			if (paint != null) {
				paint.setColor(textColor);
				canvas.drawText(text, startX + step, y, paint);
			}
			switch (scrollType) {
			case SCROLL_TYPE_NORMAL:
				break;

			case SCROLL_TYPE_TO_RIGHT:
				step += SPEED;
				if (startX + step >= endX)
					step = 0;
				break;

			case SCROLL_TYPE_TO_LEFT:
				step -= SPEED;
				if (startX + step <= endX)
					step = 0;
				break;

			case SCROLL_TYPE_TO_LEFT_RIGHT:
				if (isToLeft) {
					step -= SPEED;
					if (startX + step <= endX)
						isToLeft = false;
				} else {
					step += SPEED;
					if (startX + step >= startX)
						isToLeft = true;
				}
				break;

			default:
				break;
			}
		}
		invalidate();
	}

	@Override
	public boolean onPreDraw() {
		return super.onPreDraw();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		viewWidth = getWidth();
		prepareScroll();
	}

	public void setText(String textInfo) {
		super.setText(textInfo);
		this.text = textInfo;
		prepareScroll();
	}

	public String getText() {
		return this.text;
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
		this.textColor = color;
	}

	public int getScrollType() {
		return scrollType;
	}

	public void setScrollType(int scrollType) {
		this.scrollType = scrollType;
		prepareScroll();
	}
}