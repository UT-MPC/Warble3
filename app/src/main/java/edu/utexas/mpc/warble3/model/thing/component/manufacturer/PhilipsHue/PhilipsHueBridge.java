package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import android.util.Log;

import java.util.ArrayList;
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

        // Set Discoveries
        List<Discovery> discoveries = new ArrayList<>();
        discoveries.add(new PhilipsHueUPnPDiscovery());
        setDiscoveries(discoveries);

        // Set ThingAccessCredentialClasses
        List<Class> thingAccessCredentialClasses = new ArrayList<>();
        thingAccessCredentialClasses.add(UsernamePasswordCredential.class);
        setThingAccessCredentialClasses(thingAccessCredentialClasses);
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

                String token;
                if (usernamePasswordCredential.getToken() == null) {
                    token = service.createUser(usernamePasswordCredential.getUsername());

                    if (token == null) {
                        if (Logging.WARN) Log.w(TAG, "Create User failed");
                        continue;
                    }
                }
                else {
                    token = usernamePasswordCredential.getToken();
                }

                String userInfo = service.getUserInfo(token);
                Log.d(TAG, userInfo);
                if (userInfo != null) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += String.format(", Connections: %s", getConnections().toString());
        string += String.format(", TAG: \"%s\"", TAG);
        return string;
    }
}
