package edu.utexas.mpc.warble3.model.thing.component;

import java.util.Arrays;

public abstract class Alarm extends Thing {
    public static final String TAG = "Alarm";

    public Alarm() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.SECURITY));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.ALARM));
    }
}
