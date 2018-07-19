package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "ConnectionDb")
public class ConnectionDb {
    @PrimaryKey (autoGenerate = true)
    private long dbid;
}
