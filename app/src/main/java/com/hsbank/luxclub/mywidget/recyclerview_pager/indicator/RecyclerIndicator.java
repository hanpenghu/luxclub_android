package com.hsbank.luxclub.mywidget.recyclerview_pager.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.recyclerview_pager.RecyclerViewPager;
import com.hsbank.luxclub.mywidget.recyclerview_pager.indicator.holder.ShapeHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 轻量级ViewPager指示器,支持三种不同的模式
 * recyclerview指示器，由viewpagerindicator改造而来，兼容两种模式
 * 源代码地址：https://github.com/Trinea/android-open-project
 */

public class RecyclerIndicator extends View{

//    private ViewPager viewPager;
    private RecyclerViewPager mRecyclerViewPager;
    private List<ShapeHolder> tabItems;
    private ShapeHolder movingItem;

    //config list
    private int mCurItemPosition;
    private float mCurItemPositionOffset;
    private float mIndicatorRadius;
    private float mIndicatorMargin;
    private int mIndicatorBackground;
    private int mIndicatorSelectedBackground;
    private Gravity mIndicatorLayoutGravity;
    private Mode mIndicatorMode;

    //default value
    private final int DEFAULT_INDICATOR_RADIUS = 10;
    private final int DEFAULT_INDICATOR_MARGIN = 40;
    private final int DEFAULT_INDICATOR_BACKGROUND = Color.BLUE;
    private final int DEFAULT_INDICATOR_SELECTED_BACKGROUND = Color.YELLOW;
    private final int DEFAULT_INDICATOR_LAYOUT_GRAVITY = Gravity.CENTER.ordinal();
    private final int DEFAULT_INDICATOR_MODE = Mode.SOLO.ordinal();

    public enum Gravity{
        LEFT,
        CENTER,
        RIGHT
    }
    public enum Mode{
        INSIDE,
        OUTSIDE,
        SOLO
    }
    public RecyclerIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public RecyclerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecyclerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(Context context,AttributeSet attrs){
        tabItems = new ArrayList<>();
        handleTypedArray(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if(attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecyclerIndicator);
        mIndicatorRadius = typedArray.getDimensionPixelSize(R.styleable.RecyclerIndicator_ri_radius, DEFAULT_INDICATOR_RADIUS);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.RecyclerIndicator_ri_margin, DEFAULT_INDICATOR_MARGIN);
        mIndicatorBackground = typedArray.getColor(R.styleable.RecyclerIndicator_ri_background, DEFAULT_INDICATOR_BACKGROUND);
        mIndicatorSelectedBackground = typedArray.getColor(R.styleable.RecyclerIndicator_ri_selected_background,DEFAULT_INDICATOR_SELECTED_BACKGROUND);
        int gravity = typedArray.getInt(R.styleable.RecyclerIndicator_ri_gravity,DEFAULT_INDICATOR_LAYOUT_GRAVITY);
        mIndicatorLayoutGravity = Gravity.values()[gravity];
        int mode = typedArray.getInt(R.styleable.RecyclerIndicator_ri_mode,DEFAULT_INDICATOR_MODE);
        mIndicatorMode = Mode.values()[mode];
        typedArray.recycle();
    }

    public void setViewPager(final RecyclerViewPager recyclerViewPager){
        this.mRecyclerViewPager = recyclerViewPager;
        createTabItems();
        createMovingItem();
        setUpListener();
    }

