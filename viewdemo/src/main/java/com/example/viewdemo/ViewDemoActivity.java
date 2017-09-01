package com.example.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gaoliang on 2017/8/29.
 */

public class ViewDemoActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPageAdapter mViewPageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_page);
        mViewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPageAdapter);
    }
}
