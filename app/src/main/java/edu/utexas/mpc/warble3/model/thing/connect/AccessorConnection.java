package edu.utexas.mpc.warble3.model.thing.connect;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class AccessorConnection extends Connection {
    private static final String TAG = "AccessorConnection";

    private Thing accessor;

    public AccessorConnection(Thing source, Thing destination) {
        super(source, destination);
    }
}