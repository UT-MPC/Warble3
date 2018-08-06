package edu.utexas.mpc.warble3.model.thing.connect;

import edu.utexas.mpc.warble3.database.interfaces.ConnectionStoreable;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class BluetoothConnection extends Connection implements ConnectionStoreable {
    private static final String TAG = "BluetoothConnection";

    public BluetoothConnection(Thing source, Thing destination) {
        super(source, destination);
        setDirectionalType(DIRECTIONAL_TYPE.UNIDIRECTIONAL);
    }

    @Override
    public String toConnectionInfo() {
        return null;
    }

    @Override
    public void fromConnectionInfo(String connectionInfo) {

    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof BluetoothConnection)) {
            return false;
        }

        BluetoothConnection c = (BluetoothConnection) object;

        return (this.getSource().equals(c.getSource()));
    }
}
