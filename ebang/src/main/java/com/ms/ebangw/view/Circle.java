package com.ms.ebangw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆环
 * @author gaoshunsheng
 *
 */
public class Circle extends View {

	private int radius = 50;
	private Paint paint;
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	
	public Circle(Context context) {
		this(context, null);
	}

	public Circle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public Circle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	private void init(){
		paint = new Paint();
		//蓝色
		paint.setColor(Color.parseColor("#4A9AEC"));
		this.paint.setStrokeWidth(2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		paint.setAntiAlias(true);
        canvas.drawCircle(getLeft() + getWidth() / 2, getTop() + getHeight() / 2, radius, paint);  
	}
}
