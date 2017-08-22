package com.example.designsample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gaoliang on 2017/8/21.
 */

public class MyViewPageAdapter extends FragmentPagerAdapter {

    public MyViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        fragment = new MyFragment();
        ((MyFragment)fragment).setIndex(position);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "biaoqian"+position;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
