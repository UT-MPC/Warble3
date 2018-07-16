package edu.utexas.mpc.warble3.model.thing.feature;

public interface Switcher {
    Boolean switchOn(Boolean postCheck);
    Boolean switchOff(Boolean postCheck);
}
