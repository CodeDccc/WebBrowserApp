package edu.temple.webbrowserapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {
    View frame;
    WebView webView;
    private updatable parentActivity;
    public PageViewerFragment() {
        // Required empty public constructor
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            webView.restoreState(savedInstanceState);
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
    interface updatable {
        void updateUrlString(String url);
    }
}
