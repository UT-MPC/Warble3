package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import java.util.List;

public interface PhilipsHueBridgeHttpInterface {
    String createUser(String username);
    String getUserInfo(String user);

    List<PhilipsHueLight> getLights(String user);

    PhilipsHueLightState getLightState(String user, PhilipsHueLight philipsHueLight);
    void putLight(String user, PhilipsHueLight philipsHueLight, PhilipsHueLightState philipsHueLightState);
}
