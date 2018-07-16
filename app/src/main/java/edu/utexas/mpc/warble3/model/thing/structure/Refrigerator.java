package edu.utexas.mpc.warble3.model.thing.structure;

import java.util.Arrays;

public abstract class Refrigerator extends Thing {
    public static final String TAG = "Refrigerator";

    public Refrigerator() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.FOOD_DRINK_STORAGE));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.REFRIGERATOR));
    }
}
