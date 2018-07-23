package edu.utexas.mpc.warble3.frontend.async_tasks;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public interface DiscoveryAsyncTaskComplete {
    void onDiscoveryTaskComplete(List<Thing> things);
}
