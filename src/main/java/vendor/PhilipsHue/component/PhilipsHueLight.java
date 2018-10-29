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

package vendor.PhilipsHue.component;

import service.ServiceAdapterManager;
import thing.command.Command;
import thing.command.GenericResponse;
import thing.command.Response;
import thing.component.Light;
import thing.component.ThingState;
import thing.connection.AccessorConnection;
import thing.connection.Connection;
import thing.credential.ThingAccessCredential;
import thing.feature.Hub;

import java.util.logging.Logger;

public final class PhilipsHueLight extends Light {
    private static final String TAG = PhilipsHueLight.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public PhilipsHueLight(String uuid) {
        super(uuid);
        // set discovery
        // TODO: seems like not possible, because AccessorDiscovery needs bridge.
    }

    public boolean authenticate() {
        return true;
    }

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

    public void setState(ServiceAdapterManager serviceAdapterManager, ThingState thingState) {
        if (getConnections() != null) {
            for (Connection connection : getConnections()) {
                if (connection instanceof AccessorConnection) {
                    Hub hub = (Hub) ((AccessorConnection) connection).getAccessor();

                    hub.updateThingState(serviceAdapterManager, this, thingState);
                    break;
                }
            }
        }
    }

    @Override
    public Response callCommand(ServiceAdapterManager serviceAdapterManager, Command command) {
        Response response = super.preCallCommand(command);

        if (response != null) {
            return response;
        } else {
            response = new GenericResponse();
        }

        switch (command.getName()) {
            case AUTHENTICATE: {
                response.setCommandName(command.getName());

                if (command.getRegister1() == null) {
                    response.setStatus(false);
                    response.setDescription("NO_CREDENTIAL");

                    return response;
                }

                boolean result = authenticate((ThingAccessCredential) command.getRegister1());
                response.setStatus(result);
                if (result) {
                    response.setDescription("");
                }
                else {
                    response.setDescription("UNKNOWN_REASON");
                }
                break;
            }
            case SET_THING_STATE: {
                response.setCommandName(command.getName());

                if (command.getRegister1() == null) {
                    response.setStatus(false);
                    response.setDescription("NO_THING_STATE");

                    return response;
                }

                setState(serviceAdapterManager, (ThingState) command.getRegister1());

                break;
            }
            default: {
                response.setCommandName(command.getName());
                response.setStatus(false);
                response.setDescription("UNSUPPORTED_COMMAND");
            }
        }

        return response;
    }

    @Override
    public void sendCommand(ServiceAdapterManager serviceAdapterManager, Command command) {
    }

    @Override
    public Response receiveResponse() {
        return null;
    }
}
