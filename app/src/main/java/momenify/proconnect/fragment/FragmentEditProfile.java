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
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

import momenify.proconnect.navigationviewpagerliveo.ListActivity;
import momenify.proconnect.navigationviewpagerliveo.LoginActivity;
import momenify.proconnect.navigationviewpagerliveo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditProfile extends Fragment {

    private boolean mSearchCheck;
    ParseObject updateProfile;
    EditText position;
    EditText emp;
    EditText edu;
    EditText addre;

    public FragmentEditProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_myprofile_edit, container, false);



//        Button save = (Button) v.findViewById(R.id.saveButton);
//        Button exit = (Button) v.findViewById(R.id.exitButton);
//
//         updateProfile = new ParseObject("profile");
//         position = (EditText) v.findViewById(R.id.editdesc);
//         emp = (EditText) v.findViewById(R.id.editemp);
//         edu = (EditText) v.findViewById(R.id.editedu);
//         addre = (EditText) v.findViewById(R.id.editaddre);
//         TextView premiumstat = (TextView) v.findViewById(R.id.editprem);
//
//         email.setText(ParseUser.getCurrentUser().getEmail());
//         name.setText(ParseUser.getCurrentUser().getString("name"));
//
//        if(premium)
//        {
//            premiumstat.setText("Premium");
//            premiumstat.setTextColor(Color.GREEN);
//        }
//        else
//        {
//            premiumstat.setText("Standard");
//            premiumstat.setTextColor(Color.RED);
//        }

//            save.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    updateProfile.put("currentPosition", position.getText().toString());
//                    updateProfile.put("currentEmployer", emp.getText().toString());
//                    updateProfile.put("education", edu.getText().toString());
//                    updateProfile.put("address", addre.getText().toString());
//                    ParseUser.getCurrentUser().put("profile", updateProfile);
//                    updateProfile.saveInBackground();
//                    ParseUser.getCurrentUser().saveInBackground();
//                    Toast.makeText(getActivity(), "Saved Profle", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(getActivity(), NavigationMain.class);
//                    startActivity(i);
//                }
//            });
//
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), NavigationMain.class);
//                startActivity(i);
//            }
//        });

       return v;
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
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            return false;
        }
    };


}
