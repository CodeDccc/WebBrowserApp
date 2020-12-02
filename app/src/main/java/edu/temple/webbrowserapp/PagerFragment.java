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

import java.util.ArrayList;

public class PagerFragment extends Fragment {
    private static final String FRAG_KEY = "newFrag";
    private View frame;
    private ViewPager pageSlider;
    private updates parentActivity;
    ArrayList<PageViewerFragment> newFrag;

    public PagerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PagerFragment newInstance(ArrayList<PageViewerFragment> newFrag) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(FRAG_KEY, newFrag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          newFrag = (ArrayList)getArguments().getSerializable(FRAG_KEY);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof updates) {
            parentActivity = (updates) context;
        } else {
            throw new RuntimeException("You must implement updates interface to attach this fragment");
        }
    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       frame = inflater.inflate(R.layout.fragment_pager, container, false);
        pageSlider = frame.findViewById(R.id.pageSlider);
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

                @Override
                public int getItemPosition(@NonNull Object object) {
                    if (newFrag.contains(object))
                        return newFrag.indexOf(object);
                    else
                        return POSITION_NONE;
                }
            });
            pageSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
                @Override
                public void onPageSelected(int position){
                    parentActivity.updateTitle((newFrag.get(position)).getTitle());
                    parentActivity.updateUrlString((newFrag.get(position)).getUrl());
                }
                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        return frame;
    }

   public void notifyWebs() {
       pageSlider.getAdapter().notifyDataSetChanged();
   }
    public void clickedFrag(int position){
        pageSlider.setCurrentItem(position);
    }
    public void callGoPageViewer(String urlString){
            newFrag.get(pageSlider.getCurrentItem()).showUrl(urlString);
    }
    public void callBackPageViewer(){
            newFrag.get(pageSlider.getCurrentItem()).showBack();
    }
    public void callForwardPageViewer(){
            newFrag.get(pageSlider.getCurrentItem()).showForward();
    }
    interface updates{
        void updateUrlString(String urlString);
        void updateTitle(String title);
    }
    public String getCurrentUrl(){
        return (newFrag.get(pageSlider.getCurrentItem())).getUrl();
    }
    public String getCurrentTitle() {
        return (newFrag.get(pageSlider.getCurrentItem())).getTitle();
    }

}
