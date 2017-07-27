package com.surcreak.androiddemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by gaoliang on 2017/6/5.
 */

public class MainFragment extends ListFragment {

    MainActivity activity;
    OnListItemClickListerner listerner;

    public MainFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        listerner = (OnListItemClickListerner) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String[] items = getResources().getStringArray(R.array.main_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listerner.OnListItemClick(position);
    }

    public interface OnListItemClickListerner {
        void OnListItemClick(int position);
    }
}
