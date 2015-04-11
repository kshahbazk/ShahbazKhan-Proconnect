package momenify.proconnect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;

import momenify.proconnect.navigationviewpagerliveo.ListActivity;
import momenify.proconnect.navigationviewpagerliveo.LoginActivity;
import momenify.proconnect.navigationviewpagerliveo.R;




public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    private ParseUser myUser;
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
	
	public FragmentMain newInstance(String text){
		FragmentMain mFragment = new FragmentMain();
		Bundle mBundle = new Bundle();
		mBundle.putString(TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        myUser = ParseUser.getCurrentUser();

        TextView mTxtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        mTxtTitle.setText(getArguments().getString(TEXT_FRAGMENT));

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);		
		inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

		menu.findItem(R.id.menu_add).setVisible(true);
		menu.findItem(R.id.menu_search).setVisible(true);
  	    
		mSearchCheck = false;	
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {

		case R.id.menu_add:

            ParseUser.logOut();
            myUser = null;

            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
			break;				
		
		case R.id.menu_search:
			mSearchCheck = true;

			break;
		}		
		return true;
	}	

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {

           if (mSearchCheck){
               if(s!=null) {
                   Intent i2 = new Intent(getActivity(), ListActivity.class);
                   i2.putExtra("Search", s);
                   startActivity(i2);
               }
               // implement your search here
           }
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
//           if (mSearchCheck){
//               if(s!=null) {
//                   Intent i2 = new Intent(getActivity(), ListActivity.class);
//                   startActivity(i2);
//               }
//               // implement your search here
//           }
           return false;
       }
   };
}
