package edu.utexas.mpc.warble3.warble.thing.util;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Location implements Serializable {
    @Expose private double latitude;
    @Expose private double longitude;

    @Expose private double indoorX;
    @Expose private double indoorY;
    @Expose private double indoorZ;

    public Location() {}

    public Location(double latitude, double longitude, double indoorX, double indoorY, double indoorZ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.indoorX = indoorX;
        this.indoorY = indoorY;
        this.indoorZ = indoorZ;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getIndoorX() {
        return indoorX;
    }

    public void setIndoorX(double indoorX) {
        this.indoorX = indoorX;
    }

    public double getIndoorY() {
        return indoorY;
    }

    public void setIndoorY(double indoorY) {
        this.indoorY = indoorY;
    }

    public double getIndoorZ() {
        return indoorZ;
    }

    public void setIndoorZ(double indoorZ) {
        this.indoorZ = indoorZ;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", indoorX=" + indoorX +
                ", indoorY=" + indoorY +
                ", indoorZ=" + indoorZ +
                '}';
    }

    public static Location fromString(String string) {
        if (string == null || string.equals("")) {
            return null;
        }
        else {
            return new Gson().fromJson(string.replace("Location", ""), Location.class);
        }
    }
}
