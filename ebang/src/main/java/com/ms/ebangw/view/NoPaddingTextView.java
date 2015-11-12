package com.ms.ebangw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * textview时发现在使用android:layout_height="wrap_content"这个属性设置后，textview会有默认的padding，也就是fontpadding。这样就会造成textview和其他view中间的间距会比自己的设置的大。
 * User: WangKai(123940232@qq.com)
 * 2015-11-12 14:46
 */
public class NoPaddingTextView extends TextView {
    Paint.FontMetricsInt fontMetricsInt;
    private boolean adjustTopForAscent = true;
    public NoPaddingTextView(Context context) {
        super(context);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adjustTopForAscent){
            if (fontMetricsInt == null){
                fontMetricsInt = new Paint.FontMetricsInt();
                getPaint().getFontMetricsInt(fontMetricsInt);
            }
            canvas.translate(0, fontMetricsInt.top - fontMetricsInt.ascent);
        }
        super.onDraw(canvas);
    }
}
