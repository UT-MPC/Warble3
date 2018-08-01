package edu.utexas.mpc.warble3.model.thing.connect;

import java.io.Serializable;

import edu.utexas.mpc.warble3.database.interfaces.ConnectionStoreable;
import edu.utexas.mpc.warble3.database.interfaces.Storeable;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

public abstract class Connection implements Serializable, Storeable, ConnectionStoreable {
    private static final String TAG = "Connection";

    private Thing source;
    private Thing destination;

    private DIRECTIONAL_TYPE directionalType;

    private long dbid;

    public Connection() {
    }

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

    public DIRECTIONAL_TYPE getDirectionalType() {
        return directionalType;
    }

    public void setDirectionalType(DIRECTIONAL_TYPE directionalType) {
        this.directionalType = directionalType;
    }

    public long getDbid() {
        return dbid;
    }

    private void setDbid(long dbid) {
        this.dbid = dbid;
    }

    @Override
    public void onPostStore(long dbid) {
        setDbid(dbid);
    }

    @Override
    public void onPostLoad(long dbid) {
        setDbid(dbid);
    }
}
