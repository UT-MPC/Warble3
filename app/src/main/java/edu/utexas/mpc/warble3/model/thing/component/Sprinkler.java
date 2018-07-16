package edu.utexas.mpc.warble3.model.thing.component;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprinkler extends Thing {
    public static final String TAG = "Sprinkler";

    public Sprinkler() {
        super();

        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACTUATOR, THING_FUNCTION_TYPE.GARDEN_MANAGEMENT));

        setThingTypes(thingTypes);
        setThingConcreteType(THING_CONCRETE_TYPE.SPRINKLER);
    }
}
