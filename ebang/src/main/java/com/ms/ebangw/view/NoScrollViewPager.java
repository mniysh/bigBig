package com.ms.ebangw.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * Created by admin on 2015/9/27.
 */
public class NoScrollViewPager extends ViewPager {
    private float mDownx , mDowny;
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec( Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                mDownx=ev.getX();
                mDowny=ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE :
               if(Math.abs(ev.getX()-mDownx)>(Math.abs(ev.getY()-mDowny))){
                   getParent().requestDisallowInterceptTouchEvent(true);
               }else{
                   getParent().requestDisallowInterceptTouchEvent(true);
               }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL :
               getParent().requestDisallowInterceptTouchEvent(false);

                break;


        }


        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
