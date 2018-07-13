package edu.utexas.mpc.warble3.model.discovery;

import java.util.List;

import edu.utexas.mpc.warble3.model.Thing;

public interface DiscoveryAsyncTaskComplete {
    void onDiscoveryTaskComplete(List<Thing> things);
}
