package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context;

import java.util.ArrayList;

/*
    Base single context type
 */
public abstract class BaseContext {
    public abstract BaseContext makeClone();
    public abstract double distanceTo (BaseContext ctx);
    public abstract boolean isBetween (BaseContext ctxA, BaseContext ctxB);
    public abstract BaseContext getOffset(double offset);
    public abstract BaseContext getMidCtx(BaseContext ctxB, double ratio);
    /*
        This method is used to find the splitters to branch
     */
    public abstract ArrayList<BaseContext[]> splitPoints(BaseContext bContext, double ratio);
}

