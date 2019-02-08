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
 */

package thing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import context.Context;
import service.BaseDatabaseServiceAdapter;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;
import service.ServiceAdapterManager;
import service.ServiceAdapterUser;
import thing.command.Command;
import thing.command.CommandCaller;
import thing.command.Response;
import thing.component.Thing;
import thing.connection.Connection;
import thing.credential.ThingAccessCredential;
import thing.discovery.Discovery;
import thing.feature.Hub;

public class ThingManager implements ServiceAdapterUser {
    private static final String TAG = ThingManager.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private ServiceAdapterManager serviceAdapterManager = null;
    private CommandCaller commandCaller;

    public ThingManager() {
    }

    public List<Thing> getThings() {
        BaseDatabaseServiceAdapter databaseServiceAdapter = ((BaseDatabaseServiceAdapter) serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE));

        List<Thing> things = databaseServiceAdapter.getThings();

        if ((things == null) || (things.size() == 0)) {
            return null;
        }
        else {
            HashMap<Long, Thing> thingHashMap = new HashMap<>();
            for (Thing thing : things) {
                thingHashMap.put(thing.getDbid(), thing);
            }

            for (Thing thing : things) {
                long thingDbid = thing.getDbid();
                if (thingDbid > 0) {
                    List<Connection> connections = databaseServiceAdapter.getConnectionsBySourceId(thingDbid);
                    if (connections != null) {
                        for (Connection connection : connections) {
                            if ((connection.getSource() != null) && (connection.getSource().getDbid() != 0)) {
                                connection.setSource(thingHashMap.get(connection.getSource().getDbid()));
                            }
                            else {
                                connection.setSource(null);
                            }

                            if ((connection.getDestination() != null) && (connection.getDestination().getDbid() != 0)) {
                                connection.setDestination(thingHashMap.get(connection.getDestination().getDbid()));
                            }
                            else {
                                connection.setDestination(null);
                            }
                        }
                        thing.setConnections(connections);
                    }

                    List<ThingAccessCredential> thingAccessCredentials = databaseServiceAdapter.getThingAccessCredentialsByThingId(thing.getDbid());
                    if (thingAccessCredentials != null) {
                        for(ThingAccessCredential thingAccessCredential : thingAccessCredentials) {
                            if ((thingAccessCredential.getThing() != null) && (thingAccessCredential.getThing().getDbid() != 0)) {
                                thingAccessCredential.setThing(thingHashMap.get(thingAccessCredential.getThing().getDbid()));
                            }
                            else {
                                thingAccessCredential.setThing(null);
                            }
                        }
                        thing.setThingAccessCredentials(thingAccessCredentials);
                    }
                }
                else {
                    LOGGER.warning(String.format("Thing %s ThingDbid = %s", thing.getFriendlyName(), thingDbid));
                }
            }

            return things;
        }
    }

    public void discover() {
        List<Discovery> discoveries = new ArrayList<>();

        // TODO: Discovery list has to be managed somewhere else
//        discoveries.add(new PhilipsHueUPnPDiscovery());
//        discoveries.add(new WinkDiscovery());
//        discoveries.add(new GEDiscovery());

        List<Thing> firstLevelThings = new ArrayList<>();
        for(Discovery discovery: discoveries) {
            List<? extends Thing> things1 = discovery.onDiscover(serviceAdapterManager);
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

        if (thing != null && (thing instanceof Hub)) {
            childThings = ((Hub) thing).getThings(serviceAdapterManager);

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
            LOGGER.fine(String.format("Saving %s ...", thing.getFriendlyName()));

            BaseDatabaseServiceAdapter databaseServiceAdapter = (BaseDatabaseServiceAdapter) serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE);

            databaseServiceAdapter.saveThing(thing);

            List<Connection> connections = thing.getConnections();
            if (connections != null) {
                for (Connection connection : connections) {
                    Thing destinationThing = connection.getDestination();
                    if (destinationThing != null) {
                        saveThing(destinationThing);
                    }
                    databaseServiceAdapter.saveConnection(connection);
                }
            }

            List<ThingAccessCredential> thingAccessCredentials = thing.getThingAccessCredentials();
            if (thingAccessCredentials != null) {
                databaseServiceAdapter.saveThingAccessCredentials(thingAccessCredentials);
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
            BaseDatabaseServiceAdapter databaseServiceAdapter = (BaseDatabaseServiceAdapter) serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE);

            Thing loadedThing = databaseServiceAdapter.loadThing(thing);

            return getThing(databaseServiceAdapter, loadedThing);
        }
    }

    public Thing getThing(String uuid) {
        if (uuid.equals("")) {
            return null;
        } else {
            BaseDatabaseServiceAdapter databaseServiceAdapter = (BaseDatabaseServiceAdapter) serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE);

            Thing loadedThing = databaseServiceAdapter.getThingByUuid(uuid);

            return getThing(databaseServiceAdapter, loadedThing);
        }
    }

    private Thing getThing(BaseDatabaseServiceAdapter databaseServiceAdapter, Thing loadedThing) {
        if (loadedThing == null) {
            return null;
        } else {
            loadedThing.setConnections(databaseServiceAdapter.getConnectionsBySourceId(loadedThing.getDbid()));
            loadedThing.setThingAccessCredentials(databaseServiceAdapter.getThingAccessCredentialsByThingId(loadedThing.getDbid()));
        }
        return loadedThing;
    }

    public boolean authenticateThing(Thing thing) {
        //TODO: implement
//        LOGGER.fine(String.format("Authenticating %s ...", thing.getFriendlyName()));
//
//        boolean result = thing.authenticate();
//        saveThing(thing);
//
//        return result;
        return false;
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

    public Response sendCommand(Context context, Command command, Thing thing) {
        return commandCaller.call(context, command, thing);
    }

    @Override
    public void setServiceAdapter(ServiceAdapterManager serviceAdapterManager) {
        // TODO catch casting exception
        this.serviceAdapterManager = serviceAdapterManager;
    }
}
