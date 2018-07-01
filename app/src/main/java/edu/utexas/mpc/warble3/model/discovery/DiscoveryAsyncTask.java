package edu.utexas.mpc.warble3.model.discovery;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.manufacturer.GE.GEDiscovery;
import edu.utexas.mpc.warble3.model.manufacturer.PhilipsHue.PhilipsHueDiscovery;
import edu.utexas.mpc.warble3.model.manufacturer.Wink.WinkDiscovery;

public class DiscoveryAsyncTask extends AsyncTask<Void, Void, List<Thing>> {
    private DiscoveryAsyncTaskComplete mCallback;

    public DiscoveryAsyncTask(Context context) {
        mCallback = (DiscoveryAsyncTaskComplete) context;
    }

    @Override
    protected List<Thing> doInBackground(Void... voids) {
        List<Discovery> discoveries = new ArrayList<>();
        discoveries.add(new PhilipsHueDiscovery());
        discoveries.add(new WinkDiscovery());
        discoveries.add(new GEDiscovery());

        List<Thing> things = new ArrayList<>();
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
        mCallback.onTaskComplete(things);
    }
}
