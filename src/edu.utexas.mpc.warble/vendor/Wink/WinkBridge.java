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

package edu.utexas.mpc.warble.vendor.Wink;

import edu.utexas.mpc.warble.thing.command.Command;
import edu.utexas.mpc.warble.thing.command.Response;
import edu.utexas.mpc.warble.thing.component.Bridge;
import edu.utexas.mpc.warble.thing.component.Thing;
import edu.utexas.mpc.warble.thing.component.ThingState;
import edu.utexas.mpc.warble.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble.thing.credential.UsernamePasswordCredential;

import java.util.Collections;
import java.util.List;

public final class WinkBridge extends Bridge {
    public WinkBridge() {
        super();
    }

    @Override
    public List<Thing> getThings() {
        return null;
    }

    @Override
    public List<ThingState> getThingsState() {
        return null;
    }

    @Override
    public boolean updateThingState(Thing thing, ThingState thingState) {
        return false;
    }

    @Override
    public boolean updateThingState(Thing thing, ThingState thingState, boolean postCheck) {
        return false;
    }

    @Override
    public boolean updateThingsState(List<Thing> things, List<ThingState> thingsState) {
        return false;
    }

    @Override
    public boolean updateThingsState(List<Thing> things, List<ThingState> thingsState, boolean postCheck) {
        return false;
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
        setCredentialRequired(true);
    }

    @Override
    public void setThingAccessCredentialClasses() {
        setThingAccessCredentialClasses(Collections.<Class>singletonList(UsernamePasswordCredential.class));
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
