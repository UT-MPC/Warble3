package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import edu.utexas.mpc.warble3.model.thing.component.Light;

public final class PhilipsHueLight extends Light {
    private static final String TAG = "PhilipsHueLight";

    public PhilipsHueLight() {
        super();
    }

    @Override
    public Boolean authenticate() {
        return false;
    }
}
