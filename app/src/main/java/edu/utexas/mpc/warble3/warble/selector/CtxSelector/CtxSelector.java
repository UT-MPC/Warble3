package edu.utexas.mpc.warble3.warble.selector.CtxSelector;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.warble.selector.AbstractSelector;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Agent.MobileAgent;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.AbstractContextArr;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.ThingAction;
import edu.utexas.mpc.warble3.warble.selector.ThingConcreteTypeSelector;
import edu.utexas.mpc.warble3.warble.thing.ThingManager;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class CtxSelector extends AbstractSelector {
    private static String TAG = "ContextSelector";
    private ThingConcreteTypeSelector typeFilter;
    private static MobileAgent myAgent;
    private AbstractContextArr context;

    public void setDeviceType(THING_CONCRETE_TYPE... thingConcreteTypes){
        typeFilter = new ThingConcreteTypeSelector(thingConcreteTypes);
    }

    public void setContext (AbstractContextArr newContext){
        this.context = newContext;
    }
    @Override
    public List<Thing> fetch() {
        return select(ThingManager.getInstance().getThings());
    }

    @Override
    public List<Thing> select(List<Thing> things) {
        List<Thing> filteredThings = typeFilter.select(things);
        ThingAction chosenAction = myAgent.getFilteredConfiguration(filteredThings, context, null);
        ArrayList<Thing> returnList = new ArrayList<>();
        chosenAction.doAction();
        returnList.add(chosenAction.getDevice());
        return returnList;
    }

    public void doAction(){

    }


}
