package edu.utexas.mpc.warble3.model.thing.discovery;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.structure.Thing;

public interface DiscoveryAsyncTaskComplete {
    void onDiscoveryTaskComplete(List<Thing> things);
}
