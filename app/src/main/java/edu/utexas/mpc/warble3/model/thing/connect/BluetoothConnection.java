package edu.utexas.mpc.warble3.model.thing.connect;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class BluetoothConnection extends Connection {
    private static final String TAG = "BluetoothConnection";

    public BluetoothConnection(Thing source, Thing destination) {
        super(source, destination);
    }
}
