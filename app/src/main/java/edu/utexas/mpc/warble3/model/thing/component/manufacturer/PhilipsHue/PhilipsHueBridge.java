package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Bridge;
import edu.utexas.mpc.warble3.model.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.THING_FUNCTION_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.THING_MAIN_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;
import edu.utexas.mpc.warble3.model.thing.component.ThingType;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;

public class PhilipsHueBridge extends Bridge {
    private static final String TAG = "PhilipsHueBridge";

    public PhilipsHueBridge() {
        super();

        // Set Thing Types
        List<ThingType> thingTypes = new ArrayList<>();
        thingTypes.add(new ThingType(THING_MAIN_TYPE.ACCESSOR, THING_FUNCTION_TYPE.ACCESSOR));
        setThingTypes(thingTypes);

        // Set Thing Concrete Type
        setThingConcreteType(THING_CONCRETE_TYPE.BRIDGE);

        // Set Discoveries
        List<Discovery> discoveries = new ArrayList<>();
        discoveries.add(new PhilipsHueUPnPDiscovery());
        setDiscoveries(discoveries);
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

    @Override
    public String toString() {
        String string = super.toString();
        string += String.format(", Connections: %s", getConnections().toString());
        string += String.format(", TAG: \"%s\"", TAG);
        return string;
    }
}
