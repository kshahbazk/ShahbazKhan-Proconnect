package momenify.proconnect.navigationviewpagerliveo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.adapter.ListViewAdapter;
import momenify.proconnect.objects.AUserProfile;

public class ListActivity extends ActionBarActivity {

    ListView listview;
    List<ParseUser> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<AUserProfile> searchedUsers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        new RemoteDataTask().execute();
    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ListActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Listing Users");
            // Set progressdialog message
            mProgressDialog.setMessage("Searching...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            searchedUsers = new ArrayList<AUserProfile>();
            try {
                //Query for all users
                ParseQuery<ParseUser> UserQuery = ParseUser.getQuery();
                // Create a bundle in which you can recieve the search string from our search bar
                Bundle extras = getIntent().getExtras();
                String thesearch = extras.getString("Search");
                if (thesearch.contains("@")) {
                    ob = UserQuery.whereMatches("email", "(" + thesearch + ")", "i").find();

                } else {
                    ob = UserQuery.whereMatches("name", "(" + thesearch + ")", "i").find();

                }


                for (ParseUser user : ob) {

                    AUserProfile map = new AUserProfile();
                    map.setName((String) user.get("name"));
                    map.setEmail((String) user.get("email"));
                    map.setPosition((String) user.get("position"));
                    map.setCompany((String) user.get("company"));
                    searchedUsers.add(map);
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
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(ListActivity.this,
                    searchedUsers);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

}
