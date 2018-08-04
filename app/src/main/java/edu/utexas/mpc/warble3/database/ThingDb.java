package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import edu.utexas.mpc.warble3.database.type_converter.THING_CONCRETE_TYPE_converter;
import edu.utexas.mpc.warble3.database.type_converter.THING_CONNECTION_STATE_converter;
import edu.utexas.mpc.warble3.model.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.model.thing.component.THING_CONNECTION_STATE;

@Entity(tableName = "ThingDb")
public class ThingDb {
    @Ignore
    public static final String TAG = "ThingDb";

    @PrimaryKey (autoGenerate = true)
    private long dbid;

    private String name;
    private String friendlyName;

    private String uuid;

    private String thingClass;

    private String accessName;
    private String accessUsername;
    private String accessPasscode;

    private String manufacturerSerialNumber;
    private String manufacturerModelName;
    private String manufacturerModelNumber;
    private String manufacturerName;

    @TypeConverters(THING_CONCRETE_TYPE_converter.class)
    private THING_CONCRETE_TYPE thingConcreteType;

    private boolean isCredentialRequired;

    @TypeConverters(THING_CONNECTION_STATE_converter.class)
    private THING_CONNECTION_STATE connectionState;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getThingClass() {
        return thingClass;
    }

    public void setThingClass(String thingClass) {
        this.thingClass = thingClass;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getAccessUsername() {
        return accessUsername;
    }

    public void setAccessUsername(String accessUsername) {
        this.accessUsername = accessUsername;
    }

    public String getAccessPasscode() {
        return accessPasscode;
    }

    public void setAccessPasscode(String accessPasscode) {
        this.accessPasscode = accessPasscode;
    }

    public String getManufacturerSerialNumber() {
        return manufacturerSerialNumber;
    }

    public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
        this.manufacturerSerialNumber = manufacturerSerialNumber;
    }

    public String getManufacturerModelName() {
        return manufacturerModelName;
    }

    public void setManufacturerModelName(String manufacturerModelName) {
        this.manufacturerModelName = manufacturerModelName;
    }

    public String getManufacturerModelNumber() {
        return manufacturerModelNumber;
    }

    public void setManufacturerModelNumber(String manufacturerModelNumber) {
        this.manufacturerModelNumber = manufacturerModelNumber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public THING_CONCRETE_TYPE getThingConcreteType() {
        return thingConcreteType;
    }

    public void setThingConcreteType(THING_CONCRETE_TYPE thingConcreteType) {
        this.thingConcreteType = thingConcreteType;
    }

    public boolean getCredentialRequired() {
        return isCredentialRequired;
    }

    public void setCredentialRequired(boolean credentialRequired) {
        isCredentialRequired = credentialRequired;
    }

    public THING_CONNECTION_STATE getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(THING_CONNECTION_STATE connectionState) {
        this.connectionState = connectionState;
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s:%s", dbid, friendlyName);
        return string;
    }
}
