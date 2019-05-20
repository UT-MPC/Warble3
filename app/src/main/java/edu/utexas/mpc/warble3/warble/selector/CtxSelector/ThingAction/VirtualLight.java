package edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction;

import android.util.Log;

import edu.utexas.mpc.warble3.warble.thing.command.Command;
import edu.utexas.mpc.warble3.warble.thing.command.Response;
import edu.utexas.mpc.warble3.warble.thing.component.Light;
import edu.utexas.mpc.warble3.warble.thing.component.LightState;
import edu.utexas.mpc.warble3.warble.thing.component.ThingState;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;

public class VirtualLight extends Light {
    private static final String TAG = "PhilipsHueLight";

    public VirtualLight() {
        super();
//        Log.i("riotTest","Reach Here!!!");
        LightState newS = new LightState();
        newS.setActive(ThingState.ACTIVE_STATE.OFF);
        this.setState(newS);
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
        super.setState(thingState);
    }

    @Override
    public Response callCommand(Command command) {
        return null;
    }

    @Override
    public void sendCommand(Command command) {}

    @Override
    public Response receiveResponse() {
        return null;
    }
}
