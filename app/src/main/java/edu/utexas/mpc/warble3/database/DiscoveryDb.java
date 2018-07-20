package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "DiscoveryDb")
public class DiscoveryDb {
    @PrimaryKey (autoGenerate = true)
    private long dbid;
}
