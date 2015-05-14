package momenify.proconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.navigationviewpagerliveo.R;
import momenify.proconnect.objects.AUserProfile;
import momenify.proconnect.objects.Acceptance;

/**
 * Created by shahbazkhan on 3/31/15.
 */
public class CRListViewAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;

    private List<AUserProfile> searchedUsers = null;
    private List<ParseObject> ob;
    private ArrayList<AUserProfile> arraylist;
    private ParseObject currentRequest;




    public CRListViewAdapter(Context context,
                           List<AUserProfile> searchedUsers, List<ParseObject> ob) {
        this.ob=ob;
        this.context = context;
        this.searchedUsers = searchedUsers;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<AUserProfile>();
        this.arraylist.addAll(searchedUsers);


    }

    public class ViewHolder {
        TextView name;
        TextView email;
        TextView createdat;

    }

       @Override
    public int getCount() {
        return searchedUsers.size();
    }

      @Override
    public Object getItem(int position) {
        return searchedUsers.get(position);
    }

       @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.connection_request_listitem, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.nameCR);
            holder.email= (TextView) view.findViewById(R.id.emailCR);
            holder.createdat = (TextView) view.findViewById(R.id.joinedCR);

            
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


            // Set the results into TextViews
            holder.name.setText(searchedUsers.get(position).getName());
            holder.email.setText(searchedUsers.get(position).getEmail());
            holder.createdat.setText(searchedUsers.get(position).getCreatedAt());



        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // Send single item click data to UserProfileView Class

                currentRequest = ob.get(position);

            Intent intent = new Intent(context, Acceptance.class);

            // Pass all data name
            intent.putExtra("objectId", currentRequest.getObjectId());

            // Start Acceptance Class
            context.startActivity(intent);

        }
        });
        return view;
    }


}
