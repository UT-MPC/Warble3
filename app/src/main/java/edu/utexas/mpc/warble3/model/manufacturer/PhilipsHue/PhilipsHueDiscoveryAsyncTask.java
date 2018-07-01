package edu.utexas.mpc.warble3.model.manufacturer.PhilipsHue;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.Thing;

public class PhilipsHueDiscoveryAsyncTask extends AsyncTask<Void, Void, List<? extends Thing>> {
    @Override
    protected List<? extends Thing> doInBackground(Void... voids) {
        PhilipsHueDiscovery discovery = new PhilipsHueDiscovery();

        List<PhilipsHueBridge> philipsHueBridges = discovery.onDiscover();
        List<Thing> philipsHueDescendants = discovery.onDiscoverDescendants();

        List<Thing> philipsHueThings = new ArrayList<>();
        philipsHueThings.addAll(philipsHueBridges);
        philipsHueThings.addAll(philipsHueDescendants);

        return philipsHueThings;
    }
}
