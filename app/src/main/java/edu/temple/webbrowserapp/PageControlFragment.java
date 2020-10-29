package edu.temple.webbrowserapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageControlFragment extends Fragment {
    private static final String URL_KEY = "urlString";
    private static final String GO_KEY = "urlString";
    private static final String BACK_KEY = "urlString";
    private static final String NEXT_KEY = "urlString";
   // private BrowserActivity.SharedViewModel mSharedViewModel;
    private Bundle buttonstate;
    View editText;
    View backButton;
    View goButton;
    View nextButton;
    View frame;
    String urlString;
   // String save;
    WebSelectable parentActivity;
    public boolean isPressed;
    private boolean isGoPressed = false;
    private boolean isNextPressed = false;
    private boolean isBackPressed = false;
    private static final String GO_PRESSED = "GoPressed";
    private static final String BACK_PRESSED = "BackPressed";
    private static final String NEXT_PRESSED = "NextPressed";
   // private SharedPreferences prefs;
    private String PrefName = "Prefers";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PageControlFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PageControlFragment newInstance(String urlString) {
        PageControlFragment fragment = new PageControlFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, urlString);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlString = getArguments().getString(URL_KEY);
            //mParam2 = getArguments().getString(ARG_PARAM2);

        }
        if(savedInstanceState != null){
            urlString = savedInstanceState.getString(URL_KEY);
        }
        else{
            urlString = "https://google.com/";
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(URL_KEY, urlString);
    }

 /* @Override
  public void onPause(){
      super.onPause();
      buttonstate = new Bundle();
      //goButton.saveState(buttonstate);
      goButton.notify();
      backButton.notify();
      nextButton.notify();
      editText.notify();
  }*/

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
                urlString = ((EditText)frame.findViewById(R.id.editText)).getText().toString();
               if(urlString==null){
                   urlString = "https://google.com";
               }
                parentActivity.selectWeb(urlString);
            }
        });
        nextButton = frame.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.selectForward();
            }
        });

        //((EditText)editText).setText(urlString);

        return frame;
    }
   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //webView.restoreState(savedInstanceState);
        savedInstanceState.putString(URL_KEY, urlString);
        // onViewStateRestored(savedInstanceState);
    }*/
    interface WebSelectable{
        void selectWeb(String urlString);
        void selectBack();
        void selectForward();
    }
}