    private void setUpListener() {

        mRecyclerViewPager.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
//                if (mIndicatorMode == Mode.SOLO) {
                trigger(newPosition, 0);
            }
//                Log.i("***CLC***", "SingleFlingPagerActivity-->" + "onScrolled:   rate:" + "oldPosition" + oldPosition + "newPosition" + newPosition);
        });
    }

    /**
     * trigger to redraw the indicator when the ViewPager's selected recyclerview_item_joy changed!
     * @param position
     * @param positionOffset
     */
    private void trigger(int position,float positionOffset){
        RecyclerIndicator.this.mCurItemPosition = position;
        RecyclerIndicator.this.mCurItemPositionOffset = positionOffset;
//        Log.e("CircleIndicator", "onPageScrolled()" + position + ":" + positionOffset);
        requestLayout();
        invalidate();
    }
    private void createTabItems() {
        for (int i = 0; i < mRecyclerViewPager.getAdapter().getItemCount(); i++) {
            OvalShape circle = new OvalShape();
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            Paint paint = drawable.getPaint();
            paint.setColor(mIndicatorBackground);
            paint.setAntiAlias(true);
            shapeHolder.setPaint(paint);
            tabItems.add(shapeHolder);
        }
    }

    /**
     * 更新指示器数量
     */
    public void notifyCountChanged(){
        tabItems.clear();
        createTabItems();
        requestLayout(); // view 重绘，否则不刷新
    }

    private void createMovingItem() {
        OvalShape circle = new OvalShape();
        ShapeDrawable drawable = new ShapeDrawable(circle);
        movingItem = new ShapeHolder(drawable);
        Paint paint = drawable.getPaint();
        paint.setColor(mIndicatorSelectedBackground);
        paint.setAntiAlias(true);

        switch (mIndicatorMode){
            case INSIDE:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                break;
            case OUTSIDE:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                break;
            case SOLO:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                break;
        }

        movingItem.setPaint(paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e("CircleIndicator","onLayout()");
        super.onLayout(changed, left, top, right, bottom);
        final int width = getWidth();
        final int height = getHeight();
        layoutTabItems(width, height);
        layoutMovingItem(mCurItemPosition, mCurItemPositionOffset);
    }

   private void layoutTabItems(final int containerWidth,final int containerHeight){
       if(tabItems == null){
           throw new IllegalStateException("forget to create tabItems?");
       }
       final float yCoordinate = containerHeight*0.5f;
       final float startPosition = startDrawPosition(containerWidth);
       for(int i=0;i<tabItems.size();i++){
           ShapeHolder item = tabItems.get(i);
           item.resizeShape(2* mIndicatorRadius,2* mIndicatorRadius);
           item.setY(yCoordinate- mIndicatorRadius);
           float x = startPosition + (mIndicatorMargin + mIndicatorRadius*2)*i;
           item.setX(x);
       }

    }
    private float startDrawPosition(final int containerWidth){
        if(mIndicatorLayoutGravity == Gravity.LEFT)
            return 0;
        float tabItemsLength = tabItems.size()*(2* mIndicatorRadius + mIndicatorMargin)- mIndicatorMargin;
        if(containerWidth<tabItemsLength){
            return 0;
        }
        if(mIndicatorLayoutGravity == Gravity.CENTER){
            return (containerWidth-tabItemsLength)/2;
        }
        return containerWidth - tabItemsLength;
    }
    private void layoutMovingItem(final int position,final float positionOffset){
        if(movingItem == null){
            throw new IllegalStateException("forget to create movingItem?");
        }
        ShapeHolder item = tabItems.get(position);
        movingItem.resizeShape(item.getWidth(), item.getHeight());
        float x = item.getX()+(mIndicatorMargin + mIndicatorRadius*2)*positionOffset;
        movingItem.setX(x);
        movingItem.setY(item.getY());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("CircleIndicator", "onDraw()");
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        for(ShapeHolder item : tabItems){
            canvas.save();
            canvas.translate(item.getX(),item.getY());
            item.getShape().draw(canvas);
            canvas.restore();
        }

        if(movingItem != null){
            canvas.save();
            canvas.translate(movingItem.getX(), movingItem.getY());
            movingItem.getShape().draw(canvas);
            canvas.restore();
        }
        canvas.restoreToCount(sc);
    }

    public void setIndicatorRadius(float mIndicatorRadius) {
        this.mIndicatorRadius = mIndicatorRadius;
    }

    public void setIndicatorMargin(float mIndicatorMargin) {
        this.mIndicatorMargin = mIndicatorMargin;
    }

    public void setIndicatorBackground(int mIndicatorBackground) {
        this.mIndicatorBackground = mIndicatorBackground;
    }

    public void setIndicatorSelectedBackground(int mIndicatorSelectedBackground) {
        this.mIndicatorSelectedBackground = mIndicatorSelectedBackground;
    }

    public void setIndicatorLayoutGravity(Gravity mIndicatorLayoutGravity) {
        this.mIndicatorLayoutGravity = mIndicatorLayoutGravity;
    }

    public void setIndicatorMode(Mode mIndicatorMode) {
        this.mIndicatorMode = mIndicatorMode;
    }
}
