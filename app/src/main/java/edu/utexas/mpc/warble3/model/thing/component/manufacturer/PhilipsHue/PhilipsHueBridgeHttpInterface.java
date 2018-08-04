package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import java.util.List;

public interface PhilipsHueBridgeHttpInterface {
    String createUser(String username);

    List<PhilipsHueLight> getLights();

    PhilipsHueLightState getLightState(PhilipsHueLight philipsHueLight);
    void putLight(PhilipsHueLight philipsHueLight, PhilipsHueLightState philipsHueLightState);
}
