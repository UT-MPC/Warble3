package edu.utexas.mpc.warble3.frontend.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.adapter.SimpleAdapter;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;

public class ThingDetailActivity extends AppCompatActivity implements SimpleAdapter.mClickListener {
    private static final String TAG = "ThingDetailActivity";

    public static final String THING_BUNDLE_INTENT_EXTRA = "edu.utexas.mpc.warble3.setup_page.ThingDetailActivity.THING";

    private Thing thing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);

        Bundle thingBundle = getIntent().getBundleExtra(THING_BUNDLE_INTENT_EXTRA);
        final Thing thing = (Thing) thingBundle.getSerializable(THING_BUNDLE_INTENT_EXTRA);

        if (thing == null) {
            finish();
        }
        else {
            this.thing = thing;

            Objects.requireNonNull(getSupportActionBar()).setTitle(this.thing.getFriendlyName());

            ((TextView) findViewById(R.id.name_thingActivity_textView)).setText(this.thing.getName());
            ((TextView) findViewById(R.id.friendlyName_thingActivity_textView)).setText(this.thing.getFriendlyName());
            ((TextView) findViewById(R.id.uuid_thingActivity_textView)).setText(this.thing.getUuid());
            ((TextView) findViewById(R.id.thingTypes_thingActivity_textView)).setText(this.thing.getThingTypes().toString());
            ((TextView) findViewById(R.id.concreteTypes_thingActivity_textView)).setText(this.thing.getThingConcreteType().toString());
            ((TextView) findViewById(R.id.accessName_thingActivity_textView)).setText(this.thing.getAccessName());
            ((TextView) findViewById(R.id.accessUsername_thingActivity_textView)).setText(this.thing.getAccessUsername());
            ((TextView) findViewById(R.id.accessPasscode_thingActivity_textView)).setText(this.thing.getAccessPasscode());
            ((TextView) findViewById(R.id.manufacturerSerialNumber_thingActivity_textView)).setText(this.thing.getManufacturerSerialNumber());
            ((TextView) findViewById(R.id.manufacturerModelName_thingActivity_textView)).setText(this.thing.getManufacturerModelName());
            ((TextView) findViewById(R.id.manufacturerModelNumber_thingActivity_textView)).setText(this.thing.getManufacturerModelNumber());
            ((TextView) findViewById(R.id.manufacturerName_thingActivity_textView)).setText(this.thing.getManufacturerName());
            // ((TextView) findViewById(R.id.connections_thingActivity_textView)).setText(this.thing);
            // ((TextView) findViewById(R.id.discoveries_thingActivity_textView)).setText(this.thing);
            ((TextView) findViewById(R.id.isCredentialRequired_thingActivity_textView)).setText(String.valueOf(this.thing.getCredentialRequired()));
            ((TextView) findViewById(R.id.dbid_thingActivity_textView)).setText(String.valueOf(this.thing.getDbid()));

            if (this.thing.getThingAccessCredentials() != null) {
                List<String> thingAccessCredentialsString = new ArrayList<>();
                for (ThingAccessCredential thingAccessCredential : this.thing.getThingAccessCredentials()) {
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

                    List<String> thingAccessCredentialClassesString = new ArrayList<>();
                    for (Class thingAccessCredentialClass : ThingDetailActivity.this.thing.getThingAccessCredentialClasses()) {
                        thingAccessCredentialClassesString.add(thingAccessCredentialClass.getSimpleName());
                    }
                    
                    RecyclerView recyclerView = mView.findViewById(R.id.selectThingAccessCredentialClass_selectAddThingAccessCredential_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ThingDetailActivity.this);
                    RecyclerView.Adapter adapter = new SimpleAdapter(ThingDetailActivity.this, thingAccessCredentialClassesString);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                    builder.setView(mView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    @Override
    public void mClick(View view, int position) {
        Class thingAccessCredentialClass = thing.getThingAccessCredentialClasses().get(position);

        Intent intent = new Intent(this, AddThingAccessCredentialActivity.class);
        intent.putExtra(AddThingAccessCredentialActivity.THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA, thingAccessCredentialClass.getSimpleName());
        startActivityForResult(intent, 0);
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }
}
