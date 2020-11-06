package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;


public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebSelectable, PageViewerFragment.updatable,
    PageListFragment.ItemSelected {
    PageViewerFragment pageViewerFragment;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    ArrayList<String> items;
    ArrayList<PageViewerFragment> newFrag;
    boolean otherFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otherFrag = findViewById(R.id.page_list) != null;
       // pageViewerFragment = PageViewerFragment.newInstance(urlString);

        // pageViewerFragment = new PageViewerFragment();
        items = new ArrayList<>();
        newFrag = new ArrayList<>();
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
            pagerFragment = new PagerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.page_display, pagerFragment.newInstance(newFrag))
                    .commit();
        }
        if(otherFrag) {
            if ((fragment = fragmentManager.findFragmentById(R.id.page_list)) instanceof PageListFragment)
                pageListFragment = (PageListFragment) fragment;
            else {
                pageListFragment = new PageListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.page_list, pageListFragment.newInstance(items))
                        .commit();
            }
        }
    }


    @Override
    public void selectWeb (String urlString){
        pageViewerFragment.showUrl(urlString);
    }
    @Override
    public void selectBack() {
        pageViewerFragment.showBack();
    }
    @Override
    public void selectForward() {
        pageViewerFragment.showForward();
    }
    @Override
    public void updateUrlString(String url) {
        pageControlFragment.updateUrlString(url);
    }

    @Override
    public void itemSelected(int index) {

    }
}

