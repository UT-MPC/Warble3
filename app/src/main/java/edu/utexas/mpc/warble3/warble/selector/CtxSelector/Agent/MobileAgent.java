package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Agent;


import java.util.*;
import java.util.List;

import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.AbstractContextArr;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.DynamicContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.Action;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.ThingAction;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class MobileAgent extends AbstractAgent{
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
    ArrayList<MobileAgent> myAgents;
    ArrayList<DeviceAgent> myDevices;
    HashMap<String, AbstractAgent> deviceMap;
    AgentActionTuple lastAgentAction = null;

    PriorityQueue<AgentActionTuple> lastCandidate;


    private int maxDeviceNum = 100;
    private final int longRetPraise = 10;
    private final int longRetPunish = -5;
    protected DeviceAgent paintAgent = null;
    private ThingAction undoAction;
    private DynamicContext.Alpha defaultAlpha = DynamicContext.Alpha.defaultAlpha();
    AbstractContextArr lastCtx;


    private void addDeviceAgent(DeviceAgent newAgent){
        myDevices.add(newAgent);
        deviceMap.put(newAgent.getDevice().getUuid(), newAgent);
    }

    public MobileAgent(List<Thing> devices) {
        int num = devices.size();
        myAgents = new ArrayList<>();
        myDevices = new ArrayList<>();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new MobileAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), new ArrayList<MobileAgent>(), defaultAlpha));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }else{
            for (Thing t: devices){
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }
    }
    public MobileAgent(List<Thing> devices, DynamicContext.Alpha setAlpha) {
        defaultAlpha = setAlpha;
        int num = devices.size();
        myAgents = new ArrayList<>();
        myDevices = new ArrayList<>();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new MobileAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), new ArrayList<MobileAgent>(), defaultAlpha));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }else{
            for (Thing t: devices){
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }
    }

    public MobileAgent(List<Thing> devices, ArrayList<MobileAgent> agents) {
        myAgents = agents;
        myDevices = new ArrayList<>();
        int num = devices.size();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new MobileAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), new ArrayList<MobileAgent>()));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }else {
            for (Thing t : devices) {
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }
    }
    public MobileAgent(List<Thing> devices, ArrayList<MobileAgent> agents, DynamicContext.Alpha setAlpha) {
        defaultAlpha = setAlpha;
        myAgents = agents;
        myDevices = new ArrayList<>();
        int num = devices.size();
        if ((num > maxDeviceNum) && (maxDeviceNum > 0)){
            int numA = num / maxDeviceNum;
            for (int i = 0; i < numA; i++){
                myAgents.add(new MobileAgent(devices.subList(i * maxDeviceNum, (i + 1) * maxDeviceNum), null, defaultAlpha));
            }
            List<Thing> tmp = devices.subList(numA * maxDeviceNum, devices.size());
            for (Thing t: tmp){
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);
            }
        }else {
            for (Thing t : devices) {
                DeviceAgent newAgent = new DeviceAgent(t);
                newAgent.setDeviceUserAlpha(new DynamicContext.Alpha(defaultAlpha));
                addDeviceAgent(newAgent);

            }
        }
    }

    public ThingAction getFilteredConfiguration (List<Thing> filteredDevices, AbstractContextArr currContext, Action actionFilter){
        if ((lastCtx == null) || (currContext.distanceTo(lastCtx) > 5)) {
            lastCandidate = new PriorityQueue<>(myDevices.size() + myAgents.size(), Collections.reverseOrder());
            for (Thing device: filteredDevices){
                AbstractAgent knownAgent = knowDevice(device);
                ThingAction newAction;
                AbstractAgent newAgent;
                if (knownAgent != null){
                    if (knownAgent instanceof DeviceAgent){
                        ((DeviceAgent) knownAgent).setDevice(device);
                        newAction = ((DeviceAgent)knownAgent).getConfiguration(currContext, device.getClass(), actionFilter);
                    }else{
                        ArrayList<Thing> tmpDeviceHolder = new ArrayList<>();
                        tmpDeviceHolder.add(device);
                        newAction = ((MobileAgent)knownAgent).getFilteredConfiguration(tmpDeviceHolder, currContext, actionFilter);
                    }
                    newAgent = knownAgent;
                }else{
                    // add this device to list
                    newAgent = new DeviceAgent(device);
                    addDeviceAgent((DeviceAgent)newAgent);
                    newAction = ((DeviceAgent)knownAgent).getConfiguration(currContext, device.getClass(), actionFilter);
                }
                lastCandidate.add(new AgentActionTuple(newAgent, newAction));
            }
        }
        lastCtx = currContext;
        AgentActionTuple newAction = lastCandidate.poll();
        lastAgentAction = newAction;
        if (lastAgentAction == null){
            return null;
        }
//        System.out.println("chosen agent action device " + lastAgentAction.action.getActions().get(0).getDevice() + " chosen action = " + lastAgentAction.action.getActions().get(0).checkAction());
//        lastAgentAction.action.doAction();
        return lastAgentAction.action;
    }
    public AbstractAgent knowDevice(Thing device){
        if (deviceMap.containsKey(device.getUuid())){
            return deviceMap.get(device.getUuid());
        }
        for (MobileAgent agent: myAgents){
            if (agent.knowDevice(device) != null){
                return agent;
            }
        }
        return null;
    }
    public ThingAction getConfiguration(AbstractContextArr currContext, Class thingType, Action actionFilter) {
        if ((lastCtx == null) || (currContext.distanceTo(lastCtx) > 5)) {
            lastCandidate = new PriorityQueue<>(myDevices.size() + myAgents.size(), Collections.reverseOrder());
            for (DeviceAgent agent: myDevices){
                ThingAction newAction = agent.getConfiguration(currContext, thingType, actionFilter);
                if (newAction != null){
                    lastCandidate.add(new AgentActionTuple(agent, newAction));
                }
            }
            for (MobileAgent agent: myAgents){
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
//        lastAgentAction.action.doAction();
        return lastAgentAction.action;
    }

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
