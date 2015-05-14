package momenify.proconnect.navigationviewpagerliveo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
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
        ActionBar ab = getSupportActionBar();

        ab.setTitle("Search Results...");
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


                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (ParseUser user : ob) {

                    AUserProfile map = new AUserProfile();
                    map.setName( user.getString("name"));
                    map.setEmail( user.getString("email"));
                    map.setCreatedAt(formatter.format(user.getCreatedAt()));
                    map.setObjectId(user.getObjectId());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_education, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
