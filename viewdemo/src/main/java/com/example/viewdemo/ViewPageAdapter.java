package com.example.viewdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gaoliang on 2017/8/29.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DrawCircleFragment();
        }else if (position == 1){
            return new DrawPIeChartFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
