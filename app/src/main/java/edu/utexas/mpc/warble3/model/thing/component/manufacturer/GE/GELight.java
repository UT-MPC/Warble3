package edu.utexas.mpc.warble3.model.thing.component.manufacturer.GE;

import edu.utexas.mpc.warble3.model.thing.component.Light;

public final class GELight extends Light {
    public GELight() {
        super();
    }

    @Override
    public Boolean authenticate() {
        return false;
    }
}
