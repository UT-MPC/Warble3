package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.util.List;

import edu.utexas.mpc.warble3.database.converter.ConnectionConverter;
import edu.utexas.mpc.warble3.database.converter.ThingConverter;
import edu.utexas.mpc.warble3.database.converter.UserConverter;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.user.User;
import edu.utexas.mpc.warble3.setup.AppDatabaseInterface;
import edu.utexas.mpc.warble3.util.Logging;

@Database(entities = {UserDb.class, ThingDb.class, ConnectionDb.class, ThingAccessCredentialDb.class},
        version = 5,
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

    @Override
    public void deleteAllUsers() {
        getDatabase().userDbDao().deleteAllUserDbs();
    }

    // Thing
    public List<Thing> getThings() {
        List<ThingDb> thingDbs = getDatabase().thingDbDao().getAllThingDbs();

        if (thingDbs.size() == 0) {
            return null;
        }
        else {
            return ThingConverter.toThings(thingDbs);
        }
    }

    public Thing getThingByDbid(long dbid) {
        return ThingConverter.toThing(getDatabase().thingDbDao().getThingDb(dbid));
    }

    public Thing getThingByUuid(String uuid) {
        return ThingConverter.toThing(getDatabase().thingDbDao().getThingDbByUuid(uuid));
    }

    private void addThing(Thing thing) {
        getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
    }

    @Override
    public void saveThing(Thing thing) {
        long sourceThingDbId;

        if (getThingByUuid(thing.getUuid()) != null) {
            getDatabase().thingDbDao().update(ThingConverter.toThingDb(thing));
            sourceThingDbId = AppDatabase.getDatabase().thingDbDao().getThingDbByUuid(thing.getUuid()).getDbid();
        }
        else {
            sourceThingDbId = getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
        }

        for (Connection connection : thing.getConnections()) {
            if (connection.getDestination() == null) {
                saveConnection(connection, sourceThingDbId, 0);
            }
            else {
                saveConnection(connection, sourceThingDbId, thingDbDao().getThingDbByUuid(connection.getDestination().getUuid()).getDbid());
            }
        }
    }

    @Override
    public void saveThings(List<Thing> things) {
        if (things != null) {
            for (Thing thing : things) {
                saveThing(thing);
            }
        }
    }

    // Connection
    @Override
    public List<Connection> getConnections() {
        return null;
    }

    @Override
    public void saveConnection(Connection connection, long sourceThingDbId, long destThingDbId) {
        ConnectionDb connectionDb = ConnectionConverter.toConnectionDb(connection);
        List<ConnectionDb> dbConnectionDbs = getDatabase().connectionDbDao().getConnectionDbBySourceDestinationId(connectionDb.getSourceId(), connectionDb.getDestinationId());
        ConnectionDb foundConnectionDb = null;

        boolean found = false;
        for (ConnectionDb dbConnectionDb : dbConnectionDbs) {
            if (connectionDb.equals(dbConnectionDb)) {
                found = true;
                foundConnectionDb = dbConnectionDb;
                break;
            }
        }

        if (found) {
            connectionDb.setDbid(foundConnectionDb.getDbid());
            getDatabase().connectionDbDao().update(connectionDb);
        }
        else {
            getDatabase().connectionDbDao().insert(connectionDb);
        }
    }

    @Override
    public void saveConnections(List<Connection> connections, long sourceThingDbId, long destThingDbId) {
        if (connections != null) {
            for (Connection connection : connections) {
                saveConnection(connection, sourceThingDbId, destThingDbId);
            }
        }
    }
}
