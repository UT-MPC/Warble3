package edu.utexas.mpc.warble3.model.thing;

import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.ThingDb;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

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
        List<ThingDb> thingDbs = AppDatabase.getDatabase().thingDbDao().getAllThingDbs();
        return null;
    }
}
