package edu.utexas.mpc.warble3.model.thing.component;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.feature.Accessor;

public abstract class Bridge extends Thing implements Accessor {
    public static final String TAG = "Bridge";

    public Bridge() {
        super();

        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACCESSOR, THING_FUNCTION_TYPE.ACCESSOR));

        setThingTypes(thingTypes);
        setThingConcreteType(THING_CONCRETE_TYPE.BRIDGE);
    }
}
