package edu.utexas.mpc.warble3.model.thing.discovery;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public abstract class Discovery {
    public abstract List<? extends Thing> onDiscover();
    public abstract List<? extends Thing> onDiscoverDescendants();
}
