package com.example.viewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by gaoliang on 2017/9/25.
 */

public class LineChar extends View {
    private final static String TAG = LineChar.class.getSimpleName();

    private List<Point> data;
    private float perUnitLenthX;
    private float perUnitLenthY;
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private float gapX;
    private float gapY;
    private boolean hasGridLineX;
    private boolean hasGridLineY;

    public LineChar(Context context) {
        this(context, null);
    }

    public LineChar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    }

    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (data == null)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            final float lenth = maxX - minX;
            result = (int)(getPaddingLeft() + getPaddingRight() + lenth * perUnitLenthX);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            final float hight = maxY - minX;
            result = (int)(getPaddingTop() + getPaddingBottom() + hight * perUnitLenthY);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || data.size() <= 0) {
            return;
        }

        Paint linePaint = new Paint();
        Paint pointPaint = new Paint();

        canvas.drawPoint(data.get(0).x, data.get(0).y, pointPaint);
        for (int i = 1; i < data.size(); i++) {
            canvas.drawLine(data.get(i - 1).x, data.get(i - 1).y, data.get(i).x, data.get(i).y, linePaint);
            canvas.drawPoint(data.get(i).x, data.get(i).y, pointPaint);
        }
    }
}
