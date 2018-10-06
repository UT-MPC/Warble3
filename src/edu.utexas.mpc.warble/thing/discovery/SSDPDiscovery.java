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

package edu.utexas.mpc.warble.thing.discovery;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class SSDPDiscovery extends Discovery {
    private static final String TAG = SSDPDiscovery.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    protected static final String IPv4_MULTICAST_ADDRESS = "239.255.255.250";
    protected static final int IPv4_SSDP_PORT = 1900;
    protected static final String IPv4_MSEARCH_MESSAGE = "M-SEARCH * HTTP/1.1\r\n"
            + "HOST: 239.255.255.250:1900\r\n"
            + "MAN: \"ssdp:discover\"\r\n"
            + "MX: 3\r\n"
            + "ST: ssdp:all\r\n";

    protected static int TIMEOUT_MICROSECONDS = 5000;

    protected int discovery_period_microseconds;

    protected MulticastSocket getMulticastSocket() {
        MulticastSocket multicastSocket;
        try {
            multicastSocket = new MulticastSocket(IPv4_SSDP_PORT);
            return multicastSocket;
        }
        catch (IOException e) {
            LOGGER.severe(e.getClass().toString());
            return null;
        }
    }

    protected InetAddress getInetAddress() {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(IPv4_MULTICAST_ADDRESS);
            return inetAddress;
        }
        catch (UnknownHostException e) {
            LOGGER.severe(e.getClass().toString());
            return null;
        }
    }

    protected Boolean sendDatagramPacket() {
        MulticastSocket multicastSocket = getMulticastSocket();
        InetAddress inetAddress = getInetAddress();

        if (multicastSocket != null & inetAddress != null) {
            DatagramPacket datagramPacket = new DatagramPacket(IPv4_MSEARCH_MESSAGE.getBytes(), IPv4_MSEARCH_MESSAGE.length(), inetAddress, 1900);

            try {
                multicastSocket.setSoTimeout(TIMEOUT_MICROSECONDS);
            }
            catch (SocketException e) {
                LOGGER.severe(e.getClass().toString());
                return false;
            }

            try {
                multicastSocket.send(datagramPacket);
            }
            catch (IOException e) {
                LOGGER.severe(e.getClass().toString());
                return false;
            }
        }
        return true;
    }

    protected List<String> receiveDatagramPacket() {
        List<String> messages = new ArrayList<>();

        MulticastSocket multicastSocket = getMulticastSocket();
        try {
            multicastSocket.setSoTimeout(TIMEOUT_MICROSECONDS);
        }
        catch (SocketException e) {
            LOGGER.severe(e.toString());
        }

        if (discovery_period_microseconds == 0) {
            discovery_period_microseconds = TIMEOUT_MICROSECONDS;
        }

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < discovery_period_microseconds) {
            byte[] buf = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            try {
                multicastSocket.receive(receivePacket);
            }
            catch (SocketTimeoutException e) {
                LOGGER.fine("Multicast socket is interrupted");
            }
            catch (IOException e) {
                LOGGER.severe(e.getClass().toString());
                return null;
            }

            String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            messages.add(msg);
        }

        if (messages.size() > 0) {
            return messages;
        }
        else {
            return null;
        }
    }
}
