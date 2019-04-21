package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Agent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.AbstractContextArr;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.DynamicContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.Action;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.LightOnOffAction;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.ThingAction;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class DeviceAgent extends AbstractAgent{
    private Thing device;
    ArrayList<StateWithEntropy> knownStates;
    boolean lastStateNew = false;
    StateWithEntropy chosenState;
    ThingAction chosenAction;
    AbstractContextArr lastContext;
    private final int longRetPraise = 10;
    private final int longRetPunish = -9;
    private final int longRetSmallPunish = -1;

    public void setDeviceUserAlpha(DynamicContext.Alpha deviceUserAlpha) {
        this.deviceUserAlpha = deviceUserAlpha;
    }

    public DynamicContext.Alpha getDeviceUserAlpha() {
        return deviceUserAlpha;
    }

    private DynamicContext.Alpha deviceUserAlpha;


    public DeviceAgent(Thing newDev) {
        device = newDev;
        knownStates = new ArrayList<StateWithEntropy>();
        deviceUserAlpha = DynamicContext.Alpha.defaultAlpha();
    }

    public Thing getDevice() {
        return device;
    }

    public ThingAction getConfiguration(AbstractContextArr currContext, Class thingType){
        return getConfiguration(currContext, thingType, null);
    }

    public ThingAction getConfiguration(AbstractContextArr currContext, Class thingType, Action actionFilter) {
        if (!thingType.isInstance(device)){
            return null;
        }
        ((DynamicContext)currContext).setAlpha(deviceUserAlpha);
        lastContext = currContext;
        lastStateNew = false;

        ArrayList<StateWithEntropy> helper = new ArrayList<>(3);
        chosenState = null;
        for (StateWithEntropy state: knownStates){
            if (state.isWithin((DynamicContext) currContext)){
                //chosenState = state.updateState(currState);
                chosenState = state;
                break;
            }
            if (helper.size() == 0) {
                helper.add(state);
            }else{
                int i = 0;
                for (i = 0; i < helper.size(); i++){
                    if (!state.betterHelpingThan((DynamicContext) currContext, helper.get(i))){
                        break;
                    }
                }
                if (i > 0){
                    helper.add(i, state);
                    if (helper.size() > 3){
                        helper.remove(0);
                    }
                }
            }
        }
        if (chosenState == null){
            chosenState = new StateWithEntropy((DynamicContext) currContext);
            chosenState.setMyAlpha(deviceUserAlpha);
            if (chosenState.getHelped(helper)){

            } else {
                List<ThingAction> allAction = ThingAction.getLightAction(device);
                for (ThingAction action: allAction){chosenState.addAction(action);}
//                chosenAction = chosenState.actions.get(0);
            }
            //knownStates.add(chosenState);
            lastStateNew = true;
        }
//        if ((device.getScope().getX() ==1140) && ((device.getScope().getY() == 200))){
//            System.out.println(helper);
//            System.out.println(chosenState);
//        }
        chosenAction = chosenState.getAction(device.getState(), actionFilter);
//        System.out.println(chosenAction + "  "+ chosenAction.longRet);
        return chosenAction;
    }
    @Override
    public void getPunished() {
        if (chosenState != null){
            chosenAction.undoAction();
            if (lastStateNew){
                knownStates.add(chosenState);
            }
            chosenState.interactState((DynamicContext)lastContext, chosenAction, longRetPunish, knownStates);
        }
    }
    @Override
    public void getPraised() {
        if (chosenState != null){
            if (lastStateNew){
                knownStates.add(chosenState);
            }
            chosenState.interactState((DynamicContext)lastContext, chosenAction, longRetPraise, knownStates);
        }
    }

    @Override
    public void getSmallPunished(){
        if (chosenState != null){
            if (lastStateNew){
                knownStates.add(chosenState);
            }
            chosenState.interactState((DynamicContext)lastContext, chosenAction, longRetSmallPunish, knownStates);
        }
    }

}
