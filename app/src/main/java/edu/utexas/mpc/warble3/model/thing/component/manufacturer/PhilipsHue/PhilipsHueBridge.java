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

package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.utexas.mpc.warble3.model.service.PhilipsHueBridgeHttpService;
import edu.utexas.mpc.warble3.model.thing.component.Bridge;
import edu.utexas.mpc.warble3.model.thing.component.THING_AUTHENTICATION_STATE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;
import edu.utexas.mpc.warble3.model.thing.connection.Connection;
import edu.utexas.mpc.warble3.model.thing.connection.HttpConnection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.thing.credential.UsernamePasswordCredential;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;
import edu.utexas.mpc.warble3.util.Logging;

public final class PhilipsHueBridge extends Bridge {
    private static final String TAG = "PhilipsHueBridge";

    public PhilipsHueBridge() {
        super();

        setDiscoveries(Collections.<Discovery>singletonList(new PhilipsHueUPnPDiscovery()));
    }

    @Override
    public List<Thing> getThings() {
        List<Thing> things = new ArrayList<>();

        for (Connection connection : getConnections()) {
            if (connection instanceof HttpConnection) {
                PhilipsHueBridgeHttpService service = new PhilipsHueBridgeHttpService(((HttpConnection) connection).getUrl(), this);

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

        return things;
    }

    @Override
    public List<ThingState> getThingsState() {
        return null;
    }

    @Override
    public boolean updateThingState(Thing thing, ThingState thingState) {
        return false;
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
            authenticate(thingAccessCredential);
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
                PhilipsHueBridgeHttpService service = new PhilipsHueBridgeHttpService(((HttpConnection) connection).getUrl(), this);

                if (thingAccessCredential instanceof UsernamePasswordCredential) {
                    UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) thingAccessCredential;

                    String token = usernamePasswordCredential.getToken();
                    if (token == null) {
                        token = service.createUser(usernamePasswordCredential.getUsername());

                        if (token == null) {
                            if (Logging.WARN) Log.w(TAG, "Create User failed");
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
