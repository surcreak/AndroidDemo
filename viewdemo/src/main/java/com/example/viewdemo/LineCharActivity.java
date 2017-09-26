package com.example.viewdemo;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.viewdemo.view.LineChar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoliang on 2017/9/25.
 */

public class LineCharActivity extends Activity {

    private LineChar lineChar;
    private List<Point> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_char);
        initView();
        initData();
    }

    private void initView() {
        lineChar = (LineChar) findViewById(R.id.line_chart);
    }

    private void initData() {
        data = createTestData();
        lineChar.setData(data);
    }

    private List createTestData() {
        List list = new ArrayList<Point>();
        int scale = 10;
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i*scale, i*scale);
            list.add(p);
        }
        return list;
    }
}
