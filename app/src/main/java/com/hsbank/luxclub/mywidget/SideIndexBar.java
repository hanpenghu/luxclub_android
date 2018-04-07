package com.hsbank.luxclub.mywidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hsbank.luxclub.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字母索引控件
 * Created by chen_liuchun on 2016/3/26.
 */
public class SideIndexBar extends View {
    private String[] LETTERS = new String[] { "#", "$" };
    private Paint mPaint;
    private int mCellWidth;
    private float mCellHeight;
    private Rect mBounds;
    private int mIndex = -1;
    private TextView mTextDialog;

    private List<String> mLetterList;
    private float mStartHeight;

    /**
     * 设置字母提示dialog
     * @param mTextDialog
     */
    public void setLetterView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setLetterList(ArrayList<String> letterList) {
        mLetterList.addAll(letterList);
        invalidate();
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }

    private OnLetterChangeListener mOnLetterChangeListener;

    public OnLetterChangeListener getOnLetterChangeListener() {
        return mOnLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        mOnLetterChangeListener = onLetterChangeListener;
    }

    public SideIndexBar(Context context) {
        this(context, null);
    }

    public SideIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        List<String> list = Arrays.asList(LETTERS);
        mLetterList = new ArrayList<>(list);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        //字体调大小
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mBounds = new Rect();
    }

    // 画字母表
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLetterList.size(); i++) {
            String text = mLetterList.get(i);
            float textWidth = mPaint.measureText(text);
            float x = mCellWidth * 0.5f - textWidth * 0.5f;
            mPaint.getTextBounds(text, 0, text.length(), mBounds);
            float textHeight = mBounds.height();
            float y = mCellHeight * 0.5f + textHeight * 0.5f + i * mCellHeight + mStartHeight;
            mPaint.setColor(i == mIndex ? Color.WHITE : getResources().getColor(R.color.yellow_lux));
            canvas.drawText(text, x, y, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCellWidth = getMeasuredWidth();
        mCellHeight = mCellWidth;
        mStartHeight = (getMeasuredHeight()* 1.0f - mLetterList.size() * mCellWidth)/2;
//        mCellHeight = getMeasuredHeight() * 1.0f / mLetterList.size();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.i("clc", "onLayout: "+changed+"  "+ left+"  "+ top+"  "+ right+"  "+ bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y;
        int currentIndex;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            y = event.getY();
            currentIndex = (int) ((y - mStartHeight) / mCellHeight);
            if (currentIndex < mLetterList.size() && currentIndex != mIndex) {
                if (mOnLetterChangeListener != null) {
                    mOnLetterChangeListener.onLetterChange(mLetterList.get(currentIndex));
                }
                mIndex = currentIndex;
                // System.out.println("currentIndex: "+ mLetterList.get(currentIndex));
            }
            if (mTextDialog != null) {
                mTextDialog.setVisibility(View.VISIBLE);
                mTextDialog.setText(mLetterList.get(currentIndex));
            }
            break;
        case MotionEvent.ACTION_MOVE:
            y = event.getY();
            currentIndex = (int) ((y - mStartHeight) / mCellHeight);
            if (currentIndex < mLetterList.size() && currentIndex != mIndex) {
                if (mOnLetterChangeListener != null) {
                    mOnLetterChangeListener.onLetterChange(mLetterList.get(currentIndex));
                }
                mIndex = currentIndex;
                // System.out.println("currentIndex: "+ mLetterList.get(currentIndex));
            }
            if (mTextDialog != null) {
                mTextDialog.setText(mLetterList.get(currentIndex));
            }
            break;
        case MotionEvent.ACTION_UP:
            mIndex = -1;
            if (mTextDialog != null) {
                mTextDialog.setVisibility(View.GONE);
            }
            break;
        default:
            break;
        }
        invalidate();
        return true;
    }
}
