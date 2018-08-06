package edu.utexas.mpc.warble3.model.thing;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.GE.GEDiscovery;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueUPnPDiscovery;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.Wink.WinkDiscovery;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;
import edu.utexas.mpc.warble3.util.Logging;

public class ThingManager {
    private static final String TAG = "ThingManager";

    private static ThingManager instance = new ThingManager();

    private ThingManager() {}

    public static ThingManager getInstance() {
        if (instance == null) {
            instance = new ThingManager();
        }
        return instance;
    }

    public List<Thing> getThings() {
        List<Thing> things = AppDatabase.getDatabase().getThings();

        if (things == null) {
            return null;
        }
        else {
            for (Thing thing : things) {
                List<Connection> connections = AppDatabase.getDatabase().getConnectionsBySourceId(thing.getDbid());
                thing.setConnections(connections);

                List<ThingAccessCredential> thingAccessCredentials = AppDatabase.getDatabase().getThingAccessCredentialsByThingId(thing.getDbid());
                thing.setThingAccessCredentials(thingAccessCredentials);
            }

            return things;
        }
    }

    public void discover() {
        List<Discovery> discoveries = new ArrayList<>();

        // TODO: Discovery list has to be managed somewhere else
        discoveries.add(new PhilipsHueUPnPDiscovery());
        discoveries.add(new WinkDiscovery());
        discoveries.add(new GEDiscovery());

        List<Thing> things = new ArrayList<>();
        for(Discovery discovery: discoveries) {
            List<? extends Thing> things1 = discovery.onDiscover();
            if (things1 != null) {
                things.addAll(things1);
            }
        }

        saveThings(things);
    }

    public void saveThing(Thing thing) {
        if (Logging.VERBOSE) Log.v(TAG, String.format("Saving %s", thing.getFriendlyName()));

        AppDatabase.getDatabase().saveThing(thing);

        for (Connection connection : thing.getConnections()) {
            Thing destinationThing = connection.getDestination();
            if (destinationThing != null) {
                saveThing(destinationThing);
            }
            AppDatabase.getDatabase().saveConnection(connection);
        }

        AppDatabase.getDatabase().saveThingAccessCredentials(thing.getThingAccessCredentials());
    }

    public void saveThings(List<Thing> things) {
        if (things != null) {
            for (Thing thing : things) {
                saveThing(thing);
            }
        }
    }
}
