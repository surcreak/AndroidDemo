package com.example.horizontalgridview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by surcreak on 17-2-8.
 */

public class HorizontalGridView extends AdapterView<ListAdapter> {
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    private int row = 2;
    private int column = 3;
    private int mOrientation = ORIENTATION_HORIZONTAL;

    private ListAdapter mAdapter;

    public HorizontalGridView(Context context) {
        this(context, null);
    }

    public HorizontalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        removeAllViewsInLayout();
        fillList();
        positionItems();
        invalidate();
    }

    private void addAndMeasureChild(final View child, int viewPos) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        addViewInLayout(child, viewPos, params, true);
        child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
    }

    private void fillList(){
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View child = mAdapter.getView(i, null, this);
            addAndMeasureChild(child, -1);
        }
    }

    private void positionItems(){
        int left = 0;
        int top = 0;
        if( getChildCount()>0 ) {
            int width = getChildAt(0).getMeasuredWidth();
            int height = getChildAt(0).getMeasuredHeight();
            Log.d("gaol", "positionItems: getChildCount()="+getChildCount());
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if( mOrientation == ORIENTATION_VERTICAL ){

                }else {
                    child.layout(left, top, left+width, top+height);
                    top = ((i/column)%row)*height;
                    left = ((i/(row*column))*column + i%column)*width;
                }
            }
        }
    }

    @Override
    public ListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

}
