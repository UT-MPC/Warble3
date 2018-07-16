package edu.utexas.mpc.warble3.model.thing.component;

public class ThingType {
    public static final String TAG = "ThingType";

    private THING_MAIN_TYPE thingMainType;
    private THING_FUNCTION_TYPE thingFunctionType;

    public ThingType(THING_MAIN_TYPE thingMainType, THING_FUNCTION_TYPE thingFunctionType) {
        this.thingMainType = thingMainType;
        this.thingFunctionType = thingFunctionType;
    }

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

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s %s", thingMainType, thingFunctionType);
        return string;
    }
}
