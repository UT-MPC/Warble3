package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.converter.UserConverter;
import edu.utexas.mpc.warble3.model.user.User;
import edu.utexas.mpc.warble3.setup.AppDatabaseInterface;

@Database(entities = {UserDb.class, ThingDb.class, ThingAccessCredentialDb.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase implements AppDatabaseInterface {
    private static AppDatabase INSTANCE;

    public abstract UserDbDao userDbDao();
    public abstract ThingDbDao thingDbDao();
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

    @Override
    public List<User> getAllUsers() {
        List<UserDb> userDbs = getDatabase().userDbDao().getAllUserDbs();

        List<User> users = new ArrayList<>();
        for (UserDb userDb : userDbs) {
            users.add(UserConverter.toUser(userDb));
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        UserDb userDb = getDatabase().userDbDao().getUserDb(username);
        return UserConverter.toUser(userDb);
    }

    @Override
    public void deleteAllUsers() {
        getDatabase().userDbDao().deleteAllUserDbs();
    }

    // Thing


    // Correlation
}
