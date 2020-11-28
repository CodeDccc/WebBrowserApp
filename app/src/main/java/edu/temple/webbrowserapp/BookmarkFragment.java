package edu.temple.webbrowserapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class BookmarkFragment extends Fragment {
    private static String TITLE_KEY = "title";
    private final String MESSAGE_KEY = "message";
    private final String COUNT_KEY = "count";
    ArrayList<String> title;
    ListView listView;
    BaseAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ImageView imageView;
    private View frame;
    Context context;
    int count = 0;
    int pos;
    recallWeb  parentActivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookmarkFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(ArrayList<String> title) {
        BookmarkFragment fragment = new BookmarkFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(TITLE_KEY, title);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getStringArrayList(TITLE_KEY);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BookmarkFragment.recallWeb){
            parentActivity = (BookmarkFragment.recallWeb) context;
        }
        else{
            throw new RuntimeException("You must implement recallWeb interface before attaching this fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frame = inflater.inflate(R.layout.fragment_bookmark, container, false);
        title = new ArrayList<>();

        listView.setAdapter(new BookmarkAdapter(getActivity(), title));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                parentActivity.webRecall(parent.getItemAtPosition(position).toString());
              //  BookmarksActivity.this.finish();
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog();
                return true;
            }
        });

        return frame;
    }

    private void dialog(){
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //title.remove(pos);
                        //adapter.notifyDataSetChanged();
                        editor.remove("string["+pos+"]");
                        title.remove(preferences.getString("string["+pos+"]", ""));
                        count--;
                        editor.putInt(COUNT_KEY, count);
                        editor.apply();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    interface recallWeb{
       void webRecall(String url);
    }
}