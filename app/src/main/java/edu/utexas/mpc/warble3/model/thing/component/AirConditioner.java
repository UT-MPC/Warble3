package edu.utexas.mpc.warble3.model.thing.component;

import java.util.Arrays;

public abstract class AirConditioner extends Thing {
    public static final String TAG = "AirConditioner";

    public AirConditioner() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.ROOM_CONDITIONING));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.AIR_CONDITIONER));
    }
}
