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

package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.utexas.mpc.warble3.database.converter.ConnectionConverter;
import edu.utexas.mpc.warble3.database.converter.ThingAccessCredentialConverter;
import edu.utexas.mpc.warble3.database.converter.ThingConverter;
import edu.utexas.mpc.warble3.database.converter.UserConverter;
import edu.utexas.mpc.warble3.setup.AppDatabaseInterface;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.warble.thing.component.THING_AUTHENTICATION_STATE;
import edu.utexas.mpc.warble3.warble.thing.component.THING_BINDING_STATE;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONNECTION_STATE;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.connection.Connection;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.warble.user.User;

@Database(entities = {UserDb.class, ThingDb.class, ConnectionDb.class, ThingAccessCredentialDb.class},
        version = 8,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase implements AppDatabaseInterface {
    private static final String TAG = "AppDatabase";

    private static AppDatabase INSTANCE;

    public abstract UserDbDao userDbDao();
    public abstract ThingDbDao thingDbDao();
    public abstract ConnectionDbDao connectionDbDao();
    public abstract ThingAccessCredentialDbDao thingAccessCredentialDbDao();

    public static void initializeDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static AppDatabase getDatabase() throws NullPointerException {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        else {
            throw new NullPointerException("AppDatabase has not been initialized");
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    // User
    @Override
    public void addUser(User newUser) {
        getDatabase().userDbDao().insert(UserConverter.toUserDb(newUser));
    }

    private List<User> getUsers() {
        List<UserDb> userDbs = getDatabase().userDbDao().getAllUserDbs();

        if (userDbs.size() == 0) {
            return null;
        }
        else {
            return UserConverter.toUsers(userDbs);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        UserDb userDb = getDatabase().userDbDao().getUserDb(username);
        if (userDb == null) {
            return null;
        }
        else {
            return UserConverter.toUser(userDb);
        }
    }

    public User loadUser(User user) {
        return getUserFromDb(user);
    }

    private User getUserFromDb(User user) {
        if (user == null)
            return null;
        else {
            User returnUser = null;

            String username = user.getUsername();

            if ((returnUser == null) && (username != null) && (!username.equals("")))
                returnUser = getUserByUsername(username);

            return returnUser;
        }
    }

    @Override
    public void deleteAllUsers() {
        getDatabase().userDbDao().deleteAllUserDbs();
    }


    // Thing
    @Override
    public List<Thing> getThings() {
        List<ThingDb> thingDbs = getDatabase().thingDbDao().getAllThingDbs();

        if ((thingDbs == null) || (thingDbs.size() == 0)) {
            return null;
        }
        else {
            return ThingConverter.toThings(thingDbs);
        }
    }

    public Thing getThingByDbid(long dbid) {
        ThingDb thingDb = getDatabase().thingDbDao().getThingDb(dbid);
        if (thingDb == null) {
            return null;
        }
        else {
            Thing thing = ThingConverter.toThing(thingDb);
            thing.onPostLoad(thingDb.getDbid());
            return thing;
        }
    }

    public Thing getThingByUuid(String uuid) {
        ThingDb thingDb = getDatabase().thingDbDao().getThingDbByUuid(uuid);
        if (thingDb == null) {
            return null;
        }
        else {
            Thing thing = ThingConverter.toThing(thingDb);
            thing.onPostLoad(thingDb.getDbid());
            return thing;
        }
    }

    public Thing loadThing(Thing thing) {
        return getThingFromDb(thing);
    }

    private Thing getThingFromDb(Thing thing) {
        if (thing == null)
            return null;
        else {
            Thing returnThing = null;

            long thingDbid = thing.getDbid();
            if ((returnThing == null) && (thingDbid != 0)) {
                returnThing = getThingByDbid(thingDbid);
            }
            String thingUuid = thing.getUuid();
            if ((returnThing == null) && (thingUuid != null) && (!thingUuid.equals(""))) {
                returnThing = getThingByUuid(thingUuid);
            }

            return returnThing;
        }
    }

    private void addThing(Thing thing) {
        getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
    }

    @Override
    public long saveThing(Thing thing) {
        long thingDbid = 0;

        if (thing != null) {
            Thing storedThing = getThingFromDb(thing);

            if (storedThing == null) {
                thingDbid = getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
            }
            else {
                ThingDb updatedThingDb = ThingConverter.toThingDb(thing);
                thingDbid = storedThing.getDbid();
                updatedThingDb.setDbid(thingDbid);
                getDatabase().thingDbDao().update(updatedThingDb);
            }

            thing.onPostStore(thingDbid);
        }

        return thingDbid;
    }

    @Override
    public List<Long> saveThings(List<Thing> things) {
        List<Long> thingDbids = new ArrayList<>();
        if (things != null) {
            for (Thing thing : things) {
                thingDbids.add(saveThing(thing));
            }
        }

        if (thingDbids.size() == 0) {
            return null;
        }
        else {
            return thingDbids;
        }
    }

    @Override
    public void deleteAllThings() {
        getDatabase().thingDbDao().deleteAllThingDbs();
    }

    // Connection
    @Override
    public List<Connection> getConnections() {
        return null;
    }

    public Connection getConnectionByDbid(long dbid) {
        ConnectionDb connectionDb = getDatabase().connectionDbDao().getConnectionDbByDbid(dbid);
        if (connectionDb == null) {
            return null;
        }
        else {
            Connection connection = ConnectionConverter.toConnection(connectionDb);
            connection.onPostLoad(connectionDb.getDbid());
            return connection;
        }
    }

    public List<Connection> getConnectionsBySourceId(long thingDbid) {
        List<ConnectionDb> connectionDbs = getDatabase().connectionDbDao().getConnectionDbBySourceId(thingDbid);
        List<Connection> connections = ConnectionConverter.toConnections(connectionDbs);

        if (connections != null) {
            for (int i=0; i < connections.size(); i++) {
                Connection connection = connections.get(i);
                ConnectionDb connectionDb = connectionDbs.get(i);

                if ((connection != null) && (connectionDb != null)) {
                    connection.onPostLoad(connectionDb.getDbid());
                }
                else {
                    if (Logging.WARN) Log.w(TAG, "Either connection OR connectionDb is NULL");
                }
            }
        }

        return connections;
    }

    private List<Connection> getConnectionsByDestinationId(long thingDbid) {
        List<ConnectionDb> connectionDbs = getDatabase().connectionDbDao().getConnectionDbByDestinationId(thingDbid);
        List<Connection> connections = ConnectionConverter.toConnections(connectionDbs);

        if (connections != null) {
            for (int i=0; i < connections.size(); i++) {
                Connection connection = connections.get(i);
                ConnectionDb connectionDb = connectionDbs.get(i);

                if ((connection != null) && (connectionDb != null)) {
                    connection.onPostLoad(connectionDb.getDbid());
                }
                else {
                    if (Logging.WARN) Log.w(TAG, "Either connection OR connectionDb is NULL");
                }
            }
        }

        return connections;
    }

    public Connection loadConnection(Connection connection) {
        return getConnectionFromDb(connection);
    }

    private Connection getConnectionFromDb(Connection connection) {
        if (connection == null)
            return null;
        else {
            Connection returnConnection = null;

            long connectionDbid = connection.getDbid();
            if ((returnConnection == null) && (connectionDbid != 0)) {
                returnConnection = getConnectionByDbid(connectionDbid);
                returnConnection.onPostLoad(connectionDbid);
            }

            if (returnConnection == null) {
                Thing source = connection.getSource();
                if (source != null) {
                    List<Connection> connectionsBySourceId = getConnectionsBySourceId(source.getDbid());
                    for (Connection connectionBySourceId : connectionsBySourceId) {
                        if (connection.equals(connectionBySourceId)) {
                            returnConnection = connectionBySourceId;
                            break;
                        }
                    }
                }
            }

            return returnConnection;
        }
    }

    @Override
    public long saveConnection(Connection connection) {
        long connectionDbid = 0;

        if (connection != null) {
            Connection storedConnection = getConnectionFromDb(connection);

            if (storedConnection == null) {
                connectionDbid = getDatabase().connectionDbDao().insert(ConnectionConverter.toConnectionDb(connection));
            }
            else {
                ConnectionDb updatedConnectionDb = ConnectionConverter.toConnectionDb(connection);
                connectionDbid = storedConnection.getDbid();
                updatedConnectionDb.setDbid(connectionDbid);
                getDatabase().connectionDbDao().update(updatedConnectionDb);
            }

            connection.onPostStore(connectionDbid);
        }

        return connectionDbid;
    }

    @Override
    public List<Long> saveConnections(List<Connection> connections) {
        if (connections != null) {
            List<Long> connectionDbids = new ArrayList<>();
            for (Connection connection : connections) {
                connectionDbids.add(saveConnection(connection));
            }

            if (connectionDbids.size() == 0) {
                return null;
            }
            else {
                return connectionDbids;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteAllConnections() {
        getDatabase().connectionDbDao().deleteAllConnectionDbs();
    }

    // ThingAccessCredential
    private void addThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        AppDatabase.getDatabase().thingAccessCredentialDbDao()
                .insert(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
    }

    @Override
    public List<ThingAccessCredential> getThingAccessCredentials() {
        List<ThingAccessCredentialDb> thingAccessCredentialDbs = AppDatabase.getDatabase().thingAccessCredentialDbDao().getAllThingAccessCredentialDbs();
        List<ThingAccessCredential> thingAccessCredentials = ThingAccessCredentialConverter.toThingAccessCredentials(thingAccessCredentialDbs);

        if (thingAccessCredentials != null) {
            for (int i=0; i < thingAccessCredentials.size(); i++) {
                ThingAccessCredential thingAccessCredential = thingAccessCredentials.get(i);
                ThingAccessCredentialDb thingAccessCredentialDb = thingAccessCredentialDbs.get(i);

                if ((thingAccessCredential != null) && (thingAccessCredentialDb != null)) {
                    thingAccessCredential.onPostLoad(thingAccessCredentialDb.getDbid());
                }
                else {
                    if (Logging.WARN) Log.w(TAG, "Either thingAccessCredential OR thingAccessCredentialDb is NULL");
                }
            }
        }

        return thingAccessCredentials;
    }

    public ThingAccessCredential getThingAccessCredentialByDbid(long dbid) {
        ThingAccessCredentialDb thingAccessCredentialDb = AppDatabase.getDatabase().thingAccessCredentialDbDao().getThingAccessCredentialDbByDbid(dbid);

        if (thingAccessCredentialDb == null) {
            return null;
        }
        else {
            ThingAccessCredential thingAccessCredential = ThingAccessCredentialConverter.toThingAccessCredential(thingAccessCredentialDb);
            thingAccessCredential.onPostLoad(thingAccessCredentialDb.getDbid());
            return thingAccessCredential;
        }
    }

    public List<ThingAccessCredential> getThingAccessCredentialsByThingId(long thingId) {
        List<ThingAccessCredentialDb> thingAccessCredentialDbs = AppDatabase.getDatabase().thingAccessCredentialDbDao().getThingAccessCredentialDbsByThingId(thingId);
        List<ThingAccessCredential> thingAccessCredentials = ThingAccessCredentialConverter.toThingAccessCredentials(thingAccessCredentialDbs);

        if (thingAccessCredentials != null) {
            for (int i=0; i < thingAccessCredentials.size(); i++) {
                ThingAccessCredential thingAccessCredential = thingAccessCredentials.get(i);
                ThingAccessCredentialDb thingAccessCredentialDb = thingAccessCredentialDbs.get(i);

                if ((thingAccessCredential != null) && (thingAccessCredentialDb != null)) {
                    thingAccessCredential.onPostLoad(thingAccessCredentialDb.getDbid());
                }
                else {
                    if (Logging.WARN) Log.w(TAG, "Either thingAccessCredential OR thingAccessCredentialDb is NULL");
                }
            }
        }

        return thingAccessCredentials;
    }

    public ThingAccessCredential loadThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        return getThingAccessCredentialFromDb(thingAccessCredential);
    }

    private ThingAccessCredential getThingAccessCredentialFromDb(ThingAccessCredential thingAccessCredential) {
        if (thingAccessCredential == null)
            return null;
        else {
            ThingAccessCredential returnThingAccessCredential = null;

            long thingAccessCredentialDbid = thingAccessCredential.getDbid();
            if ((returnThingAccessCredential == null) && (thingAccessCredentialDbid != 0)) {
                returnThingAccessCredential = getThingAccessCredentialByDbid(thingAccessCredentialDbid);
                returnThingAccessCredential.onPostLoad(thingAccessCredentialDbid);
            }

            return returnThingAccessCredential;
        }
    }

    @Override
    public void deleteAllThingAccessCredentials() {
        AppDatabase.getDatabase().thingAccessCredentialDbDao().deleteAllThingAccessCredentialDbs();
    }

    @Override
    public long saveThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        long thingAccessCredentialDbid = 0;

        if (thingAccessCredential != null) {
            ThingAccessCredential storedThingAccessCredential = getThingAccessCredentialFromDb(thingAccessCredential);

            if (storedThingAccessCredential == null)
                thingAccessCredentialDbid = getDatabase().thingAccessCredentialDbDao().insert(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
            else {
                ThingAccessCredentialDb updatedThingAccessCredentialDb = ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential);
                thingAccessCredentialDbid = storedThingAccessCredential.getDbid();
                updatedThingAccessCredentialDb.setDbid(thingAccessCredentialDbid);
                getDatabase().thingAccessCredentialDbDao().update(updatedThingAccessCredentialDb);
            }

            thingAccessCredential.onPostStore(thingAccessCredentialDbid);
        }

        return thingAccessCredentialDbid;
    }

    @Override
    public List<Long> saveThingAccessCredentials(List<ThingAccessCredential> thingAccessCredentials) {
        if (thingAccessCredentials != null) {
            List<Long> thingAccessCredentialDbids = new ArrayList<>();
            for (ThingAccessCredential thingAccessCredential : thingAccessCredentials) {
                thingAccessCredentialDbids.add(saveThingAccessCredential(thingAccessCredential));
            }

            if (thingAccessCredentialDbids.size() == 0) {
                return null;
            }
            else {
                return thingAccessCredentialDbids;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public void onInitialize() {
        getDatabase().thingDbDao().updateAllConnectionStates(THING_CONNECTION_STATE.INITIAL);
        getDatabase().thingDbDao().updateAllAuthenticationStates(THING_AUTHENTICATION_STATE.UNAUTHENTICATED);
        getDatabase().thingDbDao().updateAllBindingStates(THING_BINDING_STATE.UNBOUND);
    }

    @Override
    public void onTerminate() {
        getDatabase().thingDbDao().updateAllConnectionStates(THING_CONNECTION_STATE.INITIAL);
        getDatabase().thingDbDao().updateAllAuthenticationStates(THING_AUTHENTICATION_STATE.UNAUTHENTICATED);
        getDatabase().thingDbDao().updateAllBindingStates(THING_BINDING_STATE.UNBOUND);
    }

    // Logging
    @Override
    public String toString() {
        return "===== Database =====\n" +
                toStringUserDbs()
                + "\n" +
                toStringThingDbs()
                + "\n" +
                toStringConnectionDbs()
                + "\n" +
                toStringThingAccessCredentialDbs()
                + "\n====================";
    }

    private String toStringUserDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<UserDb> userDbs = getDatabase().userDbDao().getAllUserDbs();
        if (userDbs == null) {
            stringBuilder.append("Number of userDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of userDb : %d", userDbs.size()));
            for (UserDb userDb : userDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(userDb.toString());
            }
        }

        return stringBuilder.toString();
    }

    private String toStringThingDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ThingDb> thingDbs = getDatabase().thingDbDao().getAllThingDbs();
        if (thingDbs == null) {
            stringBuilder.append("Number of thingDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of thingDb : %d", thingDbs.size()));
            for (ThingDb thingDb : thingDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(thingDb.toString());
            }
        }

        return stringBuilder.toString();
    }

    private String toStringConnectionDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ConnectionDb> connectionDbs = getDatabase().connectionDbDao().getAllConnectionDbs();
        if (connectionDbs == null) {
            stringBuilder.append("Number of connectionDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of connectionDb : %d", connectionDbs.size()));
            for (ConnectionDb connectionDb : connectionDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(connectionDb.toString());
            }
        }

        return stringBuilder.toString();
    }

    private String toStringThingAccessCredentialDbs() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ThingAccessCredentialDb> thingAccessCredentialDbs = getDatabase().thingAccessCredentialDbDao().getAllThingAccessCredentialDbs();
        if (thingAccessCredentialDbs == null) {
            stringBuilder.append("Number of thingAccessCredentialDb : 0");
        }
        else {
            stringBuilder.append(String.format(Locale.getDefault(), "Number of thingAccessCredentialDbs : %d", thingAccessCredentialDbs.size()));
            for (ThingAccessCredentialDb thingAccessCredentialDb : thingAccessCredentialDbs) {
                stringBuilder.append("\n");
                stringBuilder.append("- ");
                stringBuilder.append(thingAccessCredentialDb.toString());
            }
        }

        return stringBuilder.toString();
    }
}
