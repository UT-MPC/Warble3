package edu.utexas.mpc.warble3.setup;

import android.app.Application;
import android.content.Context;

import edu.utexas.mpc.warble3.database.AppDatabase;

public class WarbleApplication extends Application {
    public static final String TAG = "WarbleApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        initialization();
    }

    private void initialization() {
        Context appContext = getApplicationContext();

        AppDatabase.initializeDatabase(appContext);
    }
}
