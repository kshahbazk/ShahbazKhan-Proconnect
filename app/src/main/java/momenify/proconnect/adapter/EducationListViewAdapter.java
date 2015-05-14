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
 * Created by shahbazkhan on 5/4/15.
 */
public class EducationListViewAdapter extends BaseAdapter{
private List<ParseObject> listOfEducation;
    LayoutInflater inflater;
    Context context;

    public EducationListViewAdapter(Context context, List<ParseObject> list)
    {
        this.context= context;
        inflater = LayoutInflater.from(context);
        listOfEducation = list;

    }

    public class ViewHolder {
        TextView school;
        TextView degree;
        TextView gradyear;

    }

       @Override
    public int getCount() {
        return listOfEducation.size();
    }

       @Override
    public ParseObject getItem(int position) {
        return listOfEducation.get(position);
    }

       @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.education_listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.school = (TextView) view.findViewById(R.id.putschool);
            holder.degree= (TextView) view.findViewById(R.id.putdegree);
            holder.gradyear = (TextView) view.findViewById(R.id.putGradYear);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        // Set the results into TextViews
        if(listOfEducation!=null) {
            holder.school.setText(listOfEducation.get(position).getString("school"));
            holder.degree.setText(listOfEducation.get(position).getString("degree"));
            holder.gradyear.setText(listOfEducation.get(position).getString("gradYear"));
        }

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // Send single item click data to UserProfileView Class


                Toast.makeText(context, "Education number" + position,
                        Toast.LENGTH_LONG).show();

            }
        });




        return view;
    }


}
