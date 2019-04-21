package edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction;

import edu.utexas.mpc.warble3.warble.thing.component.Light;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.component.ThingState;

import java.util.ArrayList;
import java.util.List;

public class ThingAction implements Action, Comparable<ThingAction> {
    ArrayList<Action> myActions;
    public double shortRet;
    public double longRet;
    public ThingAction(ArrayList<Action> selects){
        shortRet = 0;
        longRet = 0;
        myActions = selects;
    }
    public ThingAction(ThingAction clone){
        myActions = clone.myActions;
        shortRet = clone.shortRet;
        longRet = clone.longRet;
    }
    public  ArrayList<Action> getActions(){
        return myActions;
    }
    public ThingAction(){
        shortRet = 0;
        longRet = 0;
        myActions = new ArrayList<Action>();
    }
    public void addAction(Action newAction){
        myActions.add(newAction);
    }
    @Override
    public void doAction() {
        for (Action obj : myActions){
            obj.doAction();
        }
    }

    @Override
    public void undoAction() {
        for (Action obj : myActions){
            obj.undoAction();
        }
    }

    @Override
    public int compareTo(ThingAction agentAction) {
        if (Math.abs(longRet - agentAction.longRet) < 0.0001f){
            return 0;
        }
        if (longRet < agentAction.longRet){
            return -1;
        }else{
            return 1;
        }
    }

    @Override
    public boolean isSame(Action bAction){
        if (!(bAction instanceof ThingAction)){
            return false;
        }
        if (myActions.size() != ((ThingAction) bAction).getActions().size()){
            return false;
        }
        for (int i = 0; i < myActions.size(); i++){
            if (!(myActions.get(i).isSame(((ThingAction) bAction).getActions().get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int checkAction(){ return -1;}

    @Override
    public Thing getDevice(){return null;}

    @Override
    public boolean isStateCompatible(ThingState thingState){
        for(Action action: myActions){
            if (!action.isStateCompatible(thingState)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean actionFilter(Action filter){
        if (filter == null) return true;
        if (filter instanceof ThingAction){
            ThingAction c = (ThingAction)filter;
            for (Action x : myActions){
                boolean foundFlag = false;
                for (Action l: c.myActions){
                    if (x.actionFilter(l)){
                        foundFlag = true;
                        break;
                    }
                }
                if (!foundFlag) return false;
            }
        }else{
            for (Action x : myActions){
                if (!x.actionFilter(filter)) return false;
            }
        }
        return true;
    }

    public static List<ThingAction> getLightAction(Thing device){
        if (!(device instanceof Light)){
            return new ArrayList<ThingAction>(0);
        }
        ThingAction actions = new ThingAction();
        ArrayList<ThingAction> ret = new ArrayList<>();
        actions.addAction(new LightOnOffAction((Light) device,false));
        ret.add(actions);

        actions = new ThingAction();
        actions.addAction(new LightOnOffAction((Light) device,true));
        ret.add(actions);
        return ret;
    }
}
