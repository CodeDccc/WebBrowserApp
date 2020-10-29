package edu.temple.webbrowserapp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageViewerFragment extends Fragment {
    private static final String URL_KEY = "urlString";
    private Bundle webviewstate;
    View frame;
    WebView webView;
    String urlString;

    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   // private Object WebViewClient;

    public PageViewerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PageViewerFragment newInstance(String urlString) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, urlString);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           urlString = getArguments().getString(URL_KEY);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
       /* if(savedInstanceState != null){
            urlString = savedInstanceState.getString(URL_KEY);
           // webView.getSettings().setJavaScriptEnabled(true);
          //  webView.setWebViewClient(new WebViewClient());
        }
        else{
            urlString = "https://google.com/";
        }*/
       /*if(savedInstanceState!=null){
           urlString = savedInstanceState.getString(URL_KEY);
           webView.post(new Runnable() {
               @Override
               public void run() {
                   webView.loadUrl(urlString);
               }
           });*/


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

   /* @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(URL_KEY, urlString);
        webView.saveState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }*/
    @Override
    public void onPause(){
        super.onPause();
        webviewstate = new Bundle();
        webView.saveState(webviewstate);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frame = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = frame.findViewById(R.id.webView);
        if(webviewstate==null){
            webView.loadUrl("http://google.com");  //Load the page for first time
        }else{
            webView.restoreState(webviewstate);    // Restore the state
        }
        //webView.loadUrl(urlString);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                 super.shouldOverrideUrlLoading(view, request);
                return false;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                super.shouldOverrideKeyEvent(view, event);
                return false;
            }
        });


        return frame;
    }

    public void showUrl(String urlString){
        webView.loadUrl(urlString);
    }
    public void showBack(){
        webView.goBack();
    }
    public void showForward(){
        webView.goForward();
    }
}
