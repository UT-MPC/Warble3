package edu.utexas.mpc.warble3.model.thing.structure;

import java.util.Arrays;

public abstract class Lock extends Thing {
    public static final String TAG = "Lock";

    public Lock() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.SECURITY));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.LOCK));
    }

}
