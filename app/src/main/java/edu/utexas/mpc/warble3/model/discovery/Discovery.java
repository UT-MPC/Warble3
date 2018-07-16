package edu.utexas.mpc.warble3.model.discovery;

import java.util.List;

import edu.utexas.mpc.warble3.model.thingstructure.Thing;

public abstract class Discovery {
    public abstract List<? extends Thing> onDiscover();
    public abstract List<? extends Thing> onDiscoverDescendants();
}
