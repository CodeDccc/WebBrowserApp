package edu.temple.webbrowserapp;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PageListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PageViewerFragment> newFrag;


    public PageListAdapter (Context context, ArrayList<PageViewerFragment> newFrag) {
        this.context = context;
        this.newFrag = newFrag;
    }

    @Override
    public int getCount() {
        return newFrag.size();
    }

    @Override
    public Object getItem(int position) {
        return newFrag.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView instanceof TextView) {
            textView = (TextView) convertView;
        } else {
            textView = new TextView(context);
            textView.setPadding(5,8,8,5);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(14);
        }

        textView.setText(newFrag.get(position).getTitle());
        return textView;
    }
}
