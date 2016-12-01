package sriyaan.ac;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;

/**
 * Created by Dipesh on 12-Jul-16.
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter{
    private int[] colors = new int[] { 0x30D3D3D3, 0x00000000 };


//    private Context context;
//    private List<String> expandableListTitle;
//    private HashMap<String, List<String>> expandableListDetail;

//    ArrayList<HashMap<String, String>> expandableListTitle = new ArrayList<HashMap<String, String>>();
//    HashMap<String, String> expandableListDetail = new HashMap<String, String>();

    private ArrayList<Group> worldpopulationlist = null;
    private Context context;
    private ArrayList<Group> groups;

    public CustomExpandableListAdapter(Context context, ArrayList<Group> worldpopulationlist) {
        this.context = context;
        this.worldpopulationlist = worldpopulationlist;
        this.groups = new ArrayList<Group>();
        this.groups.addAll(worldpopulationlist);
//        this.groups = groups;
    }


//    public CustomExpandableListAdapter(Context context, ArrayList<HashMap<String, String>> expandableListTitle, HashMap<String, String> expandableListDetail) {
//
//        this.context = context;
//        this.expandableListDetail = expandableListDetail;
//        this.expandableListTitle = expandableListTitle;
//    }


    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Child> chList = worldpopulationlist.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> chList = worldpopulationlist.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

//        final String expandedListText = (String) getChild(groupPosition, childPosition);
        Child child = (Child) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        TextView expandedListTextView1 = (TextView) convertView.findViewById(R.id.expandedListItem1);

        final String custName = child.getCustid();
        expandedListTextView.setText(child.getName());
        expandedListTextView1.setText(child.getMachine());

        expandedListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(context, CustomerProfile.class);
                intent.putExtra("cust_id",custName);
                        context.startActivity(intent);

            }
        });

        expandedListTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MachineList.class);
                intent.putExtra("cust_id",custName);
                context.startActivity(intent);

            }
        });

        return convertView;
    }


    @Override
    public int getGroupCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return worldpopulationlist.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

//        String listTitle = (String) getGroup(groupPosition);
        Group group = (Group) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
//        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(group.getName());

        int colorPos = groupPosition % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);
        return convertView;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(groups);
        }
        else
        {
            for (Group wp : groups)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
//                if (wp.getMachine_name().contains(charText))
                {
                    worldpopulationlist.add(wp);
                }

            }
        }
        notifyDataSetChanged();
    }
}
