package edu.utexas.mpc.warble3.model.thingstructure.manufacturer.Wink;

import java.util.List;

import edu.utexas.mpc.warble3.model.thingstructure.Thing;
import edu.utexas.mpc.warble3.model.discovery.ServerDiscovery;

public class WinkDiscovery extends ServerDiscovery {
    @Override
    public List<? extends Thing> onDiscover() {
        return null;
    }

    @Override
    public List<? extends Thing> onDiscoverDescendants() {
        return null;
    }
}
