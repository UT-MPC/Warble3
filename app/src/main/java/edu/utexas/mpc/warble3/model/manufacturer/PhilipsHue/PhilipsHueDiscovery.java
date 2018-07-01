package edu.utexas.mpc.warble3.model.manufacturer.PhilipsHue;

import android.util.Log;

import java.util.List;

import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.discovery.SSDPDiscovery;

public class PhilipsHueDiscovery extends SSDPDiscovery {
    private static final String TAG = "PhilipsHueDiscovery";

    @Override
    public List<PhilipsHueBridge> onDiscover() {
        sendDatagramPacket();
        List<String> messages = receiveDatagramPacket();
        for (String message: messages) {
            Log.i(TAG, message);
        }
        return null;
    }

    @Override
    public List<Thing> onDiscoverDescendants() {
        return null;
    }
}
