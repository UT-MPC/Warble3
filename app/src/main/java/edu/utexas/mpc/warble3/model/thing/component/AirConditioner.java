package edu.utexas.mpc.warble3.model.thing.component;

import java.util.ArrayList;
import java.util.List;

public abstract class AirConditioner extends Thing {
    public static final String TAG = "AirConditioner";

    public AirConditioner() {
        super();

        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACTUATOR, THING_FUNCTION_TYPE.ROOM_CONDITIONING));

        setThingTypes(thingTypes);
        setThingConcreteType(THING_CONCRETE_TYPE.AIR_CONDITIONER);
    }
}
