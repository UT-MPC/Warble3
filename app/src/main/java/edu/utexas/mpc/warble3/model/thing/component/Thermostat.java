package edu.utexas.mpc.warble3.model.thing.component;

import java.util.Arrays;

public abstract class Thermostat extends Thing {
    public static final String TAG = "Thermostat";

    public Thermostat() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR, THING_MAIN_TYPE.SENSOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.ROOM_CONDITIONING, THING_FUNCTION_TYPE.ROOM_CONDITIONING));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.THERMOSTAT, THING_CONCRETE_TYPE.THERMOSTAT));
    }
}
