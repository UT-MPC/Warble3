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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.model.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class SetupFragment extends Fragment {
    private HashMap<THING_CONCRETE_TYPE, List<String>> discoveredThings;
    private ExpandableListView expandableListView;

    public static SetupFragment getNewInstance(HashMap<THING_CONCRETE_TYPE, List<String>> discoveredThings) {
        SetupFragment setupFragment = new SetupFragment();

        Bundle args = new Bundle();
        args.putSerializable("discoveredThings", discoveredThings);
        setupFragment.setDiscoveredThings(discoveredThings);
        return setupFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup holder, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, holder, false);
        expandableListView = view.findViewById(R.id.setup_elv);
        updateDiscoveredThings(discoveredThings);
        return view;
    }

    public HashMap<THING_CONCRETE_TYPE, List<String>> getDiscoveredThings() {
        return discoveredThings;
    }

    public void setDiscoveredThings(HashMap<THING_CONCRETE_TYPE, List<String>> discoveredThings) {
        this.discoveredThings = discoveredThings;
    }

    public HashMap<THING_CONCRETE_TYPE, List<String>> toThingHashMap(List<Thing> things) {
        if (things == null) {
            return null;
        }
        else {
            HashMap<THING_CONCRETE_TYPE, List<String>> thingsHashMap = new HashMap<>();

            for (Thing thing : things) {
                List<String> listThings = thingsHashMap.get(thing.getThingConcreteType());
                if (listThings == null) listThings = new ArrayList<>();
                listThings.add(thing.getFriendlyName());
                thingsHashMap.put(thing.getThingConcreteType(), listThings);
            }

            return thingsHashMap;
        }
    }

    public void updateDiscoveredThings(HashMap<THING_CONCRETE_TYPE, List<String>> discoveredThings) {
        setDiscoveredThings(discoveredThings);
        if (expandableListView != null) {
            ThingExpandableListAdapter thingExpandableListAdapter = new ThingExpandableListAdapter(getContext(), Arrays.asList(THING_CONCRETE_TYPE.values()), discoveredThings);
            expandableListView.setAdapter(thingExpandableListAdapter);
        }
    }

    public class ThingExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<THING_CONCRETE_TYPE> headers;
        private HashMap<THING_CONCRETE_TYPE, List<String>> childHashMap;

        private ThingExpandableListAdapter (Context context, List<THING_CONCRETE_TYPE> headers, HashMap<THING_CONCRETE_TYPE, List<String>> childHashMap) {
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
            try {
                return this.childHashMap.get(headers.get(i)).size();
            }
            catch (NullPointerException e) {
                return 0;
            }
        }

        @Override
        public String getGroup(int groupPosition) {
            return this.headers.get(groupPosition).toString();
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
