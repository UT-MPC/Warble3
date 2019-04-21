package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context;

import java.util.ArrayList;

/*
    Base single context type
 */
public abstract class BaseContext {
    public abstract BaseContext makeClone();
    public abstract int distanceTo (BaseContext ctx);
    public abstract boolean isBetween (BaseContext ctxA, BaseContext ctxB);
    public abstract BaseContext getOffset(int offset);
    public abstract BaseContext getMidCtx(BaseContext ctxB, double ratio);
    public abstract int longAxis(BaseContext ctxB);
    public abstract void splitLongAxis(BaseContext massCtx, BaseContext oldMin, BaseContext oldMax, BaseContext newMin, BaseContext newMax, double ratio);

    /*
        This method is used to find the splitters to branch
     */
    public abstract ArrayList<BaseContext[]> splitPoints(BaseContext bContext, double ratio);
}
