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

package edu.utexas.mpc.warble3.frontend.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.adapter.SimpleAdapter;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.WarbleHandler;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.connection.Connection;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.warble.thing.discovery.Discovery;

public class ThingDetailActivity extends AppCompatActivity {
    private static final String TAG = "ThingDetailActivity";

    public static final String THING_BUNDLE_INTENT_EXTRA = "edu.utexas.mpc.warble3.setup_page.ThingDetailActivity.THING";

    protected Thing thing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);

        Bundle thingBundle = getIntent().getBundleExtra(THING_BUNDLE_INTENT_EXTRA);
        thing = (Thing) thingBundle.getSerializable(THING_BUNDLE_INTENT_EXTRA);
    }

    @Override
    protected void onResume() {
        super.onResume();

        thing = WarbleHandler.getInstance().loadThing(thing);

        if (thing == null) {
            finish();
        }
        else {
            if (Logging.VERBOSE) Log.v(TAG, String.format("Thing : %s", thing.toString()));

            Objects.requireNonNull(getSupportActionBar()).setTitle(thing.getFriendlyName());

            ((TextView) findViewById(R.id.name_thingActivity_textView)).setText(thing.getName());
            ((TextView) findViewById(R.id.friendlyName_thingActivity_textView)).setText(thing.getFriendlyName());
            ((TextView) findViewById(R.id.uuid_thingActivity_textView)).setText(thing.getUuid());
            ((TextView) findViewById(R.id.thingTypes_thingActivity_textView)).setText(thing.getThingTypes().toString());
            ((TextView) findViewById(R.id.concreteTypes_thingActivity_textView)).setText(thing.getThingConcreteType().toString());
            if (thing.getLocation() != null) {
                ((TextView) findViewById(R.id.location_thingActivity_textView)).setText(thing.getLocation().toString());
            }
            ((TextView) findViewById(R.id.accessName_thingActivity_textView)).setText(thing.getAccessName());
            ((TextView) findViewById(R.id.accessUsername_thingActivity_textView)).setText(thing.getAccessUsername());
            ((TextView) findViewById(R.id.accessPasscode_thingActivity_textView)).setText(thing.getAccessPasscode());
            ((TextView) findViewById(R.id.manufacturerSerialNumber_thingActivity_textView)).setText(thing.getManufacturerSerialNumber());
            ((TextView) findViewById(R.id.manufacturerModelName_thingActivity_textView)).setText(thing.getManufacturerModelName());
            ((TextView) findViewById(R.id.manufacturerModelNumber_thingActivity_textView)).setText(thing.getManufacturerModelNumber());
            ((TextView) findViewById(R.id.manufacturerName_thingActivity_textView)).setText(thing.getManufacturerName());
            ((TextView) findViewById(R.id.isCredentialRequired_thingActivity_textView)).setText(String.valueOf(thing.getCredentialRequired()));
            ((TextView) findViewById(R.id.dbid_thingActivity_textView)).setText(String.valueOf(thing.getDbid()));

            if (thing.getConnections() != null) {
                List<String> connectionsString = new ArrayList<>();
                for (Connection connection : thing.getConnections()) {
                    connectionsString.add(connection.toString());
                }

                RecyclerView connectionsRecyclerView = findViewById(R.id.connections_thingActivity_recyclerView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                RecyclerView.Adapter adapter = new SimpleAdapter(this, connectionsString);
                connectionsRecyclerView.setLayoutManager(layoutManager);
                connectionsRecyclerView.setAdapter(adapter);
            }

            if (thing.getDiscoveries() != null) {
                List<String> discoveriesString = new ArrayList<>();
                for (Discovery discovery : thing.getDiscoveries()) {
                    discoveriesString.add(discovery.toString());
                }

                RecyclerView discoveriesRecyclerView = findViewById(R.id.discoveries_thingActivity_recyclerView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                RecyclerView.Adapter adapter = new SimpleAdapter(this, discoveriesString);
                discoveriesRecyclerView.setLayoutManager(layoutManager);
                discoveriesRecyclerView.setAdapter(adapter);
            }

            if (thing.getThingAccessCredentials() != null) {
                List<String> thingAccessCredentialsString = new ArrayList<>();
                for (ThingAccessCredential thingAccessCredential : thing.getThingAccessCredentials()) {
                    thingAccessCredentialsString.add(thingAccessCredential.toString());
                }

                RecyclerView thingAccessCredentialsRecyclerView = findViewById(R.id.thingCredential_recyclerView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                RecyclerView.Adapter adapter = new SimpleAdapter(this, thingAccessCredentialsString);
                thingAccessCredentialsRecyclerView.setLayoutManager(layoutManager);
                thingAccessCredentialsRecyclerView.setAdapter(adapter);
            }

            ((TextView) findViewById(R.id.addCredential_thingActivity_textView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThingDetailActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.activity_select_add_thing_access_credential, null);

                    builder.setView(mView);
                    final AlertDialog dialog = builder.create();

                    final List<String> thingAccessCredentialClassesString = new ArrayList<>();
                    for (Class thingAccessCredentialClass : thing.getThingAccessCredentialClasses()) {
                        thingAccessCredentialClassesString.add(thingAccessCredentialClass.getSimpleName());
                    }

                    RecyclerView recyclerView = mView.findViewById(R.id.selectThingAccessCredentialClass_selectAddThingAccessCredential_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ThingDetailActivity.this);
                    SimpleAdapter adapter = new SimpleAdapter(ThingDetailActivity.this, thingAccessCredentialClassesString);
                    adapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String selectedClass = thingAccessCredentialClassesString.get(position);

                            Bundle thingBundle = new Bundle();
                            thingBundle.putSerializable(AddThingAccessCredentialActivity.THING_INTENT_EXTRA, thing);

                            Intent intent = new Intent(ThingDetailActivity.this, AddThingAccessCredentialActivity.class);
                            intent.putExtra(AddThingAccessCredentialActivity.THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA, selectedClass);
                            intent.putExtra(AddThingAccessCredentialActivity.THING_INTENT_EXTRA, thingBundle);
                            startActivityForResult(intent, 0);

                            dialog.dismiss();
                        }
                    });
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                    dialog.show();
                }
            });
        }
    }
}
