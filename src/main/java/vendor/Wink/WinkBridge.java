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

package vendor.Wink;

import java.util.List;
import java.util.logging.Logger;

import service.ServiceAdapterManager;
import thing.command.Command;
import thing.command.Response;
import thing.component.Bridge;
import thing.component.Thing;
import thing.component.ThingState;

public final class WinkBridge extends Bridge {
    private static final String TAG = WinkBridge.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public WinkBridge(String uuid) {
        super(uuid);
    }

    @Override
    public void setCredentialRequired() {
        LOGGER.warning("setCredentialRequired() is not implemented");
    }

    @Override
    public void setThingAccessCredentialClasses() {
        LOGGER.warning("setThingAccessCredentialClasses() is not implemented");
    }

    @Override
    public ThingState getThingState() {
        LOGGER.warning("getThingState() is not implemented");
        return null;
    }

    @Override
    public Response callCommand(ServiceAdapterManager serviceAdapterManager, Command command) {
        return null;
    }

    @Override
    public void sendCommand(ServiceAdapterManager serviceAdapterManager, Command command) {

    }

    @Override
    public Response receiveResponse() {
        return null;
    }

    @Override
    public List<Thing> getThings(ServiceAdapterManager serviceAdapterManager) {
        return null;
    }

    @Override
    public List<ThingState> getThingsState(ServiceAdapterManager serviceAdapterManager) {
        return null;
    }

    @Override
    public boolean updateThingState(ServiceAdapterManager serviceAdapterManager, Thing thing, ThingState thingState) {
        return false;
    }

    @Override
    public boolean updateThingState(ServiceAdapterManager serviceAdapterManager, Thing thing, ThingState thingState, boolean postCheck) {
        return false;
    }
}
