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

package selector;

import thing.component.THING_CONCRETE_TYPE;
import thing.component.Thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ThingConcreteTypeSelector extends Selector {
    private static final String TAG = ThingConcreteTypeSelector.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private List<THING_CONCRETE_TYPE> thingConcreteTypes;

    public ThingConcreteTypeSelector(THING_CONCRETE_TYPE... thingConcreteTypes) {
        this.thingConcreteTypes = Arrays.asList(thingConcreteTypes);
    }

    @Override
    public List<Thing> select(List<Thing> things, int k) {
        if ((things == null) || things.size() == 0 || (k == 0)) {
            return null;
        }

        List<Thing> returnThings = new ArrayList<>();

        if (k < 0) {
            for (Thing thing : things) {
                if (thingConcreteTypes.contains(thing.getThingConcreteType())) {
                    returnThings.add(thing);
                }
            }
        } else {
            for (Thing thing : things) {
                if (thingConcreteTypes.contains(thing.getThingConcreteType())) {
                    returnThings.add(thing);
                }
                if (returnThings.size() >= k) {
                    break;
                }
            }
        }

        return returnThings;
    }
}
