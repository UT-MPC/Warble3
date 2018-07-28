package edu.utexas.mpc.warble3.database.converter;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.ThingDb;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.util.Logging;

public class ThingConverter {
    private static final String TAG = "ThingConverter";

    public static Thing toThing(ThingDb thingDb) {
        if (thingDb == null) {
            return null;
        }
        else {
            try {
                Class<?> thingClass = Class.forName(thingDb.getThingClass());
                Constructor<?> thingClassConstructor = thingClass.getConstructor();
                Object object = thingClassConstructor.newInstance();

                Thing thing = (Thing) object;

                thing.setName(thingDb.getName());
                thing.setFriendlyName(thingDb.getFriendlyName());

                thing.setUuid(thingDb.getUuid());

                thing.setAccessName(thingDb.getAccessName());
                thing.setAccessUsername(thingDb.getAccessUsername());
                thing.setAccessPasscode(thingDb.getAccessPasscode());

                thing.setManufacturerSerialNumber(thingDb.getManufacturerSerialNumber());
                thing.setManufacturerModelName(thingDb.getManufacturerModelName());
                thing.setManufacturerModelNumber(thingDb.getManufacturerModelNumber());
                thing.setManufacturerName(thingDb.getManufacturerName());

                thing.setThingConcreteType(thingDb.getThingConcreteType());

                thing.setCredentialRequired(thingDb.getCredentialRequired());

                thing.onPostLoad(thingDb.getDbid());

                return (Thing) object;
            }
            catch (ClassNotFoundException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Thing Class %s is NOT found", thingDb.getThingClass()));
                return null;
            }
            catch (NoSuchMethodException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Thing Class Constructor %s is NOT found", thingDb.getThingClass()));
                return null;
            }
            catch (IllegalAccessException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Thing Class %s has illegal access", thingDb.getThingClass()));
                return null;
            }
            catch (InstantiationException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Thing Class %s has instantiation error", thingDb.getThingClass()));
                return null;
            }
            catch (InvocationTargetException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Thing Class %s has invocation target error", thingDb.getThingClass()));
                return null;
            }
        }
    }

    public static List<Thing> toThings(List<ThingDb> thingDbs) {
        if (thingDbs == null || thingDbs.size() == 0) {
            return null;
        }
        else {
            List<Thing> things = new ArrayList<>();
            for (ThingDb thingDb : thingDbs) {
                things.add(toThing(thingDb));
            }
            return things;
        }
    }

    public static ThingDb toThingDb(Thing thing) {
        if (thing == null) {
            return null;
        }
        else {
            ThingDb thingDb = new ThingDb();

            thingDb.setName(thing.getName());
            thingDb.setFriendlyName(thing.getFriendlyName());

            thingDb.setUuid(thing.getUuid());

            thingDb.setThingClass(thing.getClass().getName());

            thingDb.setAccessName(thing.getAccessName());
            thingDb.setAccessUsername(thing.getAccessUsername());
            thingDb.setAccessPasscode(thing.getAccessPasscode());

            thingDb.setManufacturerSerialNumber(thing.getManufacturerSerialNumber());
            thingDb.setManufacturerModelName(thing.getManufacturerModelName());
            thingDb.setManufacturerModelNumber(thing.getManufacturerModelNumber());
            thingDb.setManufacturerName(thing.getManufacturerName());

            thingDb.setThingConcreteType(thing.getThingConcreteType());

            thingDb.setCredentialRequired(thing.getCredentialRequired());

            return thingDb;
        }
    }

    public static List<ThingDb> toThingDbs(List<Thing> things) {
        if (things == null || things.size() == 0) {
            return null;
        }
        else {
            List<ThingDb> thingDbs = new ArrayList<>();
            for (Thing thing : things) {
                thingDbs.add(toThingDb(thing));
            }
            return thingDbs;
        }
    }
}
