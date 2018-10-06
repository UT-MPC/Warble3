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

package edu.utexas.mpc.warble.vendor.PhilipsHue.component;

import edu.utexas.mpc.warble.thing.command.Command;
import edu.utexas.mpc.warble.thing.command.GenericResponse;
import edu.utexas.mpc.warble.thing.command.Response;
import edu.utexas.mpc.warble.thing.component.Bridge;
import edu.utexas.mpc.warble.thing.component.THING_AUTHENTICATION_STATE;
import edu.utexas.mpc.warble.thing.component.Thing;
import edu.utexas.mpc.warble.thing.component.ThingState;
import edu.utexas.mpc.warble.thing.connection.AccessorConnection;
import edu.utexas.mpc.warble.thing.connection.Connection;
import edu.utexas.mpc.warble.thing.connection.HttpConnection;
import edu.utexas.mpc.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble.thing.credential.UsernamePasswordCredential;
import edu.utexas.mpc.warble.thing.discovery.Discovery;
import edu.utexas.mpc.warble.vendor.PhilipsHue.discovery.PhilipsHueUPnPDiscovery;
import edu.utexas.mpc.warble.vendor.PhilipsHue.service.PhilipsHueBridgeHttpService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class PhilipsHueBridge extends Bridge {
    private static final String TAG = PhilipsHueBridge.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public PhilipsHueBridge() {
        super();

        setDiscoveries(Collections.<Discovery>singletonList(new PhilipsHueUPnPDiscovery()));
    }

    @Override
    public List<Thing> getThings() {
        List<Thing> things = new ArrayList<>();

        for (Connection connection : getConnections()) {
            if (connection instanceof HttpConnection) {
                PhilipsHueBridgeHttpService service = new PhilipsHueBridgeHttpService(((HttpConnection) connection).getUrl());

                if (getThingAccessCredentials() != null) {
                    for (ThingAccessCredential thingAccessCredential : getThingAccessCredentials()) {
                        if (thingAccessCredential instanceof UsernamePasswordCredential) {
                            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) thingAccessCredential;
                            String token = usernamePasswordCredential.getToken();
                            if (token != null) {
                                things.addAll(service.getThings(token));
                            }
                        }
                    }
                }
            }
        }

        for (Thing thing : things) {
            List<Connection> connections;
            if (thing.getConnections() == null) {
                 connections = new ArrayList<>();
            }
            else {
                connections = thing.getConnections();
            }
            connections.add(new AccessorConnection(thing, this));
            thing.setConnections(connections);
        }

        if (things.size() == 0) {
            return null;
        }
        else {
            return things;
        }
    }

    @Override
    public List<ThingState> getThingsState() {
        return null;
    }

    @Override
    public boolean updateThingState(Thing thing, ThingState thingState) {
        for (Connection connection : getConnections()) {
            if (connection instanceof HttpConnection) {
                PhilipsHueBridgeHttpService service = new PhilipsHueBridgeHttpService(((HttpConnection) connection).getUrl());

                if (getThingAccessCredentials() != null) {
                    for (ThingAccessCredential thingAccessCredential : getThingAccessCredentials()) {
                        if (thingAccessCredential instanceof UsernamePasswordCredential) {
                            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) thingAccessCredential;
                            String token = usernamePasswordCredential.getToken();
                            if (token != null) {
                                service.putThingState(token, thing, thingState);

                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean updateThingState(Thing thing, ThingState thingState, boolean postCheck) {
        return false;
    }

    @Override
    public boolean updateThingsState(List<Thing> things, List<ThingState> thingsState) {
        return false;
    }

    @Override
    public boolean updateThingsState(List<Thing> things, List<ThingState> thingsState, boolean postCheck) {
        return false;
    }

    @Override
    public boolean authenticate() {
        // check credentialRequired, if false, return true
        if (!getCredentialRequired()) {
            setAuthenticationState(THING_AUTHENTICATION_STATE.AUTHENTICATED);
            return true;
        }

        // check if credentials is not null
        if ((getThingAccessCredentialClasses() == null)
                || (getThingAccessCredentialClasses().size() == 0)
                || (getThingAccessCredentials() == null)
                || (getThingAccessCredentials().size() == 0)) {
            setAuthenticationState(THING_AUTHENTICATION_STATE.UNAUTHENTICATED);
            return false;
        }

        for (ThingAccessCredential thingAccessCredential : getThingAccessCredentials()) {
            if (authenticate(thingAccessCredential)) {
                break;
            }
        }

        return getAuthenticationState() == THING_AUTHENTICATION_STATE.AUTHENTICATED;
    }

    @Override
    public boolean authenticate(ThingAccessCredential thingAccessCredential) {
        if (!getCredentialRequired()) {
            setAuthenticationState(THING_AUTHENTICATION_STATE.AUTHENTICATED);
            return true;
        }

        if (!isThingAccessCredentialValid(thingAccessCredential)) {
            return false;
        }

        for (Connection connection : getConnections()) {
            if (connection instanceof HttpConnection) {
                PhilipsHueBridgeHttpService service = new PhilipsHueBridgeHttpService(((HttpConnection) connection).getUrl());

                if (thingAccessCredential instanceof UsernamePasswordCredential) {
                    UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) thingAccessCredential;

                    String token = usernamePasswordCredential.getToken();
                    if (token == null) {
                        token = service.createUser(usernamePasswordCredential.getUsername());

                        if (token == null) {
                            LOGGER.warning("Create User failed");
                            continue;
                        }
                        else {
                            usernamePasswordCredential.setToken(token);
                        }
                    }
                    else {
                        token = usernamePasswordCredential.getToken();
                    }

                    String username = service.getConfig(token);
                    if ((username != null) && (username.equals(usernamePasswordCredential.getUsername()))) {
                        setAuthenticationState(THING_AUTHENTICATION_STATE.AUTHENTICATED);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void setCredentialRequired() {
        setCredentialRequired(true);
    }

    @Override
    public void setThingAccessCredentialClasses() {
        setThingAccessCredentialClasses(Collections.<Class>singletonList(UsernamePasswordCredential.class));
    }

    @Override
    public void setState(ThingState thingState) {}

    @Override
    public Response callCommand(Command command) {
        Response response = new GenericResponse();

        if (command == null) {
            response.setStatus(false);
            response.setCommandName(null);
            response.setDescription("no command");

            return response;
        }

        if (command.getName() == null) {
            response.setStatus(false);
            response.setCommandName(null);
            response.setDescription("no command name");

            return response;
        }

        switch (command.getName()) {
            case AUTHENTICATE: {
                response.setCommandName(command.getName());

                if (command.getRegister1() == null) {
                    response.setStatus(false);
                    response.setDescription("no credential");

                    return response;
                }

                boolean result = authenticate((ThingAccessCredential) command.getRegister1());
                response.setStatus(result);
                if (result) {
                    response.setDescription("");
                }
                else {
                    response.setDescription("unknown reason");
                }
                break;
            }
            default: {
                response.setCommandName(command.getName());
                response.setStatus(false);
                response.setDescription("unknown command name");
            }
        }

        return response;
    }

    @Override
    public void sendCommand(Command command) {}

    @Override
    public Response receiveResponse() {
        return null;
    }

    public boolean isThingAccessCredentialValid(ThingAccessCredential thingAccessCredential) {
        if (!getCredentialRequired()) {
            return thingAccessCredential == null;
        }

        if (((getThingAccessCredentialClasses() == null) || (getThingAccessCredentialClasses().size() == 0))) {
            return false;
        }
        else {
            boolean result = false;
            for (Class m : getThingAccessCredentialClasses()) {
                if (thingAccessCredential.getClass().equals(m)) {
                    result = true;
                }
            }
            return result;
        }
    }

    @Override
    public String toString() {
        String string = super.toString();
        List<Connection> connections = getConnections();
        if (connections == null) {
            string += ", Connections: []";
        }
        else {
            string += String.format(", Connections: %s", connections.toString());
        }
        string += String.format(", TAG: \"%s\"", TAG);
        return string;
    }
}
