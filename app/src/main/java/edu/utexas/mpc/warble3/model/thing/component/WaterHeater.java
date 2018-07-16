package edu.utexas.mpc.warble3.model.thing.component;

import java.util.Arrays;

public abstract class WaterHeater extends Thing {
    public static final String TAG = "WaterHeater";

    public WaterHeater() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.HOUSE_UTILITY));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.WATER_HEATER));
    }
}
