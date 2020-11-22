package edu.temple.webbrowserapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {
    private static final String URL_KEY = "url";
    private View frame;
    private WebView webView;
    private String urlString;
    private updatable parentActivity;
    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance(String urlString) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, urlString);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            urlString = getArguments().getString(URL_KEY);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof updatable){
            parentActivity = (updatable) context;
        }
        else{
            throw new RuntimeException("You must implement updatable interface before attaching this fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frame = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = frame.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String urlString, Bitmap favicon) {
                super.onPageStarted(view, urlString, favicon);
                parentActivity.updateUrlString(urlString);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                parentActivity.updateTitle(view.getTitle());
            }
        });
        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
        else {
            if (urlString != null) {
                webView.loadUrl(urlString);
            } else {

                parentActivity.updateUrlString("");
            }
        }

        return frame;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
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

    public String getUrl() {
        if (webView != null)
            return webView.getUrl();
        else
            return "";
    }

    public String getTitle() {
        String title;
        if (webView != null) {
            title = webView.getTitle();
            return title == null || title.isEmpty() ? webView.getUrl() : title;
        } else
            return "Blank Page";
    }
    interface updatable {
        void updateUrlString(String url);
        void updateTitle(String title);
    }
}