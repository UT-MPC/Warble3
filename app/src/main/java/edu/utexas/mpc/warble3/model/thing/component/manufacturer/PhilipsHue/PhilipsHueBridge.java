package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Bridge;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;
import edu.utexas.mpc.warble3.model.thing.credential.UsernamePasswordCredential;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;

public final class PhilipsHueBridge extends Bridge {
    private static final String TAG = "PhilipsHueBridge";

    public PhilipsHueBridge() {
        super();

        // Set Discoveries
        List<Discovery> discoveries = new ArrayList<>();
        discoveries.add(new PhilipsHueUPnPDiscovery());
        setDiscoveries(discoveries);

        // Set ThingAccessCredentialClasses
        List<Class> thingAccessCredentialClasses = new ArrayList<>();
        thingAccessCredentialClasses.add(UsernamePasswordCredential.class);
        setThingAccessCredentialClasses(thingAccessCredentialClasses);
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
    public boolean updateThingState(Thing thing, ThingState thingState) {
        return false;
    }

    @Override
    public boolean updateThingState(Thing thing, ThingState thingState, boolean postCheck) {
        return false;
    }

    @Override
    public boolean updateThingsState(List<Thing> things, List<ThingState> thingsState) {
        return false;
    }

    @Override
    public boolean updateThingsState(List<Thing> things, List<ThingState> thingsState, boolean postCheck) {
        return false;
    }

    @Override
    public boolean authenticate() {
        return false;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += String.format(", Connections: %s", getConnections().toString());
        string += String.format(", TAG: \"%s\"", TAG);
        return string;
    }
}
