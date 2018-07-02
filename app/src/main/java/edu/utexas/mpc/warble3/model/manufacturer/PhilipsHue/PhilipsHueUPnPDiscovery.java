package edu.utexas.mpc.warble3.model.manufacturer.PhilipsHue;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import edu.utexas.mpc.warble3.model.Thing;
import edu.utexas.mpc.warble3.model.connect.Connection;
import edu.utexas.mpc.warble3.model.connect.HttpConnection;
import edu.utexas.mpc.warble3.model.discovery.SSDPDiscovery;

public class PhilipsHueUPnPDiscovery extends SSDPDiscovery {
    private static final String TAG = "PhilipsHueUPnPDiscovery";

    private static final String response_signature = "IpBridge";

    private Boolean isPhilipsHue(String response) {
        return response.contains(response_signature);
    }

    private static Document loadDocument(String stringUrl) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(stringUrl).openStream());
    }


    // TODO : Implement
    private PhilipsHueBridge toPhilipsHueBridge(String response) {
        return null;
    }

    @Override
    public List<PhilipsHueBridge> onDiscover() {
        Log.d(TAG, "onDiscover()");
        List<PhilipsHueBridge> philipsHueBridges = new ArrayList<>();
        List<String> existingIpAddresses = new ArrayList<>();

        sendDatagramPacket();
        List<String> responses = receiveDatagramPacket();

        for (String response : responses) {
            Log.v(TAG, response);
            if (isPhilipsHue(response)) {
                PhilipsHueBridge bridge = toPhilipsHueBridge(response);
                if (bridge != null) {
                    List<Connection> connections = bridge.getConnections();
                    for (Connection connection : connections) {
                        if (connection instanceof HttpConnection) {
                            if (!existingIpAddresses.contains(((HttpConnection) connection).getIpAddress())) {
                                philipsHueBridges.add(bridge);
                                existingIpAddresses.add(((HttpConnection) connection).getIpAddress());
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (philipsHueBridges.size() > 0) {
            Log.i(TAG, "Discover Philips Hue Bridges by UPnP:");
            for (PhilipsHueBridge philipsHueBridge : philipsHueBridges) {
                Log.i(TAG, String.format("- %s", philipsHueBridge.getFriendlyName()));
            }
            return philipsHueBridges;
        }
        else {
            Log.i(TAG, "No Philips Hue Bridges by UPnP discovered");
            return null;
        }
    }

    @Override
    public List<Thing> onDiscoverDescendants() {
        return null;
    }
}
