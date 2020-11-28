package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class BrowserActivity extends AppCompatActivity implements PageControlFragment.webSelectable, PageViewerFragment.updatable,
    PageListFragment.itemSelected, BrowserControlFragment.createdNewFrag, PagerFragment.updates, BookmarkFragment.recallWeb{
    private final String FRAG_KEY = "newFrag";
    private final String MESSAGE_KEY = "message";
    private final String COUNT_KEY = "count";
    private final String WORD = "word";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    //int[] count;
    int count = 0;
    String word;
   // ArrayList<String> items;
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
        preferences = getSharedPreferences(MESSAGE_KEY, MODE_PRIVATE);
        editor = preferences.edit();
        word = newIntent.getStringExtra(WORD);


        if(word!=null){
            Log.d("WORD", word);
            selectWeb(word);
        }

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
        //newIntent.putExtra(MESSAGE_KEY, pagerFragment.getCurrentTitle());
        if(pagerFragment.newFrag.size()!=0) {
            if(count!=0) {
                count = preferences.getInt(COUNT_KEY, 0);
            }
            editor.putString("string["+count+"]", pagerFragment.getCurrentTitle());
            count++;
            editor.putInt(COUNT_KEY, count);
            editor.apply();

        }
      //  pagerFragment.callBackPageViewer();
        //startActivity(newIntent);
    }

    @Override
    public void savedBookmark() {
        startActivity(newIntent);
    }

    @Override
    public void updateTitle(String title) {
        if (title != null && title.equals(pagerFragment.getCurrentTitle()) && getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
        notifyWebsites();
    }

    @Override
    public void webRecall(String url) {
        selectWeb (url);
    }
}

