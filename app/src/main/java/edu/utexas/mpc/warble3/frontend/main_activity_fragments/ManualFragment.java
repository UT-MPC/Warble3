/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package edu.utexas.mpc.warble3.frontend.main_activity_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.async_tasks.DiscoveryAsyncTask;
import edu.utexas.mpc.warble3.frontend.async_tasks.SetThingStateAsyncTask;
import edu.utexas.mpc.warble3.frontend.thing.ThingDetailActivity;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.warble.Warble;
import edu.utexas.mpc.warble3.warble.thing.component.Light;
import edu.utexas.mpc.warble3.warble.thing.component.LightState;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.util.ThingUtil;

public class ManualFragment extends Fragment {
    private static final String TAG = "ManualFragment";

    private HashMap<THING_CONCRETE_TYPE, List<Thing>> discoveredThings;
    private ExpandableListView expandableListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    public static ManualFragment getNewInstance() {
        ManualFragment manualFragment = new ManualFragment();
        manualFragment.updateDiscoveredThings(ThingUtil.toThingHashMapByConcreteType(Warble.getInstance().getThings()));
        return manualFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual, null);

        expandableListView = view.findViewById(R.id.manualFragment_expandableListView);
        updateDiscoveredThings(discoveredThings);

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DiscoveryAsyncTask(new DiscoveryAsyncTask.DiscoveryAsyncTaskInterface() {
                    @Override
                    public void onDiscoveryTaskStart() {
                        // Do nothing
                    }

                    @Override
                    public void onDiscoveryTaskComplete(List<Thing> things) {
                        if (things == null) {
                            if (Logging.INFO) Log.i(TAG, "Discovered things is null");
                        }
                        else {
                            HashMap<THING_CONCRETE_TYPE, List<Thing>> thingsHashMap = ThingUtil.toThingHashMapByConcreteType(things);
                            updateDiscoveredThings(thingsHashMap);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).execute();
            }
        };
        swipeRefreshLayout = view.findViewById(R.id.manualFragment_swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        return view;
    }

    private void setDiscoveredThings(HashMap<THING_CONCRETE_TYPE, List<Thing>> discoveredThings) {
        this.discoveredThings = discoveredThings;
    }

    public void updateDiscoveredThings(final HashMap<THING_CONCRETE_TYPE, List<Thing>> discoveredThings) {
        setDiscoveredThings(discoveredThings);
        if (expandableListView != null) {
            ManualFragment.ThingExpandableListAdapter thingExpandableListAdapter = new ManualFragment.ThingExpandableListAdapter(getContext(), Arrays.asList(THING_CONCRETE_TYPE.values()), discoveredThings);
            expandableListView.setAdapter(thingExpandableListAdapter);
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                    THING_CONCRETE_TYPE selectedThingConcreteType = Arrays.asList(THING_CONCRETE_TYPE.values()).get(groupPosition);
                    Thing selectedThing = discoveredThings.get(selectedThingConcreteType).get(childPosition);

                    Intent thingDetailIntent = new Intent(getActivity(), ThingDetailActivity.class);
                    Bundle thingBundle = new Bundle();
                    thingBundle.putSerializable(ThingDetailActivity.THING_BUNDLE_INTENT_EXTRA, selectedThing);
                    thingDetailIntent.putExtra(ThingDetailActivity.THING_BUNDLE_INTENT_EXTRA, thingBundle);
                    startActivity(thingDetailIntent);
                    return true;
                }
            });
        }
    }


    public class ThingExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<THING_CONCRETE_TYPE> headers;
        private HashMap<THING_CONCRETE_TYPE, List<Thing>> childHashMap;

        public ThingExpandableListAdapter(Context context, List<THING_CONCRETE_TYPE> headers, HashMap<THING_CONCRETE_TYPE, List<Thing>> childHashMap) {
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
            return headers.get(groupPosition).toString();
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return childHashMap.get(headers.get(groupPosition)).get(childPosition).getFriendlyName();
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
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

            TextView thingListGroupHeader = (TextView) view.findViewById(R.id.thing_list_group_header);
            thingListGroupHeader.setText(getGroup(groupPosition));

            return view;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_thing_manual_item, null);
            }

            TextView thingListItemHeader = (TextView) view.findViewById(R.id.thing_manual_item_header);
            thingListItemHeader.setText(getChild(groupPosition, childPosition));

            final Switch thingManualItemSwitch = (Switch) view.findViewById(R.id.thing_manual_item_switch);
            thingManualItemSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Thing thing = childHashMap.get(headers.get(groupPosition)).get(childPosition);
                    switch (headers.get(groupPosition)) {
                        case LIGHT: {
                            if (thing instanceof Light) {
                                LightState lightState = new LightState();
                                if (thingManualItemSwitch.isChecked()) {
                                    lightState.setActive(true);
                                }
                                else {
                                    lightState.setActive(false);
                                }
                                SetThingStateAsyncTask.SetThingStateAsyncTaskParam param = new SetThingStateAsyncTask.SetThingStateAsyncTaskParam(thing, lightState);
                                new SetThingStateAsyncTask().execute(param);
                            }
                        }
                        default: {}
                    }
                }
            });

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }
}
