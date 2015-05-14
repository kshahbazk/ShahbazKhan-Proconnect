package momenify.proconnect.navigationviewpagerliveo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginDispatchActivity;

import java.util.ArrayList;
import java.util.List;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;
import momenify.proconnect.fragment.BlankFragment;
import momenify.proconnect.fragment.FragmentMain;
import momenify.proconnect.fragment.FragmentMyConnections;
import momenify.proconnect.fragment.FragmentMyProfile;
import momenify.proconnect.fragment.FragmentPremiumUser;
import momenify.proconnect.fragment.FragmentViewPager;

public class NavigationMain extends NavigationLiveo implements NavigationLiveoListener {

    private List<String> mListNameItem;
    private ParseUser myUser;
    private String name;
    private String email;
    private List<ParseUser> ob;

    private static final int LOGIN_REQUEST = 0;

    @Override
    public void onInt(Bundle bundle) {

        this.setNavigationListener(this);

        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, getString(R.string.inbox));
        mListNameItem.add(1, "My Connections");
        mListNameItem.add(2, getString(R.string.sent_mail));
        mListNameItem.add(3, "Premium Features");
        mListNameItem.add(4, getString(R.string.more_markers)); //This item will be a subHeader
        mListNameItem.add(5, getString(R.string.trash));
        mListNameItem.add(6, getString(R.string.spam));

        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, R.drawable.ic_person_grey600_48dp);
        mListIconItem.add(1, R.drawable.ic_group_grey600_48dp);;
        mListIconItem.add(2, R.drawable.ic_pages_grey600_48dp);
        mListIconItem.add(3, R.drawable.ic_party_mode_grey600_48dp);
        mListIconItem.add(4, 0); //When the item is a subHeader the value of the icon 0
        mListIconItem.add(5, R.drawable.ic_mood_grey600_48dp);
        mListIconItem.add(6, R.drawable.ic_plus_one_grey600_48dp);
        ParseRelation<ParseUser> query = ParseUser.getCurrentUser().getRelation("connections");
        //Fill the list of ParseUsers with our query
        try {

             ob = query.getQuery().find();
        }catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        List<Integer> mListHeaderItem = new ArrayList<>(); //indicate who the items is a subheader
        mListHeaderItem.add(4);


        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        mSparseCounterItem.put(1, 1);


        this.setElevationToolBar(this.getCurrentPosition() != 1 ? 15 : 0);

        this.setFooterInformationDrawer(R.string.settings, R.drawable.ic_action_settings);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }


    public class SampleDispatchActivity extends ParseLoginDispatchActivity {
        @Override
        protected Class<?> getTargetClass() {
            return NavigationMain.class;
        }
    }

    @Override
    public void onUserInformation() {

        myUser = ParseUser.getCurrentUser();
        name = myUser.getString("name");
        email = myUser.getEmail();

        this.mUserName.setText(name);
        this.mUserEmail.setText(email);
        this.mUserPhoto.setImageResource(R.drawable.mys);
        this.mUserBackground.setImageResource(R.drawable.ic_user_background);

    }

    @Override
    public void onItemClickNavigation(int position, int layoutContainerId) {

        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                mFragment = new FragmentMyProfile();
                break;
            case 1:
                mFragment = new FragmentViewPager();
                break;

            case 2:
                mFragment = new BlankFragment();
                break;
            case 3:
                mFragment = new FragmentMyConnections();
                break;
            case 5:
                mFragment = new FragmentPremiumUser();
                break;

            default:
                mFragment = new FragmentMain().newInstance(mListNameItem.get(position));
        }

        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
        }

        setElevationToolBar(position != 1 ? 15 : 0);
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {
        switch (position) {
            case 0:
                menu.findItem(R.id.menu_add).setVisible(!visible);
                menu.findItem(R.id.menu_search).setVisible(!visible);
                break;
        }
    }

    @Override
    public void onClickFooterItemNavigation(View v) {
        Toast.makeText(this, R.string.open_settings, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickUserPhotoNavigation(View v) {

    }
}
