package momenify.proconnect.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.adapter.TabPagerItem;
import momenify.proconnect.adapter.ViewPagerAdapter;

public class FragmentViewPager extends Fragment{
	private List<TabPagerItem> mTabs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabs.add(new TabPagerItem(0, getString(momenify.proconnect.navigationviewpagerliveo.R.string.starred)));
        mTabs.add(new TabPagerItem(1, getString(momenify.proconnect.navigationviewpagerliveo.R.string.important)));
        mTabs.add(new TabPagerItem(2, getString(momenify.proconnect.navigationviewpagerliveo.R.string.documents)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(momenify.proconnect.navigationviewpagerliveo.R.layout.fragment_viewpager, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	ViewPager mViewPager = (ViewPager) view.findViewById(momenify.proconnect.navigationviewpagerliveo.R.id.pager);
    	
    	mViewPager.setOffscreenPageLimit(3); 
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mTabs));

        PagerSlidingTabStrip mSlidingTabLayout = (PagerSlidingTabStrip) view.findViewById(momenify.proconnect.navigationviewpagerliveo.R.id.tabs);
        mSlidingTabLayout.setTextColorResource(momenify.proconnect.navigationviewpagerliveo.R.color.nliveo_white);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
}