package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BrowserActivity extends AppCompatActivity implements PageControlFragment.webSelectable, PageViewerFragment.updatable,
    PageListFragment.itemSelected, BrowserControlFragment.createdNewFrag, PagerFragment.updates{
    public static final String WORD = "word";
    private final String FRAG_KEY = "newFrag";
    private final String MESSAGE_KEY = "message";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    int count = 0;
    ArrayList<PageViewerFragment> newFrag;
    Intent newIntent;
    boolean otherFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newIntent = new Intent(BrowserActivity.this, BookmarksActivity.class);
        if (savedInstanceState != null)
            newFrag = (ArrayList) savedInstanceState.getSerializable(FRAG_KEY);
        else
            newFrag = new ArrayList<>();

        otherFrag = findViewById(R.id.page_list) != null;
        prefs = getSharedPreferences(MESSAGE_KEY, MODE_PRIVATE);
        editor = prefs.edit();

       // editor.clear();
       // editor.apply();
        fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        if ((fragment = fragmentManager.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browserControlFragment = (BrowserControlFragment) fragment;
        else {
            browserControlFragment = new BrowserControlFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }
        if ((fragment = fragmentManager.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) fragment;
        else {
            pageControlFragment = new PageControlFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }

        if ((fragment = fragmentManager.findFragmentById(R.id.page_display)) instanceof PagerFragment)
            pagerFragment = (PagerFragment) fragment;
        else {
            pagerFragment = PagerFragment.newInstance(newFrag);
            fragmentManager.beginTransaction()
                    .add(R.id.page_display, pagerFragment)
                    .commit();
        }
        if(otherFrag) {
            if ((fragment = fragmentManager.findFragmentById(R.id.page_list)) instanceof PageListFragment)
                pageListFragment = (PageListFragment) fragment;
            else {
                pageListFragment = pageListFragment.newInstance(newFrag);
                fragmentManager.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Sharing", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private  void clearId(){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }
        pageControlFragment.updateUrlString("");
    }
    private void notifyWebsites() {
        pagerFragment.notifyWebs();
        if (otherFrag) {
            pageListFragment.notifyWebs();
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save list of open pages for activity restart
        outState.putSerializable(FRAG_KEY, newFrag);
    }


    @Override
    public void selectWeb (String urlString){
        if (newFrag.size() > 0) {
            pagerFragment.callGoPageViewer(urlString);
        }
        else{
            newFrag.add(PageViewerFragment.newInstance(urlString));
            notifyWebsites();
            pagerFragment.clickedFrag(newFrag.size() - 1);
        }
    }
    @Override
    public void selectBack() {
        pagerFragment.callBackPageViewer();
    }
    @Override
    public void selectForward() {
        pagerFragment.callForwardPageViewer();
    }
    @Override
    public void updateUrlString(String url) {
        if (url != null && url.equals(pagerFragment.getCurrentUrl())) {
            pageControlFragment.updateUrlString(url);
            notifyWebsites();
        }
    }
    @Override
    public void itemSelected(int index) {
        pagerFragment.clickedFrag(index);
    }
    @Override
    public void newFragNote() {
        newFrag.add(new PageViewerFragment());
        notifyWebsites();
        pagerFragment.clickedFrag(newFrag.size() - 1);
        clearId();
    }

    @Override
    public void newBookmark() {
        if(pagerFragment.newFrag.size()!=0) {
            editor.putString(pagerFragment.getCurrentTitle(), pagerFragment.getCurrentUrl());
            editor.apply();
        }
    }

    @Override
    public void savedBookmark() {
        startActivityForResult(newIntent, 7);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 7){
            if ((resultCode == RESULT_OK)){
                String word = data.getStringExtra(WORD);
                if(word!=null){
                    selectWeb(word);
                    Log.d("yess", word);
                }
            }
        }
    }

    @Override
    public void updateTitle(String title) {
        if (title != null && title.equals(pagerFragment.getCurrentTitle()) && getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
        notifyWebsites();
    }
}

