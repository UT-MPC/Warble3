package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Agent;


import java.util.*;
import java.util.List;

import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.AbstractContextArr;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.DynamicContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.Action;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.ThingAction;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class OneAgent extends AbstractAgent{
    private class AgentActionTuple implements Comparable<AgentActionTuple> {
        public ThingAction action;
        public AbstractAgent agent;

        public AgentActionTuple (AbstractAgent newAgent, ThingAction newAction) {
            action = newAction;
            agent = newAgent;
        }

        @Override
        public int compareTo(AgentActionTuple bTuple) {
            return action.compareTo(bTuple.action);
        }
    }
    ArrayList<OneAgent> myAgents;
    ArrayList<DeviceAgent> myDevices;

    AgentActionTuple lastAgentAction = null;

    PriorityQueue<AgentActionTuple> lastCandidate;


    private int maxDeviceNum = 100;
    private final int longRetPraise = 10;
    private final int longRetPunish = -5;
    protected DeviceAgent paintAgent = null;
    private ThingAction undoAction;
    AbstractContextArr lastCtx;


    public OneAgent(List<Thing> devices) {
        DynamicContext.Alpha defaultAlpha = DynamicContext.Alpha.defaultAlpha();
        int num = devices.size();
        myAgents = new ArrayList<>();
        myDevices = new ArrayList<>();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new OneAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), new ArrayList<OneAgent>(), defaultAlpha));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                myDevices.add(new DeviceAgent(t));
                myDevices.get(myDevices.size() - 1).setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
            }
        }else{
            for (Thing t: devices){
                myDevices.add(new DeviceAgent(t));
                myDevices.get(myDevices.size() - 1).setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
            }
        }
    }
    public OneAgent(List<Thing> devices, DynamicContext.Alpha defaultAlpha) {
        int num = devices.size();
        myAgents = new ArrayList<>();
        myDevices = new ArrayList<>();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new OneAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), new ArrayList<OneAgent>(), defaultAlpha));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                myDevices.add(new DeviceAgent(t));
                myDevices.get(myDevices.size() - 1).setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
            }
        }else{
            for (Thing t: devices){
                myDevices.add(new DeviceAgent(t));
                myDevices.get(myDevices.size() - 1).setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
            }
        }
    }

    public OneAgent(List<Thing> devices, ArrayList<OneAgent> agents) {
        myAgents = agents;
        myDevices = new ArrayList<>();
        int num = devices.size();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new OneAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), new ArrayList<OneAgent>()));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                myDevices.add(new DeviceAgent(t));
            }
        }else {
            for (Thing t : devices) {
                myDevices.add(new DeviceAgent(t));
            }
        }
    }
    public OneAgent(List<Thing> devices, ArrayList<OneAgent> agents, DynamicContext.Alpha defaultAlpha) {
        myAgents = agents;
        myDevices = new ArrayList<>();
        int num = devices.size();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new OneAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), null, defaultAlpha));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                myDevices.add(new DeviceAgent(t));
                myDevices.get(myDevices.size() - 1).setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
            }
        }else {
            for (Thing t : devices) {
                myDevices.add(new DeviceAgent(t));
                myDevices.get(myDevices.size() - 1).setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));

            }
        }
    }
    public ThingAction getConfiguration(AbstractContextArr currContext, Class thingType, Action actionFilter) {
        //System.out.println(((SimpleSpaceTimeContext)currContext).x + " " + ((SimpleSpaceTimeContext)currContext).y);
//        lastAgentAction = null;
        if ((lastCtx == null) || (currContext.distanceTo(lastCtx) > 5)) {
            lastCandidate = new PriorityQueue<>(myDevices.size() + myAgents.size(), Collections.reverseOrder());
            for (DeviceAgent agent: myDevices){
                ThingAction newAction = agent.getConfiguration(currContext, thingType, actionFilter);
                if (newAction != null){
                    lastCandidate.add(new AgentActionTuple(agent, newAction));
                }
            }
            for (OneAgent agent: myAgents){
                ThingAction newAction = agent.getConfiguration(currContext, thingType, actionFilter);
                if (newAction != null){
                    lastCandidate.add(new AgentActionTuple(agent, newAction));
                }
            }
        }
        lastCtx = currContext;
        AgentActionTuple newAction = lastCandidate.poll();
        lastAgentAction = newAction;
        if (lastAgentAction == null){
            return null;
        }
//        System.out.println("chosen agent action device " + lastAgentAction.action.getActions().get(0).getDevice() + " chosen action = " + lastAgentAction.action.getActions().get(0).checkAction());
        lastAgentAction.action.doAction();
        return lastAgentAction.action;
    }
//    public void resetAll(){
//        for (OneAgent agent: myAgents){
//            agent.resetAll();
//        }
//
//        for (DeviceAgent device: myDevices){
//            device.getDevice().reset();
//        }
//    }

    @Override
    public void getPunished() {
        if (lastAgentAction != null){
            lastAgentAction.agent.getPunished();
//            lastAgentAction = null;
        }
    }
    @Override
    public void getPraised() {
        if (lastAgentAction != null){
            lastAgentAction.agent.getPraised();
            lastCtx = null;
        }
    }

    @Override
    public void getSmallPunished(){
        if (lastAgentAction != null){
            lastAgentAction.agent.getSmallPunished();
            lastCtx = null;
        }
    }


}
