package momenify.proconnect.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;

import momenify.proconnect.navigationviewpagerliveo.ListActivity;
import momenify.proconnect.navigationviewpagerliveo.LoginActivity;
import momenify.proconnect.navigationviewpagerliveo.R;
import momenify.proconnect.objects.EditProfileFragmentWrapper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyProfile extends Fragment {

    private boolean mSearchCheck;
    boolean premium;
    private FragmentActivity myContext;
   // private FrameLayout rl;


    public FragmentMyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.universal_profile, container, false);


            premium = ParseUser.getCurrentUser().getBoolean("premium");
            // Locate the TextViews and button in universal_profile.xml
            TextView txtname = (TextView) v.findViewById(R.id.UserName);
            TextView txtemail = (TextView) v.findViewById(R.id.emai);
            TextView premiumstat = (TextView) v.findViewById(R.id.prem);

            Button edit = (Button) v.findViewById(R.id.connect_disconnect);

            //populate textviews
            edit.setText("Edit Profile");
            txtname.setText(ParseUser.getCurrentUser().getString("name"));
            txtemail.setText(ParseUser.getCurrentUser().getString("email"));
            //Check to see if user is premium or not
        if(premium)
        {
            premiumstat.setText("Premium");
            premiumstat.setTextColor(Color.GREEN);
        }
        else
        {
            premiumstat.setText("Standard");
            premiumstat.setTextColor(Color.RED);
        }
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.connect_disconnect:

                            Intent i = new Intent(getActivity(), EditProfileFragmentWrapper.class);
                            startActivity(i);

                            break;
                    }
                }
            });



        return v;
    }



    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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

        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
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

            if (mSearchCheck) {
                if (s != null) {
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

            return false;
        }


    };


}
