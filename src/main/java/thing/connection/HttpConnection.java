/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package thing.connection;

import thing.component.Thing;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpConnection extends Connection {
    private static final String TAG = HttpConnection.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public enum IP_TYPE {
        IPv4,
        IPv6
    }

    private String url;
    private String ipAddress;
    private IP_TYPE ipType;

    public HttpConnection() {
        super();
        setDirectionalType(DIRECTIONAL_TYPE.UNIDIRECTIONAL);
    }

    public HttpConnection(Thing source, Thing destination) {
        super(source, destination);
        setDirectionalType(DIRECTIONAL_TYPE.UNIDIRECTIONAL);
    }

    @Override
    public boolean testConnection() {
        return true;
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
    public String toStoreableText() {
        return String.format("%s %s", ipType.name(), url);
    }

    @Override
    public void fromStoreableText(String storeableText) {
        Pattern pattern = Pattern.compile("(IPv4|IPv6)(\\s+)((https://|http://)?([0-9.]+)(:([0-9]+))*/*)");
        Matcher matcher = pattern.matcher(storeableText);

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
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof HttpConnection)) {
            return false;
        }

        HttpConnection c = (HttpConnection) object;

        return (this.getSource().equals(c.getSource())) &&
                (this.ipAddress.equals(c.ipAddress)) &&
                (this.url.equals(c.url)) &&
                (this.ipType.equals(c.ipType));
    }

}