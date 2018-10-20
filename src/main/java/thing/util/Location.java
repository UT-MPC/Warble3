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

package thing.util;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.logging.Logger;

public class Location implements Serializable {
    private static final String TAG = Location.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

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
                "lat=" + latitude +
                ", lon=" + longitude +
                ", inX=" + indoorX +
                ", inY=" + indoorY +
                ", inZ=" + indoorZ +
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
