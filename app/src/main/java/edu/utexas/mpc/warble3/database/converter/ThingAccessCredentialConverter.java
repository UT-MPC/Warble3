package edu.utexas.mpc.warble3.database.converter;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.ThingAccessCredentialDb;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredentialFactory;

public class ThingAccessCredentialConverter {
    public static final String TAG = "ThingAccessCredentialConverter";

    public static ThingAccessCredential toThingAccessCredential(ThingAccessCredentialDb thingAccessCredentialDb) {
        if (thingAccessCredentialDb == null) {
            return null;
        }
        else {
            ThingAccessCredentialFactory factory = new ThingAccessCredentialFactory();
            ThingAccessCredential thingAccessCredential = factory.createThingAccessCredential(thingAccessCredentialDb.getCredentialClass());
            thingAccessCredential.fromStoreableText(thingAccessCredentialDb.getCredentialInfo());

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
        ThingAccessCredentialDb thingAccessCredentialDb = new ThingAccessCredentialDb();

        thingAccessCredentialDb.setCredentialClass(thingAccessCredential.getClass().getName());
        thingAccessCredentialDb.setCredentialInfo(thingAccessCredential.toStoreableText());
        thingAccessCredentialDb.setUsername(thingAccessCredential.getUser().getUsername());

        if (thingAccessCredential.getThing().getDbid() == 0) {
            thingAccessCredentialDb.setThingId(AppDatabase.getDatabase().thingDbDao().getDbidByUuid(thingAccessCredential.getThing().getUuid()));
        }
        else {
            thingAccessCredentialDb.setThingId(thingAccessCredential.getDbid());
        }

        return thingAccessCredentialDb;
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
