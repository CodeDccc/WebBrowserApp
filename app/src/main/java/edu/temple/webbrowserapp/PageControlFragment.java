package edu.temple.webbrowserapp;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class PageControlFragment extends Fragment {
    View editText;
    View backButton;
    View goButton;
    View nextButton;
    View frame;
    private WebSelectable parentActivity;

    public PageControlFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof WebSelectable){
            parentActivity = (WebSelectable) context;
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
    interface WebSelectable{
        void selectWeb(String urlString);
        void selectBack();
        void selectForward();
    }
    public void updateUrlString(String urlString) {
        ((TextView)editText).setText(urlString);
    }
    private String fixUrl(String urlString) {
        if ((urlString.startsWith("http://") )|| (urlString.startsWith("https://"))) {
            return urlString;
        } else {
            return "http://" + urlString;
        }
    }
}