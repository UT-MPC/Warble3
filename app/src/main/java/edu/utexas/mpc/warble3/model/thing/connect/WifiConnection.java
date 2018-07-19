package edu.utexas.mpc.warble3.model.thing.connect;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class WifiConnection extends Connection {
    private static final String TAG = "WifiConnection";

    public WifiConnection(Thing source, Thing destination) {
        super(source, destination);
    }
}
