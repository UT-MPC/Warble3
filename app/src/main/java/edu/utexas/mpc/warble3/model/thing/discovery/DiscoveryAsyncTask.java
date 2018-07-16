package edu.utexas.mpc.warble3.model.thing.discovery;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.structure.Thing;
import edu.utexas.mpc.warble3.model.thing.structure.manufacturer.GE.GEDiscovery;
import edu.utexas.mpc.warble3.model.thing.structure.manufacturer.PhilipsHue.PhilipsHueUPnPDiscovery;
import edu.utexas.mpc.warble3.model.thing.structure.manufacturer.Wink.WinkDiscovery;
import edu.utexas.mpc.warble3.util.Logging;

public class DiscoveryAsyncTask extends AsyncTask<Void, Void, List<Thing>> {
    private static final String TAG = "DiscoveryAsyncTask";
    private DiscoveryAsyncTaskComplete mCallback;
    private List<Thing> things;

    public DiscoveryAsyncTask(DiscoveryAsyncTaskComplete context) {
        mCallback = context;
    }

    @Override
    protected List<Thing> doInBackground(Void... voids) {
        if (Logging.DEBUG) Log.d(TAG, "Executing DiscoveryAsyncTask ...");
        List<Discovery> discoveries = new ArrayList<>();
        discoveries.add(new PhilipsHueUPnPDiscovery());
        discoveries.add(new WinkDiscovery());
        discoveries.add(new GEDiscovery());

        things = new ArrayList<>();
        for(Discovery discovery: discoveries) {
            List<? extends Thing> things1 = discovery.onDiscover();
            List<? extends Thing> things2 = discovery.onDiscoverDescendants();
            if (things1 != null) {
                things.addAll(things1);
            }
            if (things2 != null) {
                things.addAll(things2);
            }
        }

        return things;
    }

    @Override
    protected void onPostExecute(List<Thing> things) {
        mCallback.onDiscoveryTaskComplete(things);
    }
}
