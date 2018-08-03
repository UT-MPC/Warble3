package edu.utexas.mpc.warble3.model.thing.discovery;

import java.io.Serializable;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public abstract class Discovery implements Serializable {
    private static String TAG = "Discovery";

    public abstract List<? extends Thing> onDiscover();
    public abstract List<? extends Thing> onDiscoverDescendants();

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s", TAG);
        return string;
    }
}
