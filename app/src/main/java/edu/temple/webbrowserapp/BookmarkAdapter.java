package edu.temple.webbrowserapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BookmarkAdapter extends BaseAdapter {
    private final String MESSAGE_KEY = "message";
    private Context context;
    private ArrayList<String> title;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    public BookmarkAdapter (Context context, ArrayList<String> title) {
        this.context = context;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return title.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_items, null, true);
        TextView textView;
        //TextView textView = row.findViewById(R.id.textView);
        ImageView imageView = row.findViewById(R.id.imageView);
        imageView.setColorFilter(Color.RED);
        imageView.setMaxHeight(7);
        //textView.setGravity(Gravity.CENTER);
       // textView.setTextSize(20);
       // TextView textView;
       if (convertView instanceof TextView) {
            textView = (TextView) convertView;
        } else {
           // textView = new TextView(context);
            textView = row.findViewById(R.id.textView);
            textView.setPadding(5,8,8,5);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
        }
        final int pos = position;
        textView.setText(getItem(position).toString());
        prefs = context.getSharedPreferences(MESSAGE_KEY, MODE_PRIVATE);
        editor = prefs.edit();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.remove(getItem(pos).toString());
                                title.remove(getItem(pos).toString());
                                notifyDataSetChanged();
                                editor.apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return row;
    }

}
