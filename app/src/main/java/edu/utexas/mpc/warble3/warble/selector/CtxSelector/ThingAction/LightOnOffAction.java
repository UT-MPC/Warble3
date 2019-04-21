package edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.warble.thing.component.Light;
import edu.utexas.mpc.warble3.warble.thing.component.LightState;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.component.ThingState;

public class LightOnOffAction implements Action {
    private Light myLight;
    private boolean turnOn;
    public LightOnOffAction(Light light, boolean isTurnOn){
        myLight = light;
        turnOn = isTurnOn;
    }

    @Override
    public int checkAction(){
        return turnOn?0:1;
    }

    private void turnOn(){
        LightState lightState = new LightState();
        lightState.setActive(ThingState.ACTIVE_STATE.ON);
        myLight.setState(lightState);
    }

    private void turnOff(){
        LightState lightState = new LightState();
        lightState.setActive(ThingState.ACTIVE_STATE.OFF);
        myLight.setState(lightState);
    }

    @Override
    public void undoAction() {
        if (turnOn){
            turnOff();
        }else{
            turnOn();
        }
    }

    @Override
    public void doAction() {
        if (turnOn){
            turnOn();
        }else{
            turnOff();
        }
    }
    @Override
    public Thing getDevice(){
        return myLight;
    }


    @Override
    public boolean isSame(Action bAction){
        if (!(bAction instanceof LightOnOffAction)){
            return false;
        }
        if (!(myLight.equals(bAction.getDevice()))) {
            return false;
        }
        if (checkAction() != bAction.checkAction()){
            return false;
        }
        return true;
    }

    @Override
    public boolean isStateCompatible(ThingState thingState){
        if (!(thingState instanceof LightState)){
            return false;
        }
        LightState nowState = (LightState) thingState;
        if ((turnOn)&&( nowState.getActive() == ThingState.ACTIVE_STATE.ON)) {
            return false;
        }
        if ((!turnOn)&&(nowState.getActive() == ThingState.ACTIVE_STATE.OFF)){
            return false;
        }
        return true;
    }

    @Override
    public boolean actionFilter(Action filter){
        if (filter == null) return true;
        if (!(filter instanceof LightOnOffAction)){
            return false;
        }
        if (checkAction() != filter.checkAction()){
            return false;
        }
        return true;
    }
}
