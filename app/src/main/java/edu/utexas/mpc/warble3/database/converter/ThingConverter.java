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

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.ThingDb;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

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

                thing.setConnectionState(thingDb.getConnectionState());
                thing.setAuthenticationState(thingDb.getAuthenticationState());
                thing.setBindingState(thingDb.getBindingState());

                thing.setDbid(thingDb.getDbid());

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
        if ((thingDbs == null) || (thingDbs.size() == 0)) {
            return null;
        }
        else {
            List<Thing> things = new ArrayList<>();
            for (ThingDb thingDb : thingDbs) {
                things.add(toThing(thingDb));
            }

            if (things.size() == 0) {
                return null;
            }
            else {
                return things;
            }
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

            Class thingClass = thing.getClass();
            if (thingClass != null) {
                thingDb.setThingClass(thingClass.getName());
            }
            else {
                if (Logging.WARN) Log.w(TAG, String.format("thingClass in Thing %s is NULL", thing.getFriendlyName()));
            }

            thingDb.setAccessName(thing.getAccessName());
            thingDb.setAccessUsername(thing.getAccessUsername());
            thingDb.setAccessPasscode(thing.getAccessPasscode());

            thingDb.setManufacturerSerialNumber(thing.getManufacturerSerialNumber());
            thingDb.setManufacturerModelName(thing.getManufacturerModelName());
            thingDb.setManufacturerModelNumber(thing.getManufacturerModelNumber());
            thingDb.setManufacturerName(thing.getManufacturerName());

            thingDb.setThingConcreteType(thing.getThingConcreteType());

            thingDb.setCredentialRequired(thing.getCredentialRequired());

            thingDb.setConnectionState(thing.getConnectionState());
            thingDb.setAuthenticationState(thing.getAuthenticationState());
            thingDb.setBindingState(thing.getBindingState());

            thingDb.setDbid(thing.getDbid());

            return thingDb;
        }
    }

    public static List<ThingDb> toThingDbs(List<Thing> things) {
        if ((things == null) || (things.size() == 0)) {
            return null;
        }
        else {
            List<ThingDb> thingDbs = new ArrayList<>();
            for (Thing thing : things) {
                thingDbs.add(toThingDb(thing));
            }

            if (thingDbs.size() == 0) {
                return null;
            }
            else {
                return thingDbs;
            }
        }
    }
}
