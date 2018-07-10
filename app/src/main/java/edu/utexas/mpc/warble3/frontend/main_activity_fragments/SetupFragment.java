package edu.utexas.mpc.warble3.frontend.main_activity_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.R;

public class SetupFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup holder, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, holder, false);

        List<String> headers = new ArrayList<>();
        headers.add("Bridge");
        headers.add("Light");

        List<String> bridges = new ArrayList<>();
        bridges.add("Philips Hue Bridge 1");
        bridges.add("Wink Hub 1");

        List<String> lights = new ArrayList<>();
        lights.add("Light 1");
        lights.add("Light 2");

        HashMap<String, List<String>> childHashMap = new HashMap<>();
        childHashMap.put("Bridge", bridges);
        childHashMap.put("Light", lights);

        ThingExpandableListAdapter thingExpandableListAdapter = new ThingExpandableListAdapter(getContext(), headers, childHashMap);

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.setup_elv);
        expandableListView.setAdapter(thingExpandableListAdapter);

        return view;
    }

    public class ThingExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<String> headers;
        private HashMap<String, List<String>> childHashMap;

        public ThingExpandableListAdapter (Context context, List<String> headers, HashMap<String, List<String>> childHashMap) {
            this.context = context;
            this.headers = headers;
            this.childHashMap = childHashMap;
        }

        @Override
        public int getGroupCount() {
            return this.headers.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return this.childHashMap.get(headers.get(i)).size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return this.headers.get(groupPosition);
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return this.childHashMap.get(this.headers.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_thing_list_group, null);
            }

            TextView thing_list_group_header = (TextView) view.findViewById(R.id.thing_list_group_header);
            thing_list_group_header.setText(getGroup(groupPosition));

            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_thing_list_item, null);
            }

            TextView thing_list_item_header = (TextView) view.findViewById(R.id.thing_list_item_header);
            thing_list_item_header.setText(getChild(groupPosition, childPosition));

            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
