package edu.utexas.mpc.warble3.model.thing.structure;

import java.util.Arrays;

public abstract class Camera extends Thing {
    public static final String TAG = "Camera";

    public Camera() {
        super();
        setMainTypes(Arrays.asList(THING_MAIN_TYPE.ACTUATOR));
        setFunctionTypes(Arrays.asList(THING_FUNCTION_TYPE.VISION));
        setConcreteTypes(Arrays.asList(THING_CONCRETE_TYPE.CAMERA));
    }
}
