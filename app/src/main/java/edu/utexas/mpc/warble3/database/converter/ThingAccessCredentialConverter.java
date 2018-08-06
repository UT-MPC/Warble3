package edu.utexas.mpc.warble3.database.converter;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.ThingAccessCredentialDb;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredentialFactory;
import edu.utexas.mpc.warble3.model.user.User;

public class ThingAccessCredentialConverter {
    public static final String TAG = "ThingAccessCredConv";

    public static ThingAccessCredential toThingAccessCredential(ThingAccessCredentialDb thingAccessCredentialDb) {
        if (thingAccessCredentialDb == null) {
            return null;
        }
        else {
            ThingAccessCredentialFactory factory = new ThingAccessCredentialFactory();
            ThingAccessCredential thingAccessCredential = factory.createThingAccessCredential(thingAccessCredentialDb.getCredentialClass());

            String username = thingAccessCredentialDb.getUsername();
            if ((username != null) && (!username.equals(""))) {
                thingAccessCredential.setUser(AppDatabase.getDatabase().getUserByUsername(username));
            }
            thingAccessCredential.fromStoreableText(thingAccessCredentialDb.getCredentialInfo());
            thingAccessCredential.setDbid(thingAccessCredentialDb.getDbid());

            return thingAccessCredential;
        }
    }

    public static List<ThingAccessCredential> toThingAccessCredentials(List<ThingAccessCredentialDb> thingAccessCredentialDbs) {
        if (thingAccessCredentialDbs == null) {
            return null;
        }
        else {
            List<ThingAccessCredential> thingAccessCredentials = new ArrayList<>();

            for (ThingAccessCredentialDb thingAccessCredentialDb : thingAccessCredentialDbs) {
                thingAccessCredentials.add(ThingAccessCredentialConverter.toThingAccessCredential(thingAccessCredentialDb));
            }
            return thingAccessCredentials;
        }
    }

    public static ThingAccessCredentialDb toThingAccessCredentialDb(ThingAccessCredential thingAccessCredential) {
        if (thingAccessCredential == null) {
            return null;
        }
        else {
            User user = thingAccessCredential.getUser();
            Thing thing = thingAccessCredential.getThing();

            ThingAccessCredentialDb thingAccessCredentialDb = new ThingAccessCredentialDb();
            thingAccessCredentialDb.setCredentialClass(thingAccessCredential.getClass().getName());
            thingAccessCredentialDb.setCredentialInfo(thingAccessCredential.toStoreableText());
            if (user == null) {
                thingAccessCredentialDb.setUsername(null);
            }
            else {
                thingAccessCredentialDb.setUsername(user.getUsername());
            }

            if (thing == null) {
                thingAccessCredentialDb.setThingId(0);
            }
            else if (thing.getDbid() == 0) {
                thingAccessCredentialDb.setThingId(AppDatabase.getDatabase().thingDbDao().getDbidByUuid(thing.getUuid()));
            }
            else {
                thingAccessCredentialDb.setThingId(thing.getDbid());
            }
            thingAccessCredentialDb.setDbid(thingAccessCredential.getDbid());

            return thingAccessCredentialDb;
        }
    }

    public static List<ThingAccessCredentialDb> toThingAccessCredentialDbs(List<ThingAccessCredential> thingAccessCredentials) {
        if (thingAccessCredentials == null) {
            return null;
        }
        else {
            List<ThingAccessCredentialDb> thingAccessCredentialDbs = new ArrayList<>();

            for (ThingAccessCredential thingAccessCredential : thingAccessCredentials) {
                thingAccessCredentialDbs.add(ThingAccessCredentialConverter.toThingAccessCredentialDb(thingAccessCredential));
            }
            return thingAccessCredentialDbs;
        }
    }
}
