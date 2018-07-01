package edu.utexas.mpc.warble3.model.feature;

import java.util.List;

import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.ThingState;

public interface Accessor {
    List<Thing> getThings();
    List<ThingState> getThingsState();

    Boolean updateThingState(Thing thing, ThingState thingState);
    Boolean updateThingState(Thing thing, ThingState thingState, Boolean postCheck);

    Boolean updateThingsState(List<Thing> things, List<ThingState> thingsState);
    Boolean updateThingsState(List<Thing> things, List<ThingState> thingsState, Boolean postCheck);
}
