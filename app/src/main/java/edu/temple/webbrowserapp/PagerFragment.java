package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {
    private static final String FRAG_KEY = "newFrag";
    View frame;
    ViewPager pageSlider;
    Adapter adapter;
    PageViewerFragment pageViewerFragment;
    BrowserActivity browserActivity;
    ArrayList<PageViewerFragment> newFrag;
    private PageViewerFragment.updatable parentActivity;
   // private updateTitable parentActivity;
    public PagerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PagerFragment newInstance(ArrayList<PageViewerFragment> newFrag) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
       // newFrag.putString(ARG_PARAM1, param1);
        args.putSerializable(FRAG_KEY, newFrag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          newFrag = (ArrayList)getArguments().getSerializable(FRAG_KEY);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       frame = inflater.inflate(R.layout.fragment_pager, container, false);
     //  Fragment fragment;

       // newFrag = new ArrayList<>();
        pageSlider = frame.findViewById(R.id.pageSlider);
        //pageSlider.getAdapter().notifyDataSetChanged();
        //if ((fragment = getChildFragmentManager().findFragmentById(R.id.page_control)) instanceof PageControlFragment)
           // pageViewerFragment = (PageViewerFragment) fragment;
       // else {
           // adapter =
            pageSlider.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
                @NonNull
                @Override
                public Fragment getItem(int position) {
                    return newFrag.get(position);
                }
                @Override
                public int getCount() {
                    return newFrag.size();
                }

             /*   @Override
                public int getItemPosition(@NonNull Object object) {
                     //super.getItemPosition(object);
                     return POSITION_NONE;
                }*/
            });
            pageSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                   // browserActivity.updateUrlString((newFrag.get(position)).getUrl());
                   // browserActivity.updateTitle((newFrag.get(position)).getTitle(), (newFrag.get(position)).getUrl() );

                }
                @Override
                public void onPageSelected(int position) {
                    //browserActivity.updateTitle((newFrag.get(position)).getTitle(), (newFrag.get(position)).getUrl() );
                    //browserActivity.updateUrlString((newFrag.get(position)).getUrl());
                    //pageSlider.getAdapter().notifyDataSetChanged();
                }
                @Override
                public void onPageScrollStateChanged(int state) {

                }

            });
       // }
       // pageSlider.getAdapter().notifyDataSetChanged();
        //PageViewerFragment mypage = new PageViewerFragment();
       // newFrag.add(new PageViewerFragment());

        return frame;
    }
   /* @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        pageSlider.onSaveInstanceState();
                //saveState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            pageSlider.onRestoreInstanceState(savedInstanceState);
                    //restoreState(savedInstanceState);
        }
    }*/
    public void addFrag(){
        if(newFrag.size()!=0) {
            pageSlider.getAdapter().notifyDataSetChanged();
        }
    }
    public void clickedFrag(int position){
        //show fragment at clicked position
        Fragment myFragment;
       // myFragment = pageSlider.getCurrentItem();
      //  pageSlider.getCurrentItem()
        pageSlider.setCurrentItem(position);
    }
    public void callGoPageViewer(String urlString){
        if(newFrag.size()!=0) {
            newFrag.get(pageSlider.getCurrentItem()).showUrl(urlString);
            pageSlider.getAdapter().notifyDataSetChanged();
        }
    }
    public void callBackPageViewer(){
        if(newFrag.size()!=0) {
            newFrag.get(pageSlider.getCurrentItem()).showBack();
        }
    }
    public void callForwardPageViewer(){
            if(newFrag.size()!=0) {
                newFrag.get(pageSlider.getCurrentItem()).showForward();
            }
    }

}
