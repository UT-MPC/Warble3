package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity(tableName = "InteractionHistoryDb")
public class InteractionHistoryDb {
    @PrimaryKey (autoGenerate = true)
    private long dbid;

    private long timestamp = System.currentTimeMillis();
    private String location;

    private String command;

    private String username;

    private String thingUuid;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThingUuid() {
        return thingUuid;
    }

    public void setThingUuid(String thingUuid) {
        this.thingUuid = thingUuid;
    }

    public String getThingLocation() {
        return thingLocation;
    }

    public void setThingLocation(String thingLocation) {
        this.thingLocation = thingLocation;
    }

    private String thingLocation;

//    @TypeConverters(LocationConverter.class)
//    private Location location;
//    @TypeConverters(LocalDateTimeConverter.class)
//    private LocalDateTime timestamp;
//
//    @TypeConverters(CommandConverter.class)
//    private Command command;
//
//    private String username;
//
//    private String thingUuid;
//    @TypeConverters(LocationConverter.class)
//    private Location thingLocation;

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s:%s %s@%s %s %s %s@%s", dbid, "InteractionHistoryDb", timestamp, location, username, command, thingUuid, thingLocation);

        return string;
    }
}
