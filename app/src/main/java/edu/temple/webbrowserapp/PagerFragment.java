package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    PageViewerFragment pageViewerFragment;
    ArrayList<PageViewerFragment> newFrag;
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

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newFrag = new ArrayList<>();
       // newBrowserBtn = frame.findViewById(R.id.newBrowserBtn);
        pagerFragment = frame.findViewById(R.id.pagerFragment);
        Fragment fragment;
        if ((fragment = getChildFragmentManager().findFragmentById(R.id.page_control)) instanceof PageViewerFragment) {
            pageViewerFragment = (PageViewerFragment) fragment;
        }
        else{
                pageViewerFragment = new PageViewerFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.pagerFragment, pageViewerFragment)
                        .commit();
            }
                    // pagerFragment = findFragmentById(R.id.pagerFragment);
            newFrag = new ArrayList<>();
            pagerFragment.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
                @NonNull
                @Override
                public Fragment getItem(int position) {
                    return newFrag.get(position);
                }

                @Override
                public int getCount() {
                    return newFrag.size();
                }
            });
       /* newBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFrag.add(new PageViewerFragment());
                pagerFragment.getAdapter().notifyDataSetChanged();
            }
        });*/



    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       frame = inflater.inflate(R.layout.fragment_pager, container, false);
       Fragment fragment;

      //  newFrag = new ArrayList<>();
        pageSlider = frame.findViewById(R.id.pageSlider);
        //if ((fragment = getChildFragmentManager().findFragmentById(R.id.page_control)) instanceof PageControlFragment)
           // pageViewerFragment = (PageViewerFragment) fragment;
       // else {
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
            });
       // }
        return frame;
    }
}