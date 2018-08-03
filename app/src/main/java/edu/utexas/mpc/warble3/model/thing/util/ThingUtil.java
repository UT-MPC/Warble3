package edu.utexas.mpc.warble3.model.thing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class ThingUtil {
    public static HashMap<THING_CONCRETE_TYPE, List<Thing>> toThingHashMapByConcreteType(List<Thing> things) {
        if (things == null) {
            return null;
        }
        else {
            HashMap<THING_CONCRETE_TYPE, List<Thing>> thingsHashMap = new HashMap<>();

            for (Thing thing : things) {
                List<Thing> listThings = thingsHashMap.get(thing.getThingConcreteType());
                if (listThings == null) listThings = new ArrayList<>();
                listThings.add(thing);
                thingsHashMap.put(thing.getThingConcreteType(), listThings);
            }

            return thingsHashMap;
        }
    }
}
