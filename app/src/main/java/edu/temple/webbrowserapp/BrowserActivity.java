package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import static java.lang.System.exit;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otherFrag = findViewById(R.id.page_list) != null;
       // pageViewerFragment = PageViewerFragment.newInstance(urlString);
       // pageViewerFragment = findViewById(R.id.pageViewer);
        // pageViewerFragment = new PageViewerFragment();
        items = new ArrayList<>();
       // items.add("https://nba.com");

       // pageListFragment.listView.Adapter
       // notifyDataSetChanged();
        newFrag = new ArrayList<>();
       /*if(newFrag.size()==0){
            newFrag.add(new PageViewerFragment());
            pagerFragment.pageSlider.getAdapter().notifyDataSetChanged();
        }*/
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
            }
        }
       /* if(newFrag.size()==0){
            newFrag.add(new PageViewerFragment());
            //pagerFragment.pageSlider.getAdapter().notifyDataSetChanged();
        }*/
    }
    @Override
    public void selectWeb (String urlString){
     //   pageViewerFragment.showUrl(urlString);
      // myFragment = pagerFragment.pageSlider.getAdapter().getItemPosition(pagerFragment.pageSlider.getCurrentItem());
        //items.add(PageViewerFragment.newInstance(urlString));
       // pageViewerFragment.showUrl(urlString);
        pagerFragment.callGoPageViewer(urlString);

    }
    @Override
    public void selectBack() {
        //pageViewerFragment.showBack();
        pagerFragment.callBackPageViewer();
    }
    @Override
    public void selectForward() {
        //pageViewerFragment.showForward();
        pagerFragment.callForwardPageViewer();
    }
    @Override
    public void updateUrlString(String url) {
        pageControlFragment.updateUrlString(url);
       // items.add(url);
    }
    @Override
    public void itemSelected(int index) {
        pagerFragment.clickedFrag(index);
    }
    @Override
    public void newFragNote() {
     newFrag.add(new PageViewerFragment());
        //newFrag.add(new instanceof pageViewrFragment);
       // newFrag.add(PageViewerFragment.newInstance(urlString));
     pagerFragment.pageSlider.getAdapter().notifyDataSetChanged();
     //notifyDataSetChanged();
  //   pagerFragment.addFrag();
    }

    @Override
    public void updateTitle(String title, String url) {
        if(otherFrag) {
            //add item to list
            if (title == null) {
                if (!(items.contains(url))) {
                    items.add(url.substring(0, 14));
                    ((BaseAdapter)pageListFragment.adapter).notifyDataSetChanged();
                    // pageListFragment.notifyChange();
                }
            } else if (!(items.contains(title))) {
                items.add(title);
                //pageListFragment.notifyChange();
                 ((BaseAdapter)pageListFragment.adapter).notifyDataSetChanged();
            }
        }
    }
}

