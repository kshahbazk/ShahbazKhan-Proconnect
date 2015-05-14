package momenify.proconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.List;

import momenify.proconnect.navigationviewpagerliveo.R;

/**
 * Created by shahbazkhan on 5/5/15.
 */
public class ExperienceListViewAdapter extends BaseAdapter {

    private List<ParseObject> listOfExp;
    LayoutInflater inflater;
    Context context;

    public ExperienceListViewAdapter(Context context, List<ParseObject> list)
    {
        this.context= context;
        inflater = LayoutInflater.from(context);
        listOfExp = list;

    }

    public class ViewHolder {
        TextView comp;
        TextView title;
        TextView startyear;
        TextView endyear;

    }

    @Override
    public int getCount() {
        return listOfExp.size();
    }

    @Override
    public ParseObject getItem(int position) {
        return listOfExp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.experience_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.comp = (TextView) view.findViewById(R.id.putcompany);
            holder.title= (TextView) view.findViewById(R.id.putTitle);
            holder.startyear = (TextView) view.findViewById(R.id.putstartDate);
            holder.endyear = (TextView) view.findViewById(R.id.putendDate);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        // Set the results into TextViews
        if(listOfExp!=null) {
            holder.comp.setText(listOfExp.get(position).getString("company"));
            holder.title.setText(listOfExp.get(position).getString("title"));
            holder.startyear.setText(listOfExp.get(position).getString("sY"));
            holder.endyear.setText(listOfExp.get(position).getString("eY"));
        }

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // Send single item click data to UserProfileView Class


                Toast.makeText(context, "Experience number " + position,
                        Toast.LENGTH_LONG).show();

            }
        });




        return view;
    }

}
