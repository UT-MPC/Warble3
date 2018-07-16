package edu.utexas.mpc.warble3.model.thing.component;

import java.util.ArrayList;
import java.util.List;

public abstract class Refrigerator extends Thing {
    public static final String TAG = "Refrigerator";

    public Refrigerator() {
        super();

        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACTUATOR, THING_FUNCTION_TYPE.FOOD_DRINK_STORAGE));

        setThingTypes(thingTypes);
        setThingConcreteType(THING_CONCRETE_TYPE.REFRIGERATOR);
    }
}
