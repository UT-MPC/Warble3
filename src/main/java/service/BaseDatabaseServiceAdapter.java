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

package service;

import interaction.Interaction;
import thing.component.Thing;
import thing.connection.Connection;
import thing.credential.ThingAccessCredential;
import user.User;

import java.util.List;
import java.util.logging.Logger;

public abstract class BaseDatabaseServiceAdapter extends ServiceAdapter {
    private static final String TAG = BaseDatabaseServiceAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public BaseDatabaseServiceAdapter() {
        super();
    }

    public abstract void    addUser(User newUser);
    public abstract User    getUserByUsername(String username);
    public abstract void    deleteAllUsers();

    public abstract List<Thing>     getThings();
    public abstract Thing           getThingByUuid(String uuid);
    public abstract Thing           getThingByDbid(long dbid);
    public abstract long            saveThing(Thing thing);
    public abstract List<Long>      saveThings(List<Thing> things);
    public abstract Thing           loadThing(Thing thing);
    public abstract void            deleteAllThings();

    public abstract List<Connection>    getConnections();
    public abstract Connection          getConnectionByDbid(long dbid);
    public abstract List<Connection>    getConnectionsBySourceId(long thingDbid);
    public abstract List<Connection>    getConnectionsByDestinationId(long thingDbid);
    public abstract long                saveConnection(Connection connection);
    public abstract List<Long>          saveConnections(List<Connection> connections);
    public abstract Connection          loadConnection(Connection connection);
    public abstract void                deleteAllConnections();

    public abstract List<ThingAccessCredential>     getThingAccessCredentials();
    public abstract ThingAccessCredential           getThingAccessCredentialByDbid(long dbid);
    public abstract List<ThingAccessCredential>     getThingAccessCredentialsByThingId(long thingId);
    public abstract long                            saveThingAccessCredential(ThingAccessCredential thingAccessCredential);
    public abstract List<Long>                      saveThingAccessCredentials(List<ThingAccessCredential> thingAccessCredentials);
    public abstract ThingAccessCredential           loadThingAccessCredential(ThingAccessCredential thingAccessCredential);
    public abstract void                            deleteAllThingAccessCredentials();

    public abstract List<Interaction>   getInteraction();
    public abstract long                saveInteraction(Interaction interaction);
    public abstract void                deleteAllInteractions();
}
