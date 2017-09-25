package com.example.viewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by gaoliang on 2017/8/29.
 */

public class ViewDemoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button lineChartBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);
        initView();
    }

    private void initView() {
        lineChartBtn = (Button) findViewById(R.id.line_chart);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.line_chart) {
            startActivity(new Intent(ViewDemoActivity.this, LineCharActivity.class));
        }
    }
}
