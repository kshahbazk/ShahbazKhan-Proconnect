package momenify.proconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import momenify.proconnect.objects.AUserProfile;
import momenify.proconnect.navigationviewpagerliveo.R;
import momenify.proconnect.objects.UserProfileView;

/**
 * Created by shahbazkhan on 3/23/15.
 */
public class ListViewAdapter extends BaseAdapter
{
    // Declare Variables
    Context context;
    LayoutInflater inflater;

    private List<AUserProfile> searchedUsers = null;
    private ArrayList<AUserProfile> arraylist;

    public ListViewAdapter(Context context,
                           List<AUserProfile> searchedUsers) {
        this.context = context;
        this.searchedUsers = searchedUsers;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<AUserProfile>();
        this.arraylist.addAll(searchedUsers);

    }

    public class ViewHolder {
        TextView name;
        TextView email;
        TextView createdAt;
       // ImageView flag;
    }

 //   @Override
    public int getCount() {
        return searchedUsers.size();
    }

 //   @Override
    public Object getItem(int position) {
        return searchedUsers.get(position);
    }

 //   @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.nameCR);
            holder.email = (TextView) view.findViewById(R.id.emailCR);
            holder.createdAt= (TextView) view.findViewById(R.id.joinedCR);

            // Locate the ImageView in listview_item.xml
           // holder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(searchedUsers.get(position).getName());
        holder.email.setText(searchedUsers.get(position).getEmail());
        holder.createdAt.setText(searchedUsers.get(position).getCreatedAt());

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // Send single item click data to UserProfileView Class
                Intent intent = new Intent(context, UserProfileView.class);


                // Pass all data name
                intent.putExtra("name",
                        (searchedUsers.get(position).getName()));
                // Pass all data email
                intent.putExtra("email",
                        (searchedUsers.get(position).getEmail()));
                //pass the objectId
                intent.putExtra("objectId", searchedUsers.get(position).getObjectId());
                // Start SingleItemView Class

                intent.putExtra("joined", searchedUsers.get(position).getCreatedAt());
                context.startActivity(intent);

            }
        });
        return view;
    }


}
