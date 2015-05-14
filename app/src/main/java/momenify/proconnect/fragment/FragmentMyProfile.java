package momenify.proconnect.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;

import momenify.proconnect.navigationviewpagerliveo.ListActivity;
import momenify.proconnect.navigationviewpagerliveo.LoginActivity;
import momenify.proconnect.navigationviewpagerliveo.R;
import momenify.proconnect.navigationviewpagerliveo.ViewEducationActivity;
import momenify.proconnect.navigationviewpagerliveo.ViewExperienceActivity;
import momenify.proconnect.objects.EditProfileFragmentWrapper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyProfile extends Fragment {

    private boolean mSearchCheck;
    boolean premium;
    private ParseObject myProfile;
    ParseObject currentedu;
    ParseObject currentexp;
    // private FrameLayout rl;


    public FragmentMyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);

        try {


            //creating blank profile
            if(ParseUser.getCurrentUser().getParseObject("profile")
                    .fetchIfNeeded()==null)
            {
                ParseRelation<ParseObject> relation = ParseUser.getCurrentUser()
                        .getRelation("profile");
                ParseObject blankProfile = new ParseObject("profile");
                relation.add(blankProfile);
                blankProfile.saveInBackground();
            }

            myProfile = ParseUser.getCurrentUser().getParseObject("profile")
                    .fetchIfNeeded();

            ParseRelation<ParseObject> query = myProfile.getRelation("educationList");
            currentedu=query.getQuery().getFirst();

            ParseRelation<ParseObject> query2 = myProfile.getRelation("experienceList");
            currentexp=query2.getQuery().getFirst();
            //Fill the list of ParseUsers with our query


        }
        catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

       RelativeLayout edu = (RelativeLayout) v.findViewById(R.id.edu);

        RelativeLayout exp =(RelativeLayout) v.findViewById(R.id.exp);


        premium = ParseUser.getCurrentUser().getBoolean("premium");
        // Locate the TextViews and button in universal_profile.xml
        TextView txtname = (TextView) v.findViewById(R.id.name);
        TextView txtemail = (TextView) v.findViewById(R.id.email);
        TextView premiumstat = (TextView) v.findViewById(R.id.member);
        TextView joined = (TextView) v.findViewById(R.id.join);


        TextView txtschool = (TextView) v.findViewById(R.id.putschool);
        TextView txtdegree = (TextView) v.findViewById(R.id.putdegree);
        TextView txtgradyear = (TextView) v.findViewById(R.id.putGradYear);

        TextView txtcompany = (TextView) v.findViewById(R.id.putcompany);
        TextView txttitle = (TextView) v.findViewById(R.id.putTitle);
        TextView txtstartdate = (TextView) v.findViewById(R.id.putstartDate);
        TextView txtenddate = (TextView) v.findViewById(R.id.putendDate);

        TextView txtcurrentLocale = (TextView) v.findViewById(R.id.putDesiredLocale);

        Button edit = (Button) v.findViewById(R.id.connect_disconnect_update);

        //format the created at Date into a string
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //populate textviews
        txtname.setText(ParseUser.getCurrentUser().getString("name"));
        txtemail.setText(ParseUser.getCurrentUser().getString("email"));
        joined.setText(formatter.format(ParseUser.getCurrentUser().getCreatedAt()));

        if(currentedu!=null) {
            txtschool.setText(currentedu.getString("school"));
            txtdegree.setText(currentedu.getString("degree"));
            txtgradyear.setText(currentedu.getString("gradYear"));
        }

        if(currentexp!=null) {
            txtcompany.setText(currentexp.getString("company"));
            txttitle.setText(currentexp.getString("title"));
            txtstartdate.setText(currentexp.getString("sY"));
            txtenddate.setText(currentexp.getString("eY"));
        }

        txtcurrentLocale.setText(myProfile.getString("currentLocale"));



        //Check to see if user is premium or not
        if (premium) {
            premiumstat.setText("Premium Member");

        } else {
            premiumstat.setText("Standard Member");

        }

        edu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), ViewEducationActivity.class);
                startActivity(i);
            }
        });

        exp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), ViewExperienceActivity.class);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.connect_disconnect_update:

                        Intent i = new Intent(getActivity(), EditProfileFragmentWrapper.class);
                        startActivity(i);

                        break;
                }
            }
        });


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
