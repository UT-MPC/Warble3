package edu.utexas.mpc.warble3.nat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.utexas.mpc.warble3.warble.thing.component.Light;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.warble.thing.component.Thermostat;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.util.Location;

public class ContinuousThread {
    public ContinuousThread() {
    }

    public void run() {
        List<THING_CONCRETE_TYPE> types = Arrays.asList(THING_CONCRETE_TYPE.LIGHT, THING_CONCRETE_TYPE.THERMOSTAT);
        Location currLoc = new Location(0, 0, 0, 0, 0);
        double threshold = 10;

        List<Thing> discoveredThings = Thing.discover();
        List<Thing> selectedThings = selectByType(discoveredThings, types, false);
        selectedThings = selectByNearestLocation(selectedThings, currLoc, threshold, true);

        // manually enact the plan
        for (Thing thing : selectedThings)
            if (thing instanceof Light)
                ((Light) thing).on(); //simplified, Command Pattern
            else if (thing instanceof Thermostat)
                ((Thermostat) thing).setTemperature(298);          //in Kelvin

        // continuously recheck the surrounding things
        while (true) {
            discoveredThings = Thing.discover();
            List<Thing> newSelectedThings = selectByType(discoveredThings, types, false);
            selectedThings = selectByNearestLocation(selectedThings, currLoc, threshold, true);
            if (!newSelectedThings.equals(selectedThings)){
                for (Thing thing : newSelectedThings)
                    if (thing instanceof Light)
                        ((Light) thing).on(); //simplified, Command Pattern
                    else if (thing instanceof Thermostat)
                        ((Thermostat) thing).setTemperature(298);          //in Kelvin

                
            }
            selectedThings = newSelectedThings;
            // exception handling omitted
            Thread.sleep(rediscoveryPeriod);
        }
    }

    public List<Thing> selectByType(List<Thing> things, List<THING_CONCRETE_TYPE> types, boolean inPlace) {
        if ((things == null) || (types == null)) return null;

        if (inPlace) {
            for (Thing thing : things) if (!types.contains(thing.getThingConcreteType())) things.remove(thing);
            return null;
        }
        else {
            List<Thing> selectedThings = new ArrayList<>();
            for (Thing thing : things) if (types.contains(thing.getThingConcreteType())) selectedThings.add(thing);
            return selectedThings;
        }
    }

    public List<Thing> selectByNearestLocation(List<Thing> things, final Location currentLoc, double threshold, boolean inPlace) {
        if (things == null) return null;

        List<Thing> sorted = Collections.sort(things, new Comparator<Thing>() {
            @Override
            public int compare(Thing t1, Thing t2) {
                Double distance1 = Location.getDistance(currentLoc, t1.getLocation());
                Double distance2 = Location.getDistance(currentLoc, t2.getLocation());
                if (distance1.equals(distance2)) return 0;
                return distance1 < distance2 ? -1 : 1;
            }
        });

        int splitIndex = 0;
        for (int i=0; i < sorted.size(); i++) {
            if (Location.getDistance(currentLoc, sorted.get(i)) > threshold) {
                splitIndex = i;
                break;
            }
        }

        if (inPlace) {
            things = sorted.subList(0, splitIndex);
            return things;
        }
        else
            return sorted.subList(0, splitIndex);
    }
}
