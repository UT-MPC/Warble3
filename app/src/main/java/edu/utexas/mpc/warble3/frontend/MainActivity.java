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
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.SetupFragment;
import edu.utexas.mpc.warble3.model.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.discovery.DiscoveryAsyncTask;
import edu.utexas.mpc.warble3.model.discovery.DiscoveryAsyncTaskComplete;
import edu.utexas.mpc.warble3.util.Logging;

public class MainActivity extends AppCompatActivity implements DiscoveryAsyncTaskComplete {
    private static final String TAG = "MainActivity";

    private List<Thing> things = new ArrayList<>();
    private HashMap<THING_CONCRETE_TYPE, List<String>> thingsHashMap = new HashMap<>();

    private SetupFragment setupFragment = SetupFragment.getNewInstance(thingsHashMap);
    private Fragment manualFragment = new Fragment();
    private Fragment controlFragment = new Fragment();
    private Fragment fourFragment = new Fragment();
    private Fragment settingsFragment = new Fragment();

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

        new DiscoveryAsyncTask(this).execute();
    }

    @Override
    public void onTaskComplete(List<Thing> things) {
        if (Logging.DEBUG) Log.d(TAG, things.toString());
        this.things = things;

        for (Thing thing: this.things) {
            List<String> listThings = thingsHashMap.get(thing.getConcreteType());
            if (listThings == null) listThings = new ArrayList<>();
            listThings.add(thing.getFriendlyName());
            thingsHashMap.put(thing.getConcreteType(), listThings);
        }

        setupFragment.updateDiscoveredThings(thingsHashMap);
    }
}
