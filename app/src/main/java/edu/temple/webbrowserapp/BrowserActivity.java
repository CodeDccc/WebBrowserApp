package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;


public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebSelectable, PageViewerFragment.updatable {
    PageViewerFragment pageViewerFragment;
    PageControlFragment pageControlFragment;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // pageViewerFragment = PageViewerFragment.newInstance(urlString);
        // pageViewerFragment = new PageViewerFragment();
        fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        if ((fragment = fragmentManager.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) fragment;
        else {
            pageControlFragment = new PageControlFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }

        if ((fragment = fragmentManager.findFragmentById(R.id.page_display)) instanceof PageViewerFragment)
            pageViewerFragment = (PageViewerFragment) fragment;
        else {
            pageViewerFragment = new PageViewerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.pagerFragment, pageViewerFragment)
                    .commit();
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

}

