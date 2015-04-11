package momenify.proconnect.objects;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.navigationviewpagerliveo.NavigationMain;
import momenify.proconnect.navigationviewpagerliveo.R;

public class UserProfileView extends ActionBarActivity {

    String name;
    String email;
    String company;

    String desc;
    String education;
    boolean friend;
    Button disOrSend;
    ParseUser currentUser;
    ParseUser selectedUser;
    ParseObject newRequest;
    List<ParseUser> checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_profile);

        //Grab Intent from Previous Activity
        Intent i = getIntent();
        // Get the result of name
        name = i.getStringExtra("name");
        // Get the result of email
        email = i.getStringExtra("email");
        // Get the result of position
        desc = i.getStringExtra("position");
        // Get the result of position
        company = i.getStringExtra("company");

        // Get the current user
        currentUser = ParseUser.getCurrentUser();


        // Query for the user we are looking at.
        ParseQuery<ParseUser> UserQuery = ParseUser.getQuery();
        // Get the current users connections
        ParseRelation<ParseUser> query = currentUser.getRelation("connections");

        try {

            selectedUser = UserQuery.whereEqualTo("email", email).getFirst();

            checker = query.getQuery().find();

        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        // Look through the connections array and see if there is a match with the selectedUser
        for (ParseUser s : checker) {
            if (s.getObjectId().equals(selectedUser.getObjectId())) {
                friend = true;
            } else {
                friend = false;
            }
        }


        // Locate the TextViews and button in universal_profile.xml
        TextView txtname = (TextView) findViewById(R.id.UserName);
        TextView txtemail = (TextView) findViewById(R.id.emai);
//        TextView txtdesc = (TextView) findViewById(R.id.desc);
//        TextView txtcompany = (TextView) findViewById(R.id.emp);
        disOrSend = (Button) findViewById(R.id.connect_disconnect);

        //populate textviews
        txtname.setText(name);
        txtemail.setText(email);
        //txtdesc.setText(desc);
        //txtcompany.setText(company);


//        // If you search for yourself
//        if (currentUser.equals(selectedUser))
//        {
//            disOrSend.setVisibility(View.GONE);
//        }

        // if connected then give option to disconnect, if not then give option to send connection request
        if(friend)
        {
           disOrSend.setText("Disconnect");
            disOrSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    disconnect(currentUser.getObjectId(), selectedUser.getObjectId());


                    Toast.makeText(UserProfileView.this, "Disconnected From This User",
                            Toast.LENGTH_LONG).show();

                    Intent i = new Intent(UserProfileView.this, NavigationMain.class);
                    startActivity(i);

                }
            });
        }
        else
        {
            disOrSend.setText("Connect");
            disOrSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newRequest = new ParseObject("connectionRequest");

                    newRequest.put("fromUser", currentUser);

                    newRequest.put("toUser", selectedUser);

                    newRequest.saveInBackground();

                    Toast.makeText(UserProfileView.this, "Connection Request Sent",
                            Toast.LENGTH_LONG).show();

                    Intent i = new Intent(UserProfileView.this, NavigationMain.class);
                    startActivity(i);

                }
            });
        }

    }


    // The async task which sends the Current and selected users Objects id via http post, to Android.php,
    // which handle the disconnect.
    private void disconnect(String CuObjectId, String OuObjectId)
    {

        class disconnectAsync extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String paramObjectId = params[0];

                String paramObjectId1 = params[1];

                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://proconnect.thefreebit.com/android.php");

                BasicNameValuePair currentUserNameValuePair = new BasicNameValuePair("cuid", paramObjectId);
                BasicNameValuePair otherUserNameValuePair = new BasicNameValuePair("ufid", paramObjectId1);


                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(currentUserNameValuePair);
                nameValuePairList.add(otherUserNameValuePair);

                try {
                    // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                    //This is typically useful while sending an HTTP POST request.
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        // HttpResponse is an interface just like HttpPost.
                        //Therefore we can't initialize them
                        HttpResponse httpResponse = httpClient.execute(httpPost);


                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder stringBuilder = new StringBuilder();

                        String bufferedStrChunk = null;

                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            stringBuilder.append(bufferedStrChunk);
                        }

                        return stringBuilder.toString();

                    } catch (ClientProtocolException cpe) {
                        System.out.println("First Exception because of HttpResponese :" + cpe);
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        System.out.println("Second Exception because of HttpResponse :" + ioe);
                        ioe.printStackTrace();
                    }

                } catch (UnsupportedEncodingException uee) {
                    System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                    uee.printStackTrace();
                }

                return null;

            }




        }

        disconnectAsync sendPostReqAsyncTask = new disconnectAsync();
        sendPostReqAsyncTask.execute(CuObjectId, OuObjectId);
    }
}
