package edu.utexas.mpc.warble3.model.thing.component;

import java.util.ArrayList;
import java.util.List;

public abstract class Alarm extends Thing {
    public static final String TAG = "Alarm";

    public Alarm() {
        super();

        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACTUATOR, THING_FUNCTION_TYPE.SECURITY));

        setThingTypes(thingTypes);
        setThingConcreteType(THING_CONCRETE_TYPE.ALARM);
    }
}
