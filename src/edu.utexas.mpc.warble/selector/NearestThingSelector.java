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
 */

package edu.utexas.mpc.warble.selector;

import edu.utexas.mpc.warble.thing.ThingManager;
import edu.utexas.mpc.warble.thing.component.Thing;
import edu.utexas.mpc.warble.thing.util.Location;

import java.util.ArrayList;
import java.util.List;

public class NearestThingSelector extends AbstractSelector {
    private static String TAG = "NearestThingSelector";

    private Location currentLocation;

    public NearestThingSelector(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public List<Thing> fetch() {
        return select(ThingManager.getInstance().getThings());
    }

    @Override
    public List<Thing> select(List<Thing> things) {
        if (things == null || things.size() == 0) {
            return null;
        }

        List<Thing> returnThings = new ArrayList<>();
        Thing nearestThing = null;
        double nearestDistance;

        nearestDistance = getIndoorDistance(things.get(0).getLocation()) + 1;
        for (Thing thing : things) {
            double distance = getIndoorDistance(thing.getLocation());

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestThing = thing;
            }
        }

        returnThings.add(nearestThing);

        return returnThings;
    }

    private double getIndoorDistance(Location thingLocation) {
        double result = Math.pow(thingLocation.getIndoorX() - currentLocation.getIndoorX(), 2)
                + Math.pow(thingLocation.getIndoorY() - currentLocation.getIndoorY(), 2)
                + Math.pow(thingLocation.getIndoorZ() - currentLocation.getIndoorZ(), 2);
        result = Math.sqrt(result);

        return result;
    }
}
