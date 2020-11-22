package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowserControlFragment extends Fragment {
    private View frame;
    private View newBrowserBtn;
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
        newBrowserBtn = frame.findViewById(R.id.newBrowserBtn);
        newBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               parentActivity.newFragNote();
            }
        });
        return frame;
    }
    interface createdNewFrag{
        void newFragNote();
    }
}