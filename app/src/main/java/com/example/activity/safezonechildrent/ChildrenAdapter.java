package com.example.activity.safezonechildrent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tien on 09-Jan-16.
 */
public class ChildrenAdapter extends ArrayAdapter<String> {
    public ChildrenAdapter(Context context, int layout, List<String> childrenList) {
        super(context, layout, childrenList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View theView = layoutInflater.inflate(R.layout.row_layout_showchildren, parent, false);
        String childrenUser = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.tvUserChildren);
        theTextView.setText(childrenUser);
        ImageView theImageView = (ImageView) theView.findViewById(R.id.imgUser);
        theImageView.setImageResource(R.drawable.icon_kid);
        return theView;
    }
}
