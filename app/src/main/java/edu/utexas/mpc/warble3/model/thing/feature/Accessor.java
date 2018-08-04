package edu.utexas.mpc.warble3.model.thing.feature;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;

public interface Accessor {
    List<Thing> getThings();
    List<ThingState> getThingsState();

    boolean updateThingState(Thing thing, ThingState thingState);
    boolean updateThingState(Thing thing, ThingState thingState, boolean postCheck);

    boolean updateThingsState(List<Thing> things, List<ThingState> thingsState);
    boolean updateThingsState(List<Thing> things, List<ThingState> thingsState, boolean postCheck);
}
