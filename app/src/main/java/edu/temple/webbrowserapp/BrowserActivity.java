package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class BrowserActivity extends AppCompatActivity implements PageControlFragment.webSelectable, PageViewerFragment.updatable,
    PageListFragment.itemSelected, BrowserControlFragment.createdNewFrag{
    PageViewerFragment pageViewerFragment;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    FragmentManager fragmentManager;
    ArrayList<String> items;
    Fragment myFragment;
    ArrayList<PageViewerFragment> newFrag;
    boolean otherFrag;
    boolean checkNewFragInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otherFrag = findViewById(R.id.page_list) != null;
        checkNewFragInstance = false;

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
            pagerFragment = PagerFragment.newInstance(newFrag);
            fragmentManager.beginTransaction()
                    .add(R.id.page_display, pagerFragment)
                    .commit();
        }
        if(otherFrag) {
            if ((fragment = fragmentManager.findFragmentById(R.id.page_list)) instanceof PageListFragment)
                pageListFragment = (PageListFragment) fragment;
            else {
                pageListFragment = pageListFragment.newInstance(items);
                fragmentManager.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
              //  ((BaseAdapter)pageListFragment.adapter).notifyDataSetChanged();
            }
        }

    }
    @Override
    public void selectWeb (String urlString){
     //   pageViewerFragment.showUrl(urlString);
      // myFragment = pagerFragment.pageSlider.getAdapter().getItemPosition(pagerFragment.pageSlider.getCurrentItem());
        //items.add(PageViewerFragment.newInstance(urlString));
        pagerFragment.callGoPageViewer(urlString);
       // ((BaseAdapter)pageListFragment.adapter).notifyDataSetChanged();

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
        if(url.equals("about:blank")){
            pageControlFragment.updateUrlString("");
        }
        else{
            pageControlFragment.updateUrlString(url);
        }

       // items.add(url);
    }
    @Override
    public void itemSelected(int index) {
        pagerFragment.clickedFrag(index);
    }
    @Override
    public void newFragNote() {
    //
        //newFrag.add(new instanceof pageViewrFragment);
       // newFrag.add(PageViewerFragment.newInstance(pageControlFragment.retUrl()));
        if(checkNewFragInstance==false) {
            newFrag.add(PageViewerFragment.newInstance(pageControlFragment.retUrl()));
            checkNewFragInstance=true;
        }
        else{
            newFrag.add(new PageViewerFragment());
        }
        pagerFragment.pageSlider.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateTitle(String title, String url) {
        if(otherFrag) {
            //add item to list
            if(url.equals("about:blank")){
                //do nothing
            }
            else if (title == null) {
                if (!(items.contains(url) && !url.equals("about:blank"))) {
                    items.add(url.substring(0, 14));
                    ((BaseAdapter)pageListFragment.adapter).notifyDataSetChanged();
                }
            } else if (!(items.contains(title))) {
                items.add(title);
                 ((BaseAdapter)pageListFragment.adapter).notifyDataSetChanged();
            }
        }
    }
}

