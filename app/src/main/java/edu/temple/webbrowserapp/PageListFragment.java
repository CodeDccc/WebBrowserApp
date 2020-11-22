package edu.temple.webbrowserapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageListFragment extends Fragment {
    private static final String FRAG_KEY = "newFrag";
    private ArrayList<PageViewerFragment> newFrag;
    private View frame;
    private ListView listView;
    itemSelected parentActivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PageListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PageListFragment newInstance(ArrayList<PageViewerFragment> newFrag) {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        args.putSerializable(FRAG_KEY, newFrag);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newFrag = (ArrayList) getArguments().getSerializable(FRAG_KEY);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof itemSelected){
            parentActivity = (itemSelected) context;
        }
        else{
            throw new RuntimeException("You must implement ItemSelected interface before attaching this fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frame = inflater.inflate(R.layout.fragment_page_list, container, false);
        listView = frame.findViewById(R.id.listView);

        listView.setAdapter(new PageListAdapter(getActivity(), newFrag));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 parentActivity.itemSelected(position);
             }
         });
        return frame;
    }
    public void notifyWebs(){
        if(listView != null){
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }
    interface itemSelected{
        void itemSelected(int index);
    }
}