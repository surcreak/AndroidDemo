package com.example.horizontalgridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by server on 17-2-8.
 */

public class HorizontalGridViewAdpter extends BaseAdapter {

    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private View view;

    public HorizontalGridViewAdpter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ){
            view = inflater.inflate(R.layout.horizontal_grid_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return view;
    }

    class ViewHolder{
        public ImageView imageView;
    }
}
