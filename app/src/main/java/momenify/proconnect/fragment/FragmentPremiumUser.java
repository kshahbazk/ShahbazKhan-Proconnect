package momenify.proconnect.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import momenify.proconnect.navigationviewpagerliveo.NavigationMain;
import momenify.proconnect.navigationviewpagerliveo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPremiumUser extends Fragment {

    private ParseUser current;
    private boolean premium;


    public FragmentPremiumUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_premium_user, container, false);

        //Initialize all TextViews and Button

        TextView isUserPremium = (TextView) rootLayout.findViewById(R.id.premiumStatus);
       TextView selectionMessage = (TextView) rootLayout.findViewById(R.id.premiumMessage);
       Button becomePremium = (Button) rootLayout.findViewById(R.id.upgradeButton);


        //Get Current User
         current = ParseUser.getCurrentUser();

        //get the premium field from the current parse user
        premium = current.getBoolean("premium");

        // Check to see if User is premium, if premium give downgrade option, else give upgrade options
        if (premium) {
            isUserPremium.setText("You are a premium user");
            selectionMessage.setText("Would you like to go back to a standard user?");
            becomePremium.setText("Downgrade Premium");
            becomePremium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.put("premium", false);
                    Toast.makeText(getActivity(), "Downgraded",
                            Toast.LENGTH_LONG).show();
                    current.saveInBackground();
                    Intent i = new Intent(getActivity(), NavigationMain.class);
                    startActivity(i);

                }
            });
        } else {
            isUserPremium.setText("You are not a premium user");
            isUserPremium.setTextColor(Color.RED);
            selectionMessage.setText("Would you like to become a premium member?");
            becomePremium.setText("Upgrade Premium");
            becomePremium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.put("premium", true);
                    Toast.makeText(getActivity(), "Upgraded",
                            Toast.LENGTH_LONG).show();
                    current.saveInBackground();
                    Intent i = new Intent(getActivity(), NavigationMain.class);
                    startActivity(i);

                }
            });
        }

        return rootLayout;
    }


}
