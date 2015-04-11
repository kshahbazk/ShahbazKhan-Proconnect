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
   // ImageLoader imageLoader;
    private List<AUserProfile> searchedUsers = null;
    private ArrayList<AUserProfile> arraylist;

    public ListViewAdapter(Context context,
                           List<AUserProfile> searchedUsers) {
        this.context = context;
        this.searchedUsers = searchedUsers;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<AUserProfile>();
        this.arraylist.addAll(searchedUsers);
        //imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView name;
        TextView email;
        TextView position;
        TextView company;
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
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.email = (TextView) view.findViewById(R.id.email);
            holder.position = (TextView) view.findViewById(R.id.position);
            holder.company = (TextView) view.findViewById(R.id.company);
            // Locate the ImageView in listview_item.xml
           // holder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(searchedUsers.get(position).getName());
        holder.email.setText(searchedUsers.get(position).getEmail());
        holder.position.setText(searchedUsers.get(position)
                .getPosition());
        holder.company.setText(searchedUsers.get(position)
                .getCompany());
        // Set the results into ImageView
       // imageLoader.DisplayImage(searchedUser.get(position).getFlag(),
      //          holder.flag);
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
                // Pass all data position
                intent.putExtra("position",
                        (searchedUsers.get(position).getPosition()));
                // Pass all data company
                intent.putExtra("company",
                        (searchedUsers.get(position).getCompany()));
                // Start SingleItemView Class
                context.startActivity(intent);

            }
        });
        return view;
    }


}
