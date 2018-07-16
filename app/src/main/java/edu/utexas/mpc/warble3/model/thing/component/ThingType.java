package edu.utexas.mpc.warble3.model.thing.component;

public class ThingType {
    public static final String TAG = "ThingType";

    private THING_MAIN_TYPE thingMainType;
    private THING_FUNCTION_TYPE thingFunctionType;
    private THING_CONCRETE_TYPE thingConcreteType;

    public THING_MAIN_TYPE getThingMainType() {
        return thingMainType;
    }

    public void setThingMainType(THING_MAIN_TYPE thingMainType) {
        this.thingMainType = thingMainType;
    }

    public THING_FUNCTION_TYPE getThingFunctionType() {
        return thingFunctionType;
    }

    public void setThingFunctionType(THING_FUNCTION_TYPE thingFunctionType) {
        this.thingFunctionType = thingFunctionType;
    }

    public THING_CONCRETE_TYPE getThingConcreteType() {
        return thingConcreteType;
    }

    public void setThingConcreteType(THING_CONCRETE_TYPE thingConcreteType) {
        this.thingConcreteType = thingConcreteType;
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s %s %s", thingMainType, thingFunctionType, thingConcreteType);
        return string;
    }
}
