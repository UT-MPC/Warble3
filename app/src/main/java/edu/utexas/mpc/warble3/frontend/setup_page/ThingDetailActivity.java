package edu.utexas.mpc.warble3.frontend.setup_page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class ThingDetailActivity extends AppCompatActivity{
    private static final String TAG = "ThingDetailActivity";

    public static final String THING_BUNDLE_INTENT_EXTRA = "edu.utexas.mpc.warble3.setup_page.ThingDetailActivity.THING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);

        Bundle thingBundle = getIntent().getBundleExtra(THING_BUNDLE_INTENT_EXTRA);
        Thing thing = (Thing) thingBundle.getSerializable(THING_BUNDLE_INTENT_EXTRA);

        if (thing == null) {
            finish();
        }
        else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(thing.getFriendlyName());

            ((TextView) findViewById(R.id.name_thingActivity_textView)).setText(thing.getName());
            ((TextView) findViewById(R.id.friendlyName_thingActivity_textView)).setText(thing.getFriendlyName());
            ((TextView) findViewById(R.id.uuid_thingActivity_textView)).setText(thing.getUuid());
            ((TextView) findViewById(R.id.thingTypes_thingActivity_textView)).setText(thing.getThingTypes().toString());
            ((TextView) findViewById(R.id.concreteTypes_thingActivity_textView)).setText(thing.getThingConcreteType().toString());
            ((TextView) findViewById(R.id.accessName_thingActivity_textView)).setText(thing.getAccessName());
            ((TextView) findViewById(R.id.accessUsername_thingActivity_textView)).setText(thing.getAccessUsername());
            ((TextView) findViewById(R.id.accessPasscode_thingActivity_textView)).setText(thing.getAccessPasscode());
            ((TextView) findViewById(R.id.manufacturerSerialNumber_thingActivity_textView)).setText(thing.getManufacturerSerialNumber());
            ((TextView) findViewById(R.id.manufacturerModelName_thingActivity_textView)).setText(thing.getManufacturerModelName());
            ((TextView) findViewById(R.id.manufacturerModelNumber_thingActivity_textView)).setText(thing.getManufacturerModelNumber());
            ((TextView) findViewById(R.id.manufacturerName_thingActivity_textView)).setText(thing.getManufacturerName());
            // ((TextView) findViewById(R.id.connections_thingActivity_textView)).setText(thing);
            // ((TextView) findViewById(R.id.discoveries_thingActivity_textView)).setText(thing);
            ((TextView) findViewById(R.id.isCredentialRequired_thingActivity_textView)).setText(String.valueOf(thing.getCredentialRequired()));
            // ((TextView) findViewById(R.id.credentials_thingActivity_textView)).setText(thing);
            ((TextView) findViewById(R.id.dbid_thingActivity_textView)).setText(String.valueOf(thing.getDbid()));
        }
    }
}
