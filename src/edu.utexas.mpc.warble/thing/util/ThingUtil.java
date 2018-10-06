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

package edu.utexas.mpc.warble.thing.util;

import edu.utexas.mpc.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble.thing.component.Thing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ThingUtil {
    private static final String TAG = ThingUtil.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public static HashMap<THING_CONCRETE_TYPE, List<Thing>> toThingHashMapByConcreteType(List<Thing> things) {
        if (things == null) {
            return null;
        }
        else {
            HashMap<THING_CONCRETE_TYPE, List<Thing>> thingsHashMap = new HashMap<>();

            for (Thing thing : things) {
                List<Thing> listThings = thingsHashMap.get(thing.getThingConcreteType());
                if (listThings == null) listThings = new ArrayList<>();
                listThings.add(thing);
                thingsHashMap.put(thing.getThingConcreteType(), listThings);
            }

            return thingsHashMap;
        }
    }
}
