package com.hsbank.util.android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class DashedLineView extends View {

	public DashedLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#dadada"));
		Path path = new Path();
		path.moveTo(0, 10);
		path.lineTo(this.getWidth(), 10);
		PathEffect effects = new DashPathEffect(new float[] { 4, 4, 4, 4 }, 1);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}
}
