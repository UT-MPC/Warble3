package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context;

import java.util.ArrayList;

public class TimeContext extends BaseContext{
    public static double TIMEBIAS = 1.0/720.0;
    private int timeOfDay;              // in terms of seconds of the day. scale 0-86399. Normalize to 0-720
    private static int MAX = 86399;

    public TimeContext(int time){
        timeOfDay = time;
    }

    public int getTime() {
        return timeOfDay;
    }

    public void setTime(int time) {
        this.timeOfDay = time;
    }

    @Override
    public TimeContext makeClone(){
        return new TimeContext(timeOfDay);
    }

    @Override
    public int distanceTo (BaseContext ctx){
        if (TimeContext.class.isInstance(ctx)){
            int pureSub = Math.abs(timeOfDay - ((TimeContext)ctx).getTime());
            if (pureSub > MAX / 2){
                pureSub = MAX - pureSub;
            }
            return (int)(pureSub * TIMEBIAS);
        } else {
            return 0;
        }
    }

    @Override
    public boolean isBetween(BaseContext A, BaseContext B){
        if (!TimeContext.class.isInstance(A) || !TimeContext.class.isInstance(B)){
            return false;
        }
        if ((timeOfDay < ((TimeContext)A).getTime()) || (timeOfDay > ((TimeContext)B).getTime())){
            return false;
        }
        return true;
    }

    @Override
    public TimeContext getOffset(int offset){
        int newT = timeOfDay + (int)(offset / TIMEBIAS);
        return new TimeContext(newT);
    }

    @Override
    public TimeContext getMidCtx(BaseContext ctxB, double ratio){
        TimeContext ctx = (TimeContext) ctxB;
        double newT = timeOfDay * ratio + ctx.getTime() * (1 - ratio);
        return new TimeContext((int)newT);
    }

    @Override
    public int longAxis(BaseContext ctxB){
        return distanceTo(ctxB);
    }

    @Override
    public void splitLongAxis(BaseContext massCtx, BaseContext oldMin, BaseContext oldMax, BaseContext newMin, BaseContext newMax, double ratio){
        TimeContext ctx = (TimeContext) massCtx;
        TimeContext midCtx = getMidCtx(massCtx, ratio / (ratio + 1));
        if (timeOfDay < ctx.getTime()){
            ((TimeContext) newMax).setTime(midCtx.getTime());
            ((TimeContext) oldMin).setTime(midCtx.getTime());
        }else{
            ((TimeContext) newMin).setTime(midCtx.getTime());
            ((TimeContext) oldMax).setTime(midCtx.getTime());
        }
    }

    @Override
    public ArrayList<BaseContext[]> splitPoints(BaseContext bContext, double ratio){
        if (bContext instanceof TimeContext){
            TimeContext bCtx = (TimeContext) bContext;
            int t1 = timeOfDay;
            int t2 = bCtx.timeOfDay;
            int tPoint = (int)(t1 + (t2 - t1) * ratio);
            TimeContext newMax1 = new TimeContext(tPoint);
            TimeContext newMin1 = new TimeContext(tPoint);
            ArrayList<BaseContext[]> output = new ArrayList<>();
            output.add(new BaseContext[]{newMax1, newMin1});
            return output;
        }
        return null;
    }

}
