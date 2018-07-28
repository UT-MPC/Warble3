package edu.utexas.mpc.warble3.model.thing.component;

import java.io.Serializable;
import java.util.List;

import edu.utexas.mpc.warble3.database.interfaces.Storeable;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;

public abstract class Thing implements Serializable, Storeable {
    private static final String TAG = "Thing";

    private String name;
    private String friendlyName;

    private String uuid;

    private String accessName;
    private String accessUsername;
    private String accessPasscode;

    private String manufacturerSerialNumber;
    private String manufacturerModelName;
    private String manufacturerModelNumber;
    private String manufacturerName;

    private List<ThingType> thingTypes;
    private THING_CONCRETE_TYPE thingConcreteType;

    private List<Connection> connections;
    private List<Discovery> discoveries;

    private boolean isCredentialRequired;
    private List<ThingAccessCredential> thingAccessCredentials;

    private long dbid;

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

    public List<ThingType> getThingTypes() {
        return thingTypes;
    }

    public void setThingTypes(List<ThingType> thingTypes) {
        this.thingTypes = thingTypes;
    }

    public THING_CONCRETE_TYPE getThingConcreteType() {
        return thingConcreteType;
    }

    public void setThingConcreteType(THING_CONCRETE_TYPE thingConcreteType) {
        this.thingConcreteType = thingConcreteType;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Discovery> getDiscoveries() {
        return discoveries;
    }

    protected void setDiscoveries(List<Discovery> discoveries) {
        this.discoveries = discoveries;
    }

    public boolean getCredentialRequired() {
        return isCredentialRequired;
    }

    public void setCredentialRequired(boolean credentialRequired) {
        isCredentialRequired = credentialRequired;
    }

    public List<ThingAccessCredential> getThingAccessCredentials() {
        return thingAccessCredentials;
    }

    public void setThingAccessCredentials(List<ThingAccessCredential> thingAccessCredentials) {
        this.thingAccessCredentials = thingAccessCredentials;
    }

    public long getDbid() {
        return dbid;
    }

    private void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public abstract Boolean authenticate();

    @Override
    public void onPostStore(long dbid) {
        setDbid(dbid);
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s (friendlyName) - ", getFriendlyName());
        string += String.format("UUID: \"%s\"", getUuid());
        return string;
    }
}
