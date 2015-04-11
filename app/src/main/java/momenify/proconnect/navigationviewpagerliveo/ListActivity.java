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

import momenify.proconnect.objects.AUserProfile;
import momenify.proconnect.adapter.ListViewAdapter;

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

                ParseQuery<ParseUser> UserQuery = ParseUser.getQuery();

//                query.whereContains("name", searchData);
//                query.findInBackground(new FindCallback<ParseObject>() {
//                    public void done(List<ParseObject> objList,ParseException e) {
//                        if (e == null) {
//                            Log.d("score","@@@@Retrieved " + objList.size()+ " scores");
//
//                        } else {
//                            Log.d("score", "@@@Error: " + e.getMessage());
//                        }
//                    }
//                });

                //tob=UserQuery.find();

                Bundle extras = getIntent().getExtras();
                String thesearch = extras.getString("Search");
                if(thesearch.contains("@"))
                {
                ob=UserQuery.whereMatches("email", "("+thesearch+")", "i").find();
                    if(ob==null)
                    {

                    }
//                UserQuery.findInBackground(new FindCallback<ParseUser>() {
//                    public void done(List<ParseUser> objList,ParseException e) {
//                        if (e == null) {
//                            Log.d("email","@@@@Retrieved " + objList.size()+ " scores");
//
//                            ob=objList;
//
//                        } else {
//                            Log.d("email", "@@@Error: " + e.getMessage());
//                        }
//                    }
//                });
                }
                else
                {
                   ob = UserQuery.whereMatches("name", "("+thesearch+")", "i").find();
                    if(ob==null)
                    {

                    }
//                    UserQuery.findInBackground(new FindCallback<ParseUser>() {
//                        public void done(List<ParseUser> objList,ParseException e) {
//                            if (e == null) {
//                                Log.d("name","@@@@Retrieved " + objList.size()+ " scores");
//
//                                ob=objList;
//
//                            } else {
//                                Log.d("name", "@@@Error: " + e.getMessage());
//                            }
//                        }
//                    });
                }




                // Locate the class table named "User" in Parse.com
//                ParseQuery<ParseUser> query = new ParseQuery<ParseUser>(
//                        "User");
                // Locate the column named "name" in Parse.com and order list
                // by ascending
                //query.orderByAscending("name");
              //  ob = query.find();

//                ParseUser me = ParseUser.getCurrentUser();
//
//                ob.add(me);
//                ob.add(me);
//                ob.add(me);
//                ob.add(me);
//                ob.add(me);

                for (ParseUser user : ob) {
                    // Locate images in flag column
                    //ParseFile image = (ParseFile) user.get("flag");

                    AUserProfile map = new AUserProfile();
                    map.setName((String) user.get("name"));
                    map.setEmail((String) user.get("email"));
                    map.setPosition((String) user.get("position"));
                    map.setCompany((String) user.get("company"));
                  //  map.setFlag(image.getUrl());
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
