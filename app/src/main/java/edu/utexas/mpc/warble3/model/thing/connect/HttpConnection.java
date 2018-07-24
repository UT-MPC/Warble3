package edu.utexas.mpc.warble3.model.thing.connect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.utexas.mpc.warble3.database.interfaces.ConnectionStoreable;
import edu.utexas.mpc.warble3.model.thing.component.Thing;

public class HttpConnection extends Connection implements ConnectionStoreable {
    private static final String TAG = "HttpConnection";

    public enum IP_TYPE {
        IPv4,
        IPv6
    }

    private String url;
    private String ipAddress;
    private IP_TYPE ipType;

    public HttpConnection(Thing source, Thing destination) {
        super(source, destination);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public IP_TYPE getIpType() {
        return ipType;
    }

    public void setIpType(IP_TYPE ipType) {
        this.ipType = ipType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toConnectionInfo() {
        return String.format("%s %s", ipType.name(), url);
    }

    @Override
    public void fromConnectionInfo(String connectionInfo) {
        Pattern pattern = Pattern.compile("(IPv4|IPv6)(\\s+)((https://|http://)?([0-9.]+)/*)");
        Matcher matcher = pattern.matcher(connectionInfo);

        if (matcher.find()) {
            ipType = IP_TYPE.valueOf(matcher.group(1));
            url = matcher.group(3);
            ipAddress = matcher.group(5);
        }
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s %s \"%s\"", TAG, ipType.toString(), ipAddress);
        return string;
    }
}