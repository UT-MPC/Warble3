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

package edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;

public final class PhilipsHueDiscoveryAsyncTask extends AsyncTask<Void, Void, List<? extends Thing>> {
    @Override
    protected List<? extends Thing> doInBackground(Void... voids) {
        PhilipsHueUPnPDiscovery discovery = new PhilipsHueUPnPDiscovery();

        List<PhilipsHueBridge> philipsHueBridges = discovery.onDiscover();

        List<Thing> philipsHueThings = new ArrayList<>();

        if (philipsHueBridges != null) {
            philipsHueThings.addAll(philipsHueBridges);
        }

        return philipsHueThings;
    }
}
