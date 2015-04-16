package momenify.proconnect.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.adapter.ListViewAdapter;
import momenify.proconnect.navigationviewpagerliveo.ListActivity;
import momenify.proconnect.navigationviewpagerliveo.LoginActivity;
import momenify.proconnect.navigationviewpagerliveo.R;
import momenify.proconnect.objects.AUserProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyConnections extends Fragment {

    ListView listview;
    private boolean mSearchCheck;
    List<ParseUser> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<AUserProfile> myConnections = null;

    public FragmentMyConnections() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myconnections, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Connection Requests");
            // Set progressdialog message
            mProgressDialog.setMessage("Pulling Requests");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            myConnections = new ArrayList<AUserProfile>();
            try {
                // Get the relations of the current user, which are ParseUsers
                ParseRelation<ParseUser> query = ParseUser.getCurrentUser().getRelation("connections");
                //Fill the list of ParseUsers with our query
                ob = query.getQuery().find();

                // Map the into User Profile

                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (ParseUser user : ob) {

                    AUserProfile map = new AUserProfile();
                    map.setName((String) user.get("name"));
                    map.setEmail((String) user.get("email"));
                    map.setCreatedAt(formatter.format(user.getCreatedAt()));
                    myConnections.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) getView().findViewById(R.id.listView3);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(getActivity(), myConnections);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
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

