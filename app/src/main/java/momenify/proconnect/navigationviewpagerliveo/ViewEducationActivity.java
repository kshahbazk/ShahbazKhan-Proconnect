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
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

import momenify.proconnect.adapter.EducationListViewAdapter;

public class ViewEducationActivity extends ActionBarActivity {


    ListView listview;
    List<ParseObject> listofeducation;
    ParseObject myProfile;
    ProgressDialog mProgressDialog;
    EducationListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_education);
        new RemoteDataTask().execute();
        ActionBar ab = getSupportActionBar();

        ab.setTitle("All Education");
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ViewEducationActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Listing Education");
            // Set progressdialog message
            mProgressDialog.setMessage("Listing...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //Query for all users

              myProfile = ParseUser.getCurrentUser().getParseObject("profile").fetchIfNeeded();
                ParseRelation<ParseObject> query = myProfile.getRelation("educationList");
                //Fill the list of ParseUsers with our query
                listofeducation = query.getQuery().find();



            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.edulistview);
            // Pass the results into ListViewAdapter.java
            adapter = new EducationListViewAdapter(ViewEducationActivity.this,listofeducation);
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
