package edu.utexas.mpc.warble3.frontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.SetupFragment;
import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.discovery.DiscoveryAsyncTask;
import edu.utexas.mpc.warble3.model.discovery.DiscoveryAsyncTaskComplete;
import edu.utexas.mpc.warble3.util.Logging;

public class MainActivity extends AppCompatActivity implements DiscoveryAsyncTaskComplete {
    private static final String TAG = "MainActivity";

    private TextView mTextMessage;

    private List<Thing> things = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_setup:
                    fragmentManager.beginTransaction().replace(R.id.content, new SetupFragment()).commit();
                    return true;
                case R.id.navigation_manual:
                    return true;
                case R.id.navigation_control:
                    return true;
                case R.id.navigation_4:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new DiscoveryAsyncTask(this).execute();
    }

    @Override
    public void onTaskComplete(List<Thing> things) {
        this.things = things;
        if (Logging.DEBUG) Log.d(TAG, things.toString());
    }
}
