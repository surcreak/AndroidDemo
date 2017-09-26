package com.example.viewdemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.viewdemo.R;

import java.util.List;

/**
 * Created by gaoliang on 2017/9/25.
 */

public class LineChar extends View {
    private final static String TAG = LineChar.class.getSimpleName();

    private List<Point> data;
    private float perUnitLenthX;
    private float perUnitLenthY;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int gapX;
    private int gapY;
    private boolean hasGridLineX;
    private boolean hasGridLineY;
    private Point coordinateOrigin;
    private int lineColor;
    private int lineWidth;
    private int pointWidth;
    private int gridColor;
    private int gridWidth;


    public LineChar(Context context) {
        this(context, null);
    }

    public LineChar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //default
        final Resources resources = context.getResources();
        int defaultPerUnitLenthX = resources.getDimensionPixelSize(R.dimen.default_pre_util_lenth_x);
        int defaultPerUnitLenthY = resources.getDimensionPixelSize(R.dimen.default_pre_util_lenth_y);
        int defaultMinX = resources.getInteger(R.integer.default_min_x);
        int defaultMinY = resources.getInteger(R.integer.default_min_y);
        int defaultMaxX = resources.getInteger(R.integer.default_max_x);
        int defaultMaxY = resources.getInteger(R.integer.default_max_y);
        int defaultGapX = resources.getInteger(R.integer.default_gap_x);
        int defaultGapY = resources.getInteger(R.integer.default_gap_y);
        boolean defaultHasGridLineX = resources.getBoolean(R.bool.default_has_grid_line_x);
        boolean defaultHasGridLineY = resources.getBoolean(R.bool.default_has_grid_line_y);
        int defaultLineColor = resources.getColor(R.color.default_line_color);
        int defaultLineWidth = resources.getDimensionPixelOffset(R.dimen.default_line_width);
        int defaultPointWidth = resources.getDimensionPixelOffset(R.dimen.default_point_width);
        int defaultGridColor = resources.getColor(R.color.default_grid_color);
        int defaultGridWidth = resources.getDimensionPixelOffset(R.dimen.default_grid_width);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChar, defStyleAttr, 0);
        perUnitLenthX = a.getDimensionPixelSize(R.styleable.LineChar_pre_util_lenth_x, defaultPerUnitLenthX);
        perUnitLenthY = a.getDimensionPixelSize(R.styleable.LineChar_pre_util_lenth_y, defaultPerUnitLenthY);
        minX = a.getInteger(R.styleable.LineChar_pre_min_x, defaultMinX);
        minY = a.getInteger(R.styleable.LineChar_pre_min_y, defaultMinY);
        maxX = a.getInteger(R.styleable.LineChar_pre_min_x, defaultMaxX);
        maxY = a.getInteger(R.styleable.LineChar_pre_min_y, defaultMaxY);
        gapX = a.getInteger(R.styleable.LineChar_pre_gap_x, defaultGapX);
        gapY = a.getInteger(R.styleable.LineChar_pre_gap_y, defaultGapY);
        hasGridLineX = a.getBoolean(R.styleable.LineChar_has_grid_line_x, defaultHasGridLineX);
        hasGridLineY = a.getBoolean(R.styleable.LineChar_has_grid_line_y, defaultHasGridLineY);
        lineColor = a.getColor(R.styleable.LineChar_line_color, defaultLineColor);
        lineWidth = a.getInteger(R.styleable.LineChar_line_width, defaultLineWidth);
        pointWidth = a.getInteger(R.styleable.LineChar_point_width, defaultPointWidth);
        gridColor = a.getColor(R.styleable.LineChar_grid_color, defaultGridColor);
        gridWidth = a.getInteger(R.styleable.LineChar_grid_width, defaultGridWidth);
        a.recycle();
    }

    public void setData(List list) {
        data = list;
        invalidate();
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
            Log.d(TAG, "measureLong: lenth=" + lenth);
            result = (int)(getPaddingLeft() + getPaddingRight() + lenth * perUnitLenthX + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                Log.d(TAG, "measureLong: result=" + result + " specSize="+specSize);
                //result = Math.min(result, specSize);
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
            final float hight = maxY - minY;
            Log.d(TAG, "measureShort: hight=" + hight);
            result = (int)(getPaddingTop() + getPaddingBottom() + hight * perUnitLenthY + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                Log.d(TAG, "measureShort: result=" + result + " specSize="+specSize);
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

        coordinateOrigin = new Point(getPaddingLeft(), getHeight() - getPaddingBottom());

        Paint linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setAntiAlias(true);

        Paint pointPaint = new Paint();
        pointPaint.setColor(lineColor);
        pointPaint.setStrokeWidth(pointWidth);
        linePaint.setAntiAlias(true);

        Paint gridPaint = new Paint();
        DashPathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
        gridPaint.setPathEffect(effects);
        gridPaint.setAntiAlias(true);
        gridPaint.setColor(gridColor);
        gridPaint.setStrokeWidth(gridWidth);

        Path path = new Path();
        path.moveTo(coordinateOrigin.x, coordinateOrigin.y);
        for (int i = 0; i < data.size(); i++) {
            Point currentPoint = new Point(coordinateOrigin.x + (int)(data.get(i).x * perUnitLenthX),
                    coordinateOrigin.y - (int)(data.get(i).y * perUnitLenthY));
            canvas.drawPoint(currentPoint.x, currentPoint.y, pointPaint);
            path.lineTo(currentPoint.x, currentPoint.y);
        }
        canvas.drawPath(path, linePaint);

        if (hasGridLineX) {
            for (int i = 0; i < maxX; i++) {
                path.reset();
                path.moveTo(i * gapX * perUnitLenthX, 0);
                path.lineTo(i * gapX * perUnitLenthX, maxY * perUnitLenthY);
                canvas.drawPath(path, gridPaint);
            }
        }

        if (hasGridLineY) {
            for (int i = 0; i < maxY; i++) {
                path.reset();
                path.moveTo(0, i * gapY * perUnitLenthY);
                path.lineTo(maxX * perUnitLenthX, i * gapY * perUnitLenthY);
                canvas.drawPath(path, gridPaint);
            }
        }

    }
}
