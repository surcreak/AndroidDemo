package com.surcreak.androiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.horizontalgridview.HorizontalGridView;
import com.example.horizontalgridview.HorizontalGridViewAdpter;

/**
 * Created by gaoliang on 2017/7/31.
 */

public class TestHorizontalGridViewFragment extends Fragment {

    private HorizontalGridView mHorizontalGridView;
    private HorizontalGridViewAdpter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horizontal_grid_view_test, container, false);
        setupView(view);

        mAdapter = new HorizontalGridViewAdpter(getContext());
        mHorizontalGridView.setAdapter(mAdapter);

        return view;
    }

    private void setupView(View v){
        mHorizontalGridView = (HorizontalGridView) v.findViewById(R.id.horizontal_grid_view);
    }
}
