package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public final class PhilipsHueDiscoveryAsyncTask extends AsyncTask<Void, Void, List<? extends Thing>> {
    @Override
    protected List<? extends Thing> doInBackground(Void... voids) {
        PhilipsHueUPnPDiscovery discovery = new PhilipsHueUPnPDiscovery();

        List<PhilipsHueBridge> philipsHueBridges = discovery.onDiscover();

        List<Thing> philipsHueThings = new ArrayList<>();

        if (philipsHueBridges != null) {
            philipsHueThings.addAll(philipsHueBridges);
        }

        return philipsHueThings;
    }
}
