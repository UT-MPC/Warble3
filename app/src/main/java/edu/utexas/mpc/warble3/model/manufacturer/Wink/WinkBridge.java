package edu.utexas.mpc.warble3.model.manufacturer.Wink;

import java.util.List;

import edu.utexas.mpc.warble3.model.Bridge;
import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.ThingState;

public class WinkBridge extends Bridge {
    public WinkBridge() {
        super();
    }

    @Override
    public List<Thing> getThings() {
        return null;
    }

    @Override
    public List<ThingState> getThingsState() {
        return null;
    }

    @Override
    public Boolean updateThingState(Thing thing, ThingState thingState) {
        return null;
    }

    @Override
    public Boolean updateThingState(Thing thing, ThingState thingState, Boolean postCheck) {
        return null;
    }

    @Override
    public Boolean updateThingsState(List<Thing> things, List<ThingState> thingsState) {
        return null;
    }

    @Override
    public Boolean updateThingsState(List<Thing> things, List<ThingState> thingsState, Boolean postCheck) {
        return null;
    }
}
