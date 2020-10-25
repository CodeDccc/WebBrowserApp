package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.Buffer;

public class BrowserActivity extends AppCompatActivity {

    WebView webView;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
           // webView.loadUrl("https://tuportal5.temple.edu/");
            webView.loadData(msg.obj.toString(), "text/html", "utf-8");
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        final String urlString = "https://tuportal5.temple.edu/";
        Thread thread = new Thread(){
            public void run(){
                try{
                    URL url = new URL(urlString);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String newString;
                    while((newString = reader.readLine())!=null) {
                        stringBuilder.append(newString);
                    }
                        String response = stringBuilder.toString();
                        Message msg = Message.obtain();
                        msg.obj = response;
                        handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}