package edu.utexas.mpc.warble3.model;

import java.util.List;

import edu.utexas.mpc.warble3.model.connect.Connection;
import edu.utexas.mpc.warble3.model.discovery.Discovery;

public abstract class Thing {
    private static final String TAG = "Thing";

    private String name;
    private String friendlyName;

    private String uuid;

    private String accessName;
    private String accessPasscode;

    private String manufacturerSerialNumber;
    private String manufacturerModelName;
    private String manufacturerModelNumber;
    private String manufacturerName;

    private THING_MAIN_TYPE mainType;
    private THING_FUNCTION_TYPE functionType;
    private THING_CONCRETE_TYPE concreteType;

    private List<Connection> connections;
    private List<Discovery> discoveries;

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

    public THING_MAIN_TYPE getMainType() {
        return mainType;
    }

    public void setMainType(THING_MAIN_TYPE mainType) {
        this.mainType = mainType;
    }

    public THING_FUNCTION_TYPE getFunctionType() {
        return functionType;
    }

    public void setFunctionType(THING_FUNCTION_TYPE functionType) {
        this.functionType = functionType;
    }

    public THING_CONCRETE_TYPE getConcreteType() {
        return concreteType;
    }

    public void setConcreteType(THING_CONCRETE_TYPE concreteType) {
        this.concreteType = concreteType;
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

    public void setDiscoveries(List<Discovery> discoveries) {
        this.discoveries = discoveries;
    }
}
