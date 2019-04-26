package edu.utexas.mpc.warble3.util;

import android.bluetooth.le.ScanResult;

import java.sql.Date;

public class Beacon {
    public static final byte[] beaconPrefix = {0x02, 0x01, 0x04, 0x1B, (byte) 0xFE}; //(byte) 0x8B <- STACON PREFIX

    String deviceAddress;
    Date date;
    long rssi;
    byte[] payload;

    public Beacon() {
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getRssi() {
        return rssi;
    }

    public void setRssi(long rssi) {
        this.rssi = rssi;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public static boolean verifyBeacon(ScanResult scanResult) {
        byte[] rawBeacon = scanResult.getScanRecord().getBytes();
        for (int i = 0; i < beaconPrefix.length; ++i) {
            if (rawBeacon[i] != beaconPrefix[i]) {
                return false;
            }
        }
        return true;
    }
}
