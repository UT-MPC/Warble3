package edu.utexas.mpc.warble3.model.thing.connect;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public abstract class Connection {
    private static final String TAG = "Connection";

    private Thing source;
    private Thing destination;

    public Connection(Thing source, Thing destination) {
        this.source = source;
        this.destination = destination;
    }

    public Thing getSource() {
        return source;
    }

    public void setSource(Thing source) {
        this.source = source;
    }

    public Thing getDestination() {
        return destination;
    }

    public void setDestination(Thing destination) {
        this.destination = destination;
    }
}
