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
 */

package thing.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import context.Location;
import thing.command.Command;
import thing.command.Commandable;
import thing.command.GenericResponse;
import thing.command.Response;
import thing.connection.Connection;
import thing.credential.ThingAccessCredential;
import thing.discovery.Discovery;

public abstract class Thing implements Serializable, Storeable, Commandable {
    private static final String TAG = Thing.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    protected String name;
    protected String friendlyName;

    protected String uuid;

    protected String accessName;
    protected String accessUsername;
    protected String accessPasscode;

    protected String manufacturerSerialNumber;
    protected String manufacturerModelName;
    protected String manufacturerModelNumber;
    protected String manufacturerName;

    protected List<ThingType> thingTypes;
    protected THING_CONCRETE_TYPE thingConcreteType;

    protected List<Connection> connections;
    protected Connection lastActiveConnection;
    protected List<Discovery> discoveries;

    protected boolean isCredentialRequired = true;
    protected List<ThingAccessCredential> thingAccessCredentials;
    protected List<Class> thingAccessCredentialClasses;

    protected THING_CONNECTION_STATE connectionState = THING_CONNECTION_STATE.INITIAL;
    protected THING_AUTHENTICATION_STATE authenticationState = THING_AUTHENTICATION_STATE.UNAUTHENTICATED;
    protected THING_BINDING_STATE bindingState = THING_BINDING_STATE.UNBOUND;

    protected Location location;

    protected long dbid;

    public Thing(String uuid) {
        this.uuid = uuid;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public abstract void setCredentialRequired();

    public abstract void setThingAccessCredentialClasses();

    public abstract void setThingTypes();

    public abstract void setThingConcreteTypes();

    public abstract ThingState getThingState();

    public Response preCallCommand(Command command) {
        if (command == null) {
            Response response = new GenericResponse();

            response.setStatus(false);
            response.setCommandName(null);
            response.setDescription("no command");

            return response;
        } else if (command.getName() == null) {
            Response response = new GenericResponse();

            response.setStatus(false);
            response.setCommandName(null);
            response.setDescription("no command name");

            return response;
        } else {
            return null;
        }
    }

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
