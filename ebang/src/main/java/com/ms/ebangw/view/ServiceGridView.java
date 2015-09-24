package com.ms.ebangw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *
 * Created by admin on 2015/9/24.
 */
public class ServiceGridView extends GridView{


    public ServiceGridView(Context context) {
        super(context);
    }

    public ServiceGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int selfHeight=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, selfHeight);
    }
}
