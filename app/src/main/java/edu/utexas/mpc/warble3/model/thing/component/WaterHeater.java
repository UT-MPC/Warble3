package edu.utexas.mpc.warble3.model.thing.component;

import java.util.ArrayList;
import java.util.List;

public abstract class WaterHeater extends Thing {
    public static final String TAG = "WaterHeater";

    public WaterHeater() {
        super();

        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACTUATOR, THING_FUNCTION_TYPE.HOUSE_UTILITY));

        setThingTypes(thingTypes);
        setThingConcreteType(THING_CONCRETE_TYPE.WATER_HEATER);
    }
}
