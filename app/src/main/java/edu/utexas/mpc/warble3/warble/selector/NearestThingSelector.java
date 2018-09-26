package edu.utexas.mpc.warble3.warble.selector;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.warble.thing.ThingManager;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.util.Location;

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
