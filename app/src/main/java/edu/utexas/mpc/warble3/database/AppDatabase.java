package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.converter.ConnectionConverter;
import edu.utexas.mpc.warble3.database.converter.ThingAccessCredentialConverter;
import edu.utexas.mpc.warble3.database.converter.ThingConverter;
import edu.utexas.mpc.warble3.database.converter.UserConverter;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.user.User;
import edu.utexas.mpc.warble3.setup.AppDatabaseInterface;

@Database(entities = {UserDb.class, ThingDb.class, ConnectionDb.class, ThingAccessCredentialDb.class},
        version = 7,
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
    public long saveThing(Thing thing) {
        long thingDbid = thing.getDbid();
        String thingUuid = thing.getUuid();
        if ((thingDbid != 0) && (getThingByDbid(thingDbid) != null)) {
            getDatabase().thingDbDao().update(ThingConverter.toThingDb(thing));
        }
        else if ((thingUuid != null) && (!thingUuid.equals("")) && (getThingByUuid(thingUuid) != null)) {
            getDatabase().thingDbDao().update(ThingConverter.toThingDb(thing));
        }
        else {
            thingDbid = getDatabase().thingDbDao().insert(ThingConverter.toThingDb(thing));
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

    public void deleteAllThings() {
        getDatabase().thingDbDao().deleteAllThingDbs();
    }

    // Connection
    @Override
    public List<Connection> getConnections() {
        return null;
    }

    public Connection getConnectionByDbid(long dbid) {
        return ConnectionConverter.toConnection(getDatabase().connectionDbDao().getConnectionDbByDbid(dbid));
    }

    @Override
    public long saveConnection(Connection connection) {
        long connectionDbid = connection.getDbid();
        if ((connectionDbid != 0) && (getConnectionByDbid(connectionDbid) != null)) {
            getDatabase().connectionDbDao().update(ConnectionConverter.toConnectionDb(connection));
        }
        else {
            connectionDbid = getDatabase().connectionDbDao().insert(ConnectionConverter.toConnectionDb(connection));
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

    public void deleteAllConnections() {
        getDatabase().connectionDbDao().deleteAllConnectionDbs();
    }

    // ThingAccessCredential
    public void addThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        AppDatabase.getDatabase().thingAccessCredentialDbDao()
                .insert(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
    }

    public List<ThingAccessCredential> getThingAccessCredentials() {
        return ThingAccessCredentialConverter.toThingAccessCredentials(AppDatabase.getDatabase().thingAccessCredentialDbDao().getAllThingAccessCredentialDbs());
    }

    public ThingAccessCredential getThingAccessCredentialByDbid(long dbid) {
        return ThingAccessCredentialConverter.toThingAccessCredential(AppDatabase.getDatabase().thingAccessCredentialDbDao().getThingAccessCredentialDbByDbid(dbid));
    }

    public void deleteAllThingAccessCredentials() {
        AppDatabase.getDatabase().thingAccessCredentialDbDao().deleteAllThingAccessCredentialDbs();
    }

    public long saveThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        long thingAccessCredentialDbid = thingAccessCredential.getDbid();

        if ((thingAccessCredentialDbid != 0) && (getThingAccessCredentialByDbid(thingAccessCredentialDbid) != null)) {
            getDatabase().thingAccessCredentialDbDao().update(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
        }
        else {
            thingAccessCredentialDbid = getDatabase().thingAccessCredentialDbDao().insert(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
            thingAccessCredential.onPostStore(thingAccessCredentialDbid);
        }
        return thingAccessCredentialDbid;
    }

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
}
