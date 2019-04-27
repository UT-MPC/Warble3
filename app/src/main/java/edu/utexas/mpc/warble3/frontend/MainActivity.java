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

package edu.utexas.mpc.warble3.frontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.frontend.async_tasks.DiscoveryAsyncTask;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.BluetoothFragment;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.ControlFragment;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.ManualFragment;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.SettingsFragment;
import edu.utexas.mpc.warble3.frontend.main_activity_fragments.SetupFragment;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;
import edu.utexas.mpc.warble3.util.WarbleHandler;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.util.ThingUtil;

public class MainActivity extends AppCompatActivity implements DiscoveryAsyncTask.DiscoveryAsyncTaskInterface {
    private static final String TAG = "MainActivity";

    private SetupFragment setupFragment = SetupFragment.getNewInstance();
    private ManualFragment manualFragment = ManualFragment.getNewInstance();
    private ControlFragment controlFragment = ControlFragment.getNewInstance();
    private BluetoothFragment btFragment = new BluetoothFragment();
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
                case R.id.navigation_ble:
                    fragmentManager.beginTransaction().replace(R.id.content, btFragment).commit();
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

        if (Logging.INFO) Log.i(TAG, AppDatabase.getDatabase().toString());
        if (Logging.INFO) Log.i(TAG, SharedPreferenceHandler.getSharedPrefsString(this));

        setupFragment.updateDiscoveredThings(ThingUtil.toThingHashMapByConcreteType(WarbleHandler.getInstance().fetch()));

        new DiscoveryAsyncTask(this).execute();
    }

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
            setupFragment.updateDiscoveredThings(thingsHashMap);
        }
    }
}
