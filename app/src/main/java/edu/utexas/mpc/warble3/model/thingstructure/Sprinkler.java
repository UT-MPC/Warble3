package edu.utexas.mpc.warble3.model.thingstructure;

import java.util.Arrays;

public abstract class Sprinkler extends Thing {
    public static final String TAG = "Sprinkler";

    public Sprinkler() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.GARDEN_MANAGEMENT));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.SPRINKLER));
    }
}
