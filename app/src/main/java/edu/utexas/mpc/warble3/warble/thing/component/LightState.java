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
 *
 */

package edu.utexas.mpc.warble3.warble.thing.component;

import android.util.Log;

import edu.utexas.mpc.warble3.util.Logging;

public class LightState extends ThingState {
    private static String TAG = "ThingState";

    private ACTIVE_STATE active;

    private int brightness = -1;            // range 0 - 65536, -1 means not set
    private int hue = -1;                   // range 0 - 65536, -1 means not set
    private int saturation = -1;            // range 0 - 65536, -1 means not set

    private int red = -1;                   // range 0 - 65536, -1 means not set
    private int green = -1;                 // range 0 - 65536, -1 means not set
    private int blue = -1;                  // range 0 - 65536, -1 means not set

    public ACTIVE_STATE getActive() {
        return active;
    }

    public void setActive(ACTIVE_STATE active) {
        this.active = active;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        if (brightness < 0){
            this.brightness = 0;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setBrightness(int brightness) - Setting brightness as %s, instead of %s", this.hue, hue));
        }
        else if (brightness > 65536) {
            if (Logging.VERBOSE) Log.v(TAG, String.format("setBrightness(int brightness) - Setting brightness as %s, instead of %s", this.hue, hue));
            this.brightness = 65536;
        }
        else {
            this.brightness = brightness;
        }
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        if (hue < 0){
            this.hue = 0;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setHue(int hue) - Setting hue as %s, instead of %s", this.hue, hue));
        }
        else if (hue > 65536) {
            this.hue = 65536;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setHue(int hue) - Setting hue as %s, instead of %s", this.hue, hue));
        }
        else {
            this.hue = hue;
        }
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        if (saturation < 0){
            this.saturation = 0;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setSaturation(int saturation) - Setting saturation as %s, instead of %s", this.saturation, saturation));
        }
        else if (saturation > 65536) {
            this.saturation = 65536;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setSaturation(int saturation) - Setting saturation as %s, instead of %s", this.saturation, saturation));
        }
        else {
            this.saturation = saturation;
        }
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        if (red < 0){
            this.red = 0;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setRed(int red) - Setting red as %s, instead of %s", this.red, red));
        }
        else if (red > 65536) {
            this.red = 65536;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setRed(int red) - Setting red as %s, instead of %s", this.red, red));
        }
        else {
            this.red = red;
        }
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        if (green < 0){
            this.green = 0;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setGreen(int green) - Setting green as %s, instead of %s", this.green, green));
        }
        else if (green > 65536) {
            this.green = 65536;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setGreen(int green) - Setting green as %s, instead of %s", this.green, green));
        }
        else {
            this.green = green;
        }
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        if (blue < 0){
            this.blue = 0;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setBlue(int blue) - Setting blue as %s, instead of %s", this.blue, blue));
        }
        else if (blue > 65536) {
            this.blue = 65536;
            if (Logging.VERBOSE) Log.v(TAG, String.format("setBlue(int blue) - Setting blue as %s, instead of %s", this.blue, blue));
        }
        else {
            this.blue = blue;
        }
    }

    @Override
    public String toString() {
        String string = "";
        string += "LightState:";
        string += String.format("active=%s;", active);
        return string;
    }
}
