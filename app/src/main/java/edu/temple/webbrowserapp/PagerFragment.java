package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {
    View frame;
    ViewPager pagerFragment;
   // PagerFragment pagerFragment;
    ArrayList<PageViewerFragment> newFrag;
    public PagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PagerFragment newInstance(String param1, String param2) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
         ViewPager fragment;
         if ((fragment = getChildFragmentManager().findFragmentById(R.id.page_control)) instanceof PagerFragment) {
             pagerFragment = (ViewPager) fragment;
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
         }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         inflater.inflate(R.layout.fragment_pager, container, false);
        Fragment fragment;
        if ((fragment = getChildFragmentManager().findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pagerFragment = (PagerFragment) fragment;
        //pagerFragment = frame.findViewById(R.id.pagerFragment);
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
        frame.findViewById(R.id.newBrowserBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFrag.add(new PageViewerFragment());
                pagerFragment.getAdapter().notifyDataSetChanged();
            }
        });
        return frame;
    }
}