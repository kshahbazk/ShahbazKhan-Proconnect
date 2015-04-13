package momenify.proconnect.adapter;

import android.support.v4.app.Fragment;

import momenify.proconnect.fragment.FragmentConnectionRequests;
import momenify.proconnect.fragment.FragmentMain;
import momenify.proconnect.fragment.FragmentMyConnections;

public class TabPagerItem {
	
	private final CharSequence mTitle;
    private final int position;
        
    private Fragment[] listFragments;
    public TabPagerItem(int position, CharSequence title) {
        this.mTitle = title;
        this.position = position;

        listFragments = new Fragment[] {new FragmentMain().newInstance(title.toString()), new FragmentMyConnections(), new FragmentConnectionRequests()};
    }

    public Fragment createFragment() {
		return listFragments[position];
    }

    public CharSequence getTitle() {
        return mTitle;
    }
}
