package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class BrowserActivity extends AppCompatActivity implements PageControlFragment.webSelectable, PageViewerFragment.updatable,
    PageListFragment.itemSelected, BrowserControlFragment.createdNewFrag, PagerFragment.updates{
    private final String FRAG_KEY = "newFrag";

    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    ArrayList<String> items;
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

        items = new ArrayList<>();
       // newFrag = new ArrayList<>();

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
}

