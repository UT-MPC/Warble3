package edu.utexas.mpc.warble3.model.connect;

public class HttpConnection extends Connection {
    private static final String TAG = "HttpConnection";

    public enum IP_TYPE {
        IPv4,
        IPv6
    }

    private String url;
    private String ipAddress;
    private IP_TYPE ipType;

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
    public String toString() {
        String string = "";
        string += String.format("%s %s \'%s\'", TAG, ipType.toString(), ipAddress);
        return string;
    }
}