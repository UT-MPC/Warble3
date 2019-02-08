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

package thing.component;

public enum THING_FUNCTION_TYPE {
    ACCESSOR("ACCESSOR"),                   // ex: bridge
    AMBIENT("AMBIENT"),                    // ex: air conditioner, heater, diffuser, humidifier, fan, dehumidifier
    FOOD_DRINK_PROCESSOR("FOOD_DRINK_PROCESSOR"),     // ex: coffee machine
    FOOD_DRINK_STORAGE("FOOD_DRINK_STORAGE"),         // ex: refrigerator, freezer
    GARDEN_MANAGEMENT("GARDEN_MANAGEMENT"),          // ex: sprinkler
    HOUSE_UTILITY("HOUSE_UTILITY"),              // ex: electric plug, doorbell
    LIGHTING("LIGHTING"),                   // ex: table lamp, room lamp
    ROOM_COVER("ROOM_COVER"),                 // ex: blind, curtain
    SECURITY("SECURITY"),                   // ex: lock
    STORAGE("STORAGE"),                    // ex:
    VISION("VISION");                     // ex: camera, cctv

    private final String val;

    THING_FUNCTION_TYPE(String val) {
        this.val = val;
    }

    public String toString() {
        return val;
    }
}
