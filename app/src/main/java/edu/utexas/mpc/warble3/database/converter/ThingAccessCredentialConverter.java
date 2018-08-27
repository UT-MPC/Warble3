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

package edu.utexas.mpc.warble3.database.converter;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.ThingAccessCredentialDb;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredentialFactory;
import edu.utexas.mpc.warble3.warble.user.User;

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

            long thingDbid = thingAccessCredentialDb.getThingId();
            if (thingDbid != 0) {
                thingAccessCredential.setThing(AppDatabase.getDatabase().getThingByDbid(thingDbid));
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
