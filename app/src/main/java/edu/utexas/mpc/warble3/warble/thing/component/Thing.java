/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package edu.utexas.mpc.warble3.warble.thing.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.interfaces.Storeable;
import edu.utexas.mpc.warble3.warble.thing.command.Commandable;
import edu.utexas.mpc.warble3.warble.thing.connection.Connection;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.warble.thing.discovery.Discovery;

public abstract class Thing implements Serializable, Storeable, Commandable {
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

    private boolean isCredentialRequired = true;
    private List<ThingAccessCredential> thingAccessCredentials;
    private List<Class> thingAccessCredentialClasses;

    private THING_CONNECTION_STATE connectionState = THING_CONNECTION_STATE.INITIAL;
    private THING_AUTHENTICATION_STATE authenticationState = THING_AUTHENTICATION_STATE.UNAUTHENTICATED;
    private THING_BINDING_STATE bindingState = THING_BINDING_STATE.UNBOUND;

    private long dbid;

    public Thing() {
        setCredentialRequired();                        // Template Design Pattern
        setThingAccessCredentialClasses();              // Template Design Pattern
        setThingTypes();                                // Template Design Pattern
        setThingConcreteTypes();                        // Template Design Pattern
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

    public void addThingAccessCredentials(ThingAccessCredential newThingAccessCredential) {
        if (this.thingAccessCredentials == null) {
            this.thingAccessCredentials = new ArrayList<>();
        }
        this.thingAccessCredentials.add(newThingAccessCredential);
    }

    public List<Class> getThingAccessCredentialClasses() {
        return thingAccessCredentialClasses;
    }

    protected void setThingAccessCredentialClasses(List<Class> thingAccessCredentialClasses) {
        this.thingAccessCredentialClasses = thingAccessCredentialClasses;
    }

    public THING_CONNECTION_STATE getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(THING_CONNECTION_STATE connectionState) {
        this.connectionState = connectionState;
    }

    public THING_AUTHENTICATION_STATE getAuthenticationState() {
        return authenticationState;
    }

    public void setAuthenticationState(THING_AUTHENTICATION_STATE authenticationState) {
        this.authenticationState = authenticationState;
    }

    public THING_BINDING_STATE getBindingState() {
        return bindingState;
    }

    public void setBindingState(THING_BINDING_STATE bindingState) {
        this.bindingState = bindingState;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public abstract boolean authenticate();

    public abstract boolean authenticate(ThingAccessCredential thingAccessCredential);

    public abstract void setCredentialRequired();

    public abstract void setThingAccessCredentialClasses();

    public abstract void setThingTypes();

    public abstract void setThingConcreteTypes();

    public abstract void setState(ThingState thingState);

    @Override
    public void onPostStore(long dbid) {
        setDbid(dbid);
    }

    @Override
    public void onPostLoad(long dbid) {
        setDbid(dbid);
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s (friendlyName) - ", getFriendlyName());
        string += String.format("UUID: \"%s\"", getUuid());
        return string;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Thing)) {
            return false;
        }

        Thing t = (Thing) object;

        return (this.uuid.equals(t.uuid)) &&
                (this.name.equals(t.name)) &&
                (this.manufacturerSerialNumber.equals(t.manufacturerSerialNumber)) &&
                (this.manufacturerModelName.equals(t.manufacturerModelName)) &&
                (this.manufacturerModelNumber.equals(t.manufacturerModelNumber)) &&
                (this.manufacturerName.equals(t.manufacturerName));
    }
}
