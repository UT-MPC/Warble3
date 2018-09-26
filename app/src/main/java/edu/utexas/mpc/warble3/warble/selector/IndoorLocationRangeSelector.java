package edu.utexas.mpc.warble3.warble.selector;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.warble.thing.ThingManager;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.util.Location;

public class IndoorLocationRangeSelector extends AbstractSelector {
    private static String TAG = "LocationRangeSelector";

    private Location currentLocation;
    private double indoorRange;

    public IndoorLocationRangeSelector(Location currentLocation, double indoorRange) {
        this.currentLocation = currentLocation;
        this.indoorRange = indoorRange;
    }

    @Override
    public List<Thing> fetch() {
        return select(ThingManager.getInstance().getThings());
    }

    @Override
    public List<Thing> select(List<Thing> things) {
        if (things == null) {
            return null;
        }

        List<Thing> returnThings = new ArrayList<>();

        for (Thing thing : things) {
            if (getIndoorDistance(thing.getLocation()) < indoorRange) {
                returnThings.add(thing);
            }
        }

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
