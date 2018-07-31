package edu.utexas.mpc.warble3.frontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.ConnectionDb;
import edu.utexas.mpc.warble3.database.ThingAccessCredentialDb;
import edu.utexas.mpc.warble3.database.ThingDb;
import edu.utexas.mpc.warble3.frontend.async_tasks.DiscoveryAsyncTask;
import edu.utexas.mpc.warble3.frontend.async_tasks.DiscoveryAsyncTaskComplete;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.ControlFragment;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.SettingsFragment;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.SetupFragment;
import edu.utexas.mpc.warble3.model.resource.Resource;
import edu.utexas.mpc.warble3.model.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.util.Logging;

public class MainActivity extends AppCompatActivity implements DiscoveryAsyncTaskComplete {
    private static final String TAG = "MainActivity";

    private List<Thing> things = new ArrayList<>();
    private HashMap<THING_CONCRETE_TYPE, List<Thing>> thingsHashMap = new HashMap<>();

    private SetupFragment setupFragment = SetupFragment.getNewInstance(thingsHashMap);
    private Fragment manualFragment = new Fragment();
    private ControlFragment controlFragment = ControlFragment.getNewInstance();
    private Fragment fourFragment = new Fragment();
    private SettingsFragment settingsFragment = SettingsFragment.getNewInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_setup:
                    fragmentManager.beginTransaction().replace(R.id.content, setupFragment).commit();
                    return true;
                case R.id.navigation_manual:
                    fragmentManager.beginTransaction().replace(R.id.content, manualFragment).commit();
                    return true;
                case R.id.navigation_control:
                    fragmentManager.beginTransaction().replace(R.id.content, controlFragment).commit();
                    return true;
                case R.id.navigation_4:
                    fragmentManager.beginTransaction().replace(R.id.content, fourFragment).commit();
                    return true;
                case R.id.navigation_settings:
                    fragmentManager.beginTransaction().replace(R.id.content, settingsFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_control);

        List<ThingDb> thingDbs = AppDatabase.getDatabase().thingDbDao().getAllThingDbs();
        if (thingDbs == null) {
            if(Logging.VERBOSE) Log.v(TAG, "Number of thingDb before discover : 0");
        }
        else {
            if(Logging.VERBOSE) Log.v(TAG, String.format("Number of thingDb before discover : %d", thingDbs.size()));
            for (ThingDb thingDb : thingDbs) {
                if(Logging.VERBOSE) Log.v(TAG, thingDb.toString());
            }
        }

        List<ConnectionDb> connectionDbs = AppDatabase.getDatabase().connectionDbDao().getAllConnectionDbs();
        if (connectionDbs == null) {
            if(Logging.VERBOSE) Log.v(TAG, "Number of connectionDb before discover : 0");
        }
        else {
            if(Logging.VERBOSE) Log.v(TAG, String.format("Number of connectionDb before discover : %d", connectionDbs.size()));
            for (ConnectionDb connectionDb : connectionDbs) {
                if(Logging.VERBOSE) Log.v(TAG, connectionDb.toString());
            }
        }

        List<ThingAccessCredentialDb> thingAccessCredentialDbs = AppDatabase.getDatabase().thingAccessCredentialDbDao().getAllThingAccessCredentialDbs();
        if (thingAccessCredentialDbs == null) {
            if(Logging.VERBOSE) Log.v(TAG, "Number of thingAccessCredentialDbs before discover : 0");
        }
        else {
            if(Logging.VERBOSE) Log.v(TAG, String.format("Number of thingAccessCredentialDbs before discover : %d", thingAccessCredentialDbs.size()));
            for (ThingAccessCredentialDb thingAccessCredentialDb : thingAccessCredentialDbs) {
                if(Logging.VERBOSE) Log.v(TAG, thingAccessCredentialDb.toString());
            }
        }

        setupFragment.updateDiscoveredThings(setupFragment.toThingHashMap(Resource.getInstance().getThings()));

        new DiscoveryAsyncTask(this).execute();
    }

    @Override
    public void onDiscoveryTaskComplete(List<Thing> things) {
        if (things == null) {
            if (Logging.VERBOSE) Log.v(TAG, "Discovered things is null");
        }
        else {
            this.things = things;
            thingsHashMap = setupFragment.toThingHashMap(this.things);
            setupFragment.updateDiscoveredThings(thingsHashMap);
        }
    }
}
