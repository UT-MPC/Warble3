package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "ConnectionDb")
public class ConnectionDb {
    @PrimaryKey (autoGenerate = true)
    private long dbid;

    private long sourceId;
    private long destinationId;

    private String connectionClass;

    private String connectionInfo;

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public String getConnectionClass() {
        return connectionClass;
    }

    public void setConnectionClass(String connectionClass) {
        this.connectionClass = connectionClass;
    }

    public String getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(String connectionInfo) {
        this.connectionInfo = connectionInfo;
    }
}
