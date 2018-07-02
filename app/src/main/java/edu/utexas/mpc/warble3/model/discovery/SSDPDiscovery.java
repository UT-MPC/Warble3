package edu.utexas.mpc.warble3.model.discovery;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class SSDPDiscovery extends Discovery {
    private static final String TAG = "SSDPDiscovery";

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
            Log.e(TAG, e.getClass().toString(), e);
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
            Log.e(TAG, e.getClass().toString(), e);
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
                Log.e(TAG, e.getClass().toString(), e);
                return false;
            }

            try {
                multicastSocket.send(datagramPacket);
            }
            catch (IOException e) {
                Log.e(TAG, e.getClass().toString(), e);
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
            Log.e(TAG, "exception", e);
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
                Log.d(TAG, "Multicast socket is interrupted");
            }
            catch (IOException e) {
                Log.e(TAG, e.getClass().toString(), e);
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
