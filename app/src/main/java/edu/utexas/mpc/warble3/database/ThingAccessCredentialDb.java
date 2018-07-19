package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "ThingAccessCredentialDb")
public class ThingAccessCredentialDb {
    @Ignore
    public static final String TAG = "ThingAccessCredentialDb";

    @PrimaryKey (autoGenerate = true)
    private long dbid;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }
}
