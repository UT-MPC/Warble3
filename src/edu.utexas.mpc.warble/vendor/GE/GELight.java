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

package edu.utexas.mpc.warble.vendor.GE;

import edu.utexas.mpc.warble.thing.command.Command;
import edu.utexas.mpc.warble.thing.command.Response;
import edu.utexas.mpc.warble.thing.component.Light;
import edu.utexas.mpc.warble.thing.component.ThingState;
import edu.utexas.mpc.warble.thing.credential.ThingAccessCredential;

import java.util.logging.Logger;

public final class GELight extends Light {
    private static final String TAG = GELight.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public GELight() {
        super();
    }

    @Override
    public boolean authenticate() {
        return false;
    }

    @Override
    public boolean authenticate(ThingAccessCredential thingAccessCredential) {
        return false;
    }

    @Override
    public void setCredentialRequired() {
        setCredentialRequired(false);
    }

    @Override
    public void setThingAccessCredentialClasses() {
        setThingAccessCredentialClasses(null);
    }

    @Override
    public void setState(ThingState thingState) {

    }

    @Override
    public Response callCommand(Command command) {
        return null;
    }

    @Override
    public void sendCommand(Command command) {

    }

    @Override
    public Response receiveResponse() {
        return null;
    }
}
