package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.discovery.AccessorDiscovery;

public final class PhilipsHueBridgeAccessorDiscovery extends AccessorDiscovery {
    private final PhilipsHueBridge philipsHueBridge;

    public PhilipsHueBridgeAccessorDiscovery(PhilipsHueBridge philipsHueBridge) {
        this.philipsHueBridge = philipsHueBridge;
    }

    @Override
    public List<? extends Thing> onDiscover() {
        return null;
    }
}
