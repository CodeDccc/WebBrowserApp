package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BrowserControlFragment extends Fragment {
    private View frame;
    private View addNewFrag;
    private View saveBookmark;
    private View showBookmarks;
    private createdNewFrag parentActivity;

    public BrowserControlFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof createdNewFrag){
            parentActivity = (createdNewFrag) context;
        }
        else{
            throw new RuntimeException("You must implement createNewFrag interface before attaching this fragment");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frame = inflater.inflate(R.layout.fragment_browser_control, container, false);

         addNewFrag = frame.findViewById(R.id.addNewFrag);
         saveBookmark = frame.findViewById(R.id.saveBookmark);
         showBookmarks = frame.findViewById(R.id.showBookmarks);



          View.OnClickListener bookMarkBtns = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.equals(addNewFrag))
                        parentActivity.newFragNote();
                    else if (v.equals(saveBookmark)) {
                        parentActivity.newBookmark();
                        Toast.makeText(getActivity(), "Title Saved", Toast.LENGTH_SHORT).show();
                    }
                    else if (v.equals(showBookmarks))
                        parentActivity.savedBookmark();
                }
           };

        addNewFrag.setOnClickListener(bookMarkBtns);
        saveBookmark.setOnClickListener(bookMarkBtns);
        showBookmarks.setOnClickListener(bookMarkBtns);

        return frame;
    }
    interface createdNewFrag{
        void newFragNote();
        void newBookmark();
        void savedBookmark();
    }
}