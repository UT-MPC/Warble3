package edu.utexas.mpc.warble3.warble.selector.CtxSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.utexas.mpc.warble3.warble.selector.AbstractSelector;
import edu.utexas.mpc.warble3.warble.selector.ThingConcreteTypeSelector;
import edu.utexas.mpc.warble3.warble.thing.ThingManager;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class CtxSelector extends AbstractSelector {
    private static String TAG = "ContextSelector";
    private ThingConcreteTypeSelector typeFilter;


    public void setDeviceType(THING_CONCRETE_TYPE... thingConcreteTypes){
        typeFilter = new ThingConcreteTypeSelector(thingConcreteTypes);
    }
    @Override
    public List<Thing> fetch() {
        return select(ThingManager.getInstance().getThings());
    }

    @Override
    public List<Thing> select(List<Thing> things) {
        List<Thing> filteredThings = typeFilter.select(things);

        return returnThings;
    }

    public void doAction(){

    }


}
