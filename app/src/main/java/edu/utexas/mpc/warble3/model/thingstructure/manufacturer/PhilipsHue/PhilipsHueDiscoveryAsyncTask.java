package edu.utexas.mpc.warble3.model.thingstructure.manufacturer.PhilipsHue;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thingstructure.Thing;

public class PhilipsHueDiscoveryAsyncTask extends AsyncTask<Void, Void, List<? extends Thing>> {
    @Override
    protected List<? extends Thing> doInBackground(Void... voids) {
        PhilipsHueUPnPDiscovery discovery = new PhilipsHueUPnPDiscovery();

        List<PhilipsHueBridge> philipsHueBridges = discovery.onDiscover();
        List<Thing> philipsHueDescendants = discovery.onDiscoverDescendants();

        List<Thing> philipsHueThings = new ArrayList<>();
        philipsHueThings.addAll(philipsHueBridges);
        philipsHueThings.addAll(philipsHueDescendants);

        return philipsHueThings;
    }
}
