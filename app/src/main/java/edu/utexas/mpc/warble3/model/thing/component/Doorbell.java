package edu.utexas.mpc.warble3.model.thing.component;

import java.util.Arrays;

public abstract class Doorbell extends Thing {
    public static final String TAG = "Alarm";

    public Doorbell() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.HOUSE_UTILITY));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.DOORBELL));
    }
}
