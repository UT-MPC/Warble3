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

package edu.utexas.mpc.warble3.warble.thing;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.connection.Connection;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.warble.thing.discovery.Discovery;
import edu.utexas.mpc.warble3.warble.thing.feature.Accessor;
import edu.utexas.mpc.warble3.warble.vendor.GE.GEDiscovery;
import edu.utexas.mpc.warble3.warble.vendor.PhilipsHue.discovery.PhilipsHueUPnPDiscovery;
import edu.utexas.mpc.warble3.warble.vendor.Wink.WinkDiscovery;

public class ThingManager {
    private static final String TAG = "ThingManager";

    private static ThingManager instance = new ThingManager();

    private ThingManager() {}

    public static ThingManager getInstance() {
        if (instance == null) {                         // Singleton Design Pattern
            instance = new ThingManager();
        }
        return instance;
    }

    public List<Thing> getThings() {
        List<Thing> things = AppDatabase.getDatabase().getThings();

        if ((things == null) || (things.size() == 0)) {
            return null;
        }
        else {
            for (Thing thing : things) {
                long thingDbid = thing.getDbid();
                if (thingDbid > 0) {
                    List<Connection> connections = AppDatabase.getDatabase().getConnectionsBySourceId(thingDbid);
                    thing.setConnections(connections);

                    List<ThingAccessCredential> thingAccessCredentials = AppDatabase.getDatabase().getThingAccessCredentialsByThingId(thing.getDbid());
                    thing.setThingAccessCredentials(thingAccessCredentials);
                }
                else {
                    if (Logging.WARN) Log.w(TAG, String.format("Thing %s ThingDbid = %s", thing.getFriendlyName(), thingDbid));
                }
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

        List<Thing> firstLevelThings = new ArrayList<>();
        for(Discovery discovery: discoveries) {
            List<? extends Thing> things1 = discovery.onDiscover();
            if (things1 != null) {
                firstLevelThings.addAll(things1);
            }
        }
        saveThings(firstLevelThings);

        for (Thing firstLevelThing : firstLevelThings) {
            Thing loadedThing = loadThing(firstLevelThing);
            exploreThing(loadedThing);
        }
    }

    private void exploreThing(Thing thing) {
        List<Thing> childThings;

        if (thing != null && (thing instanceof Accessor)) {
            childThings = ((Accessor) thing).getThings();

            if (childThings != null && childThings.size() != 0) {
                saveThings(childThings);
                for (Thing childThing : childThings) {
                    Thing loadedThing = loadThing(childThing);
                    exploreThing(loadedThing);
                }
            }
        }
    }

    public void saveThing(Thing thing) {
        if (thing != null) {
            if (Logging.VERBOSE) Log.v(TAG, String.format("Saving %s ...", thing.getFriendlyName()));

            AppDatabase.getDatabase().saveThing(thing);

            List<Connection> connections = thing.getConnections();
            if (connections != null) {
                for (Connection connection : connections) {
                    Thing destinationThing = connection.getDestination();
                    if (destinationThing != null) {
                        saveThing(destinationThing);
                    }
                    AppDatabase.getDatabase().saveConnection(connection);
                }
            }

            List<ThingAccessCredential> thingAccessCredentials = thing.getThingAccessCredentials();
            if (thingAccessCredentials != null) {
                AppDatabase.getDatabase().saveThingAccessCredentials(thingAccessCredentials);
            }
        }
    }

    public void saveThings(List<Thing> things) {
        if (things != null) {
            for (Thing thing : things) {
                saveThing(thing);
            }
        }
    }

    public Thing loadThing(Thing thing) {
        if (thing == null) {
            return null;
        }
        else {
            Thing loadedThing = AppDatabase.getDatabase().loadThing(thing);

            if (loadedThing == null) {
                return null;
            }
            else {
                loadedThing.setConnections(AppDatabase.getDatabase().getConnectionsBySourceId(loadedThing.getDbid()));
                loadedThing.setThingAccessCredentials(AppDatabase.getDatabase().getThingAccessCredentialsByThingId(loadedThing.getDbid()));
            }
            return loadedThing;
        }
    }

    public boolean authenticateThing(Thing thing) {
        if (Logging.VERBOSE) Log.v(TAG, String.format("Authenticating %s ...", thing.getFriendlyName()));

        boolean result = thing.authenticate();
        saveThing(thing);

        return result;
    }

    public List<Boolean> authenticateThings(List<Thing> things) {
        List<Boolean> results = new ArrayList<>();

        if (things != null) {
            for (Thing thing : things) {
                results.add(authenticateThing(thing));
            }
        }

        return results;
    }
}
