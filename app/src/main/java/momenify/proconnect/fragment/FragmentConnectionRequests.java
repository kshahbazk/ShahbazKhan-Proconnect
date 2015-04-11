package momenify.proconnect.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.adapter.CRListViewAdapter;
import momenify.proconnect.navigationviewpagerliveo.R;
import momenify.proconnect.objects.AUserProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConnectionRequests extends Fragment {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    CRListViewAdapter adapter;
    private List<AUserProfile> connectionRequests = null;


    public FragmentConnectionRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_friends_requests, container, false);


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
            connectionRequests = new ArrayList<AUserProfile>();
            try {
                 // query all connection requests from parse.com
                ParseQuery<ParseObject> query = ParseQuery.getQuery("connectionRequest");

                 query.whereEqualTo("toUser", ParseUser.getCurrentUser());

                 query.orderByAscending("createdAt");

                  ob = query.find();

                // Map the into User Profile

                  for (ParseObject user : ob) {
                    // Locate images in flag column
                    //ParseFile image = (ParseFile) user.get("flag");

                    AUserProfile map = new AUserProfile();
                    map.setName((String) user.getParseUser("fromUser").fetchIfNeeded().get("name"));
                   // map.setEmail((String) user.get("email"));
                   // map.setPosition((String) user.get("position"));
                    //map.setCompany((String) user.get("company"));
                    //  map.setFlag(image.getUrl());
                    connectionRequests.add(map);
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
            listview = (ListView) getView().findViewById(R.id.listview1);
            // Pass the results into ListViewAdapter.java
            adapter = new CRListViewAdapter(getActivity(),
                    connectionRequests, ob);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }


}
