package edu.utexas.mpc.warble3.warble.selector.CtxSelector.Agent;

import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.AbstractContextArr;

import java.util.Random;

/*
 * AbstractAgent is the smart control of the system.

 */
public abstract class AbstractAgent {
    public AbstractAgent(){    }
    protected int chooseRand(int max){
        Random rand = new Random(System.currentTimeMillis());
        return rand.nextInt(max);
    }

    public abstract void getPunished();
    public abstract void getPraised();
    public abstract void getSmallPunished();
}

