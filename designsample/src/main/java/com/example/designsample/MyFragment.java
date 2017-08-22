package com.example.designsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gaoliang on 2017/8/21.
 */

public class MyFragment extends Fragment {

    private int index;

    private TextView textView;
    private RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;

    public MyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_my, null);
        setupView(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        textView.setText(" "+index);
    }

    private void setupView(View v) {
        textView = (TextView) v.findViewById(R.id.text_view);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
