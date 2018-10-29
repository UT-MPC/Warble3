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

import context.ContextManager;
import selector.AllThingSelector;
import selector.Selector;
import service.SERVICE_ADAPTER_TYPE_INPUT;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;
import service.ServiceAdapter;
import service.ServiceAdapterManager;
import thing.ThingManager;
import thing.command.Command;
import thing.command.Response;
import thing.component.Thing;
import user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Warble {
    private static final String TAG = Warble.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private UserManager userManager;
    private ThingManager thingManager;
    private ContextManager contextManager;

    private ServiceAdapterManager serviceAdapterManager;

    public Warble() {
        serviceAdapterManager = new ServiceAdapterManager();

        contextManager = new ContextManager();
        contextManager.setServiceAdapter(serviceAdapterManager);

        userManager = new UserManager();
        userManager.setServiceAdapter(serviceAdapterManager);

        thingManager = new ThingManager();
        thingManager.setServiceAdapter(serviceAdapterManager);
    }

    // ============= User =============
    public User getUser(String username) {
        return userManager.getUser(username);
    }

    public User authenticateUser(String username, String password) {
        return userManager.authenticateUser(username, password);
    }

    public void createUser(String username, String password) throws DuplicateUsernameException, InvalidUsernameException, InvalidPasswordException {
        userManager.createUser(username, password);
    }

    // ============ Thing =============
    public void discoverThings() {
        thingManager.discover();
    }

    public void discoverThings(boolean authenticateThings) {
        discoverThings();
        if (authenticateThings) {
            authenticateThings();
        }
    }

    public List<Thing> fetch(List<Selector> template) {
        return fetch(template, 1);
    }

    public List<Thing> fetch(List<Selector> template, int numberOfThings) {
        List<Thing> things = thingManager.getThings();

        if (template == null) {
            template = new ArrayList<>();
        }

        if (template.size() == 0) {
            template.add(new AllThingSelector());
        }

        for (Selector selector : template) {
            List<Thing> newThings = selector.select(things);
            if (newThings != null) {
                things.addAll(newThings);
            }
        }

        if (things.size() == 0) {
            return null;
        } else if ((numberOfThings != -1) & (things.size() > numberOfThings)) {
            return things.subList(0, numberOfThings - 1);
        } else {
            return things;
        }
    }

    public void reject(List<Thing> things) {
        // TODO: Implement
    }

    public void authenticateThings() {
        thingManager.authenticateThings(fetch(null, -1));
    }

    public void updateThing(Thing thing) {
        thingManager.saveThing(thing);
    }

    public Thing loadThing(Thing thing) {
        return thingManager.loadThing(thing);
    }

    // =========== Command ============
    public Response sendCommand(Command command, Thing thing) {
        return thingManager.sendCommand(contextManager.getContext(), command, thing);
    }

    // =========== Service Adapter ============
    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT serviceAdapterType) {
        return serviceAdapterManager.getServiceAdapter(serviceAdapterType);
    }

    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT serviceAdapterType) {
        return serviceAdapterManager.getServiceAdapter(serviceAdapterType);
    }

    public void setServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT serviceAdapterType, ServiceAdapter serviceAdapter) {
        serviceAdapterManager.setServiceAdapter(serviceAdapterType, serviceAdapter);

        userManager.setServiceAdapter(serviceAdapterManager);
        thingManager.setServiceAdapter(serviceAdapterManager);
        contextManager.setServiceAdapter(serviceAdapterManager);
    }

    public void setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT serviceAdapterType, ServiceAdapter serviceAdapter) {
        serviceAdapterManager.setServiceAdapter(serviceAdapterType, serviceAdapter);

        userManager.setServiceAdapter(serviceAdapterManager);
        thingManager.setServiceAdapter(serviceAdapterManager);
        contextManager.setServiceAdapter(serviceAdapterManager);
    }
}
