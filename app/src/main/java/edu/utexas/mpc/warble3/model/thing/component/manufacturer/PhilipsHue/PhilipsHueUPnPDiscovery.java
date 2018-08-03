package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import android.util.Log;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.thing.connect.HttpConnection;
import edu.utexas.mpc.warble3.model.thing.discovery.SSDPDiscovery;
import edu.utexas.mpc.warble3.util.Logging;

public class PhilipsHueUPnPDiscovery extends SSDPDiscovery {
    private static final String TAG = "PhilipsHueUPnPDiscovery";

    private static final String RESPONSE_SIGNATURE = "IpBridge";

    private Boolean isPhilipsHue(String response) {
        return response.contains(RESPONSE_SIGNATURE);
    }

    private static Document loadDocument(String stringUrl) {
        Document document = null;
        try {
            URL url = new URL(stringUrl);

            SAXBuilder saxBuilder = new SAXBuilder();
            document = saxBuilder.build(url);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
        return document;
    }

    private PhilipsHueBridge bridgeXmlTranslation(String response) {
        Pattern pattern = Pattern.compile("LOCATION: (.+?)(\\r*)(\\n)", Pattern.DOTALL | Pattern.MULTILINE);

        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            String xmlUrl = matcher.group(1);

            String name;
            String friendlyName;

            String uuid;

            String accessName;
            String accessUsername;
            String accessPasscode;

            String manufacturerSerialNumber;
            String manufacturerModelName;
            String manufacturerModelNumber;
            String manufacturerName;

            List<Connection> connections = new ArrayList<>();

            PhilipsHueBridge new_bridge;

            try {
                Document document = loadDocument(xmlUrl);

                Element rootElement = document.getRootElement();
                Element URLBaseElement = rootElement.getChild("URLBase", rootElement.getNamespace());
                Element deviceElement = rootElement.getChild("device", rootElement.getNamespace());

                new_bridge = new PhilipsHueBridge();

                name = deviceElement.getChild("friendlyName", rootElement.getNamespace()).getText();
                friendlyName = deviceElement.getChild("friendlyName", rootElement.getNamespace()).getText();
                uuid = deviceElement.getChild("UDN", rootElement.getNamespace()).getText().replace("uuid:", "");
                new_bridge.setName(name);
                new_bridge.setFriendlyName(friendlyName);
                new_bridge.setUuid(uuid);

                accessName = null;
                accessUsername = null;
                accessPasscode = null;
                new_bridge.setAccessName(accessName);
                new_bridge.setAccessUsername(accessUsername);
                new_bridge.setAccessPasscode(accessPasscode);

                manufacturerSerialNumber = deviceElement.getChild("serialNumber", rootElement.getNamespace()).getText();
                manufacturerModelName = deviceElement.getChild("modelName", rootElement.getNamespace()).getText();
                manufacturerModelNumber = deviceElement.getChild("modelNumber", rootElement.getNamespace()).getText();
                manufacturerName = deviceElement.getChild("manufacturer", rootElement.getNamespace()).getText();
                new_bridge.setManufacturerSerialNumber(manufacturerSerialNumber);
                new_bridge.setManufacturerModelName(manufacturerModelName);
                new_bridge.setManufacturerModelNumber(manufacturerModelNumber);
                new_bridge.setManufacturerName(manufacturerName);

                HttpConnection httpConnection = new HttpConnection(new_bridge, null);
                httpConnection.setUrl(URLBaseElement.getText());
                httpConnection.setIpType(HttpConnection.IP_TYPE.IPv4);
                httpConnection.setIpAddress(getIpAddressFromURL(URLBaseElement.getText()));
                connections.add(httpConnection);
                new_bridge.setConnections(connections);

                return new_bridge;
            }
            catch (Exception e) {
                if (Logging.ERROR) Log.e(TAG, "exception", e);
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public List<PhilipsHueBridge> onDiscover() {
        List<PhilipsHueBridge> philipsHueBridges = new ArrayList<>();
        List<String> existingIpAddresses = new ArrayList<>();

        sendDatagramPacket();
        List<String> responses = receiveDatagramPacket();

        for (String response : responses) {
            if (Logging.VERBOSE) Log.v(TAG, response);
            if (isPhilipsHue(response)) {
                PhilipsHueBridge bridge = bridgeXmlTranslation(response);
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

        if (Logging.INFO) Log.i(TAG, String.format("Number of discovered Philips Hue Bridges by UPnP: %d", philipsHueBridges.size()));
        if (philipsHueBridges.size() > 0) {
            if (Logging.INFO) Log.i(TAG, "Discover Philips Hue Bridges by UPnP:");
            for (PhilipsHueBridge philipsHueBridge : philipsHueBridges) {
                if (Logging.INFO) Log.i(TAG, String.format("- %s", philipsHueBridge.toString()));
            }
            return philipsHueBridges;
        }
        else {
            if (Logging.INFO) Log.i(TAG, "No Philips Hue Bridges by UPnP discovered");
            return null;
        }
    }

    @Override
    public List<Thing> onDiscoverDescendants() {
        return null;
    }

    private String getIpAddressFromURL(String url) {
        Pattern pattern = Pattern.compile("(http://|https://)*([0-9.]+)+(:[0-9]*)*(/)*");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(2);
        }
        else {
            return url;
        }
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s", TAG);
        return string;
    }
}
