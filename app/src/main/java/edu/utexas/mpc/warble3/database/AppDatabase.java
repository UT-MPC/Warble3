package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserDb.class, ThingDb.class, ThingAccessCredentialDb.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDbDao userDbDao();
    public abstract ThingDbDao thingDbDao();
    public abstract ThingAccessCredentialDbDao thingAccessCredentialDbDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
