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
import edu.utexas.mpc.warble.thing.component.Light;
import edu.utexas.mpc.warble.thing.component.ThingState;
import edu.utexas.mpc.warble.thing.connection.AccessorConnection;
import edu.utexas.mpc.warble.thing.connection.Connection;
import edu.utexas.mpc.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble.thing.feature.Accessor;

import java.util.logging.Logger;

public final class PhilipsHueLight extends Light {
    private static final String TAG = PhilipsHueLight.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public PhilipsHueLight() {
        super();
        // set discovery
        // TODO: seems like not possible, because AccessorDiscovery needs bridge.
    }

    @Override
    public boolean authenticate() {
        return true;
    }

    @Override
    public boolean authenticate(ThingAccessCredential thingAccessCredential) {
        return true;
    }

    @Override
    public void setCredentialRequired() {
        setCredentialRequired(false);
    }

    @Override
    public void setThingAccessCredentialClasses() {
        setThingAccessCredentialClasses(null);
    }

    @Override
    public void setState(ThingState thingState) {
        if (getConnections() != null) {
            for (Connection connection : getConnections()) {
                if (connection instanceof AccessorConnection) {
                    Accessor accessor = (Accessor) ((AccessorConnection) connection).getAccessor();

                    accessor.updateThingState(this, thingState);

                    break;
                }
            }
        }
    }

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
            case SET_THING_STATE: {
                response.setCommandName(command.getName());

                if (command.getRegister1() == null) {
                    response.setStatus(false);
                    response.setDescription("no thing state");

                    return response;
                }

                setState((ThingState) command.getRegister1());

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
}
