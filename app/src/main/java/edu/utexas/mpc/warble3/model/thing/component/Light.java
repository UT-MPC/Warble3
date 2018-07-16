package edu.utexas.mpc.warble3.model.thing.component;

import java.util.Arrays;

public abstract class Light extends Thing {
    public static final String TAG = "Light";

    public Light() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.LIGHTING));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.LIGHT));
    }
}
