package edu.temple.webbrowserapp;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class PageControlFragment extends Fragment {
    private TextView editText;
    private ImageButton backButton;
    private ImageButton goButton;
    private ImageButton nextButton;
    private View frame;
    private webSelectable parentActivity;

    public PageControlFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof webSelectable){
            parentActivity = (webSelectable) context;
        }
        else{
            throw new RuntimeException("You must implement WebSelectable interface before attaching this fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frame = inflater.inflate(R.layout.fragment_page_control, container, false);
        editText = frame.findViewById(R.id.editText);
        backButton = frame.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               parentActivity.selectBack();
            }
        });
        goButton = frame.findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.selectWeb(fixUrl(((EditText)editText).getText().toString()));
            }
        });
        nextButton = frame.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.selectForward();
            }
        });

        return frame;
    }
    public void updateUrlString(String urlString){
        editText.setText(urlString);
    }
    public void retUrl(String uri){
        editText.setText(uri);
        parentActivity.selectWeb(fixUrl(((EditText)editText).getText().toString()));
        //return editText.getText().toString();
    }
    private String fixUrl(String urlString) {
        if ((urlString.startsWith("http://") )|| (urlString.startsWith("https://"))) {
            return urlString;
        } else {
            return "http://" + urlString;
        }
    }
    interface webSelectable{
        void selectWeb(String urlString);
        void selectBack();
        void selectForward();
    }
}