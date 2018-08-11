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

import java.util.Collections;
import java.util.List;

import edu.utexas.mpc.warble3.model.service.PhilipsHueBridgeHttpService;
import edu.utexas.mpc.warble3.model.thing.component.Bridge;
import edu.utexas.mpc.warble3.model.thing.component.THING_AUTHENTICATION_STATE;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.thing.connect.HttpConnection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.thing.credential.UsernamePasswordCredential;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;
import edu.utexas.mpc.warble3.util.Logging;

public final class PhilipsHueBridge extends Bridge {
    private static final String TAG = "PhilipsHueBridge";

    public PhilipsHueBridge() {
        super();
        // Set isCredentialRequired as true
        setCredentialRequired(true);

        // Set Discoveries
        setDiscoveries(Collections.<Discovery>singletonList(new PhilipsHueUPnPDiscovery()));

        // Set ThingAccessCredentialClasses
        setThingAccessCredentialClasses(Collections.<Class>singletonList(UsernamePasswordCredential.class));
    }

    @Override
    public List<Thing> getThings() {
        return null;
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

        for (Connection connection : getConnections()) {
            if (!(connection instanceof HttpConnection)) {
                continue;
            }
            PhilipsHueBridgeHttpService service = new PhilipsHueBridgeHttpService(((HttpConnection) connection).getUrl());

            for (ThingAccessCredential thingAccessCredential : getThingAccessCredentials()) {
                if (!(thingAccessCredential instanceof UsernamePasswordCredential))
                    continue;

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

                String userInfo = service.getConfig(token);
                if ((userInfo != null) && (!userInfo.equals(""))) {
                    setAuthenticationState(THING_AUTHENTICATION_STATE.AUTHENTICATED);
                    return true;
                }
            }
        }

        setAuthenticationState(THING_AUTHENTICATION_STATE.UNAUTHENTICATED);
        return false;
    }

    @Override
    public boolean authenticate(ThingAccessCredential thingAccessCredential) {
        return false;
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
