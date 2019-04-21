package edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction;

import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.component.ThingState;

/* Action is a action to take when making a decision.
 *
 */
public interface Action {

    int checkAction();
    void doAction();
    void undoAction();
    Thing getDevice();
    boolean isSame(Action b);
    boolean isStateCompatible(ThingState state);

    boolean actionFilter(Action b);
}
