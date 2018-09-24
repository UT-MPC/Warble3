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

package edu.utexas.mpc.warble3.warble;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.warble.selector.AllThingSelector;
import edu.utexas.mpc.warble3.warble.selector.AbstractSelector;
import edu.utexas.mpc.warble3.warble.thing.ThingManager;
import edu.utexas.mpc.warble3.warble.thing.command.Command;
import edu.utexas.mpc.warble3.warble.thing.command.Response;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.component.ThingState;
import edu.utexas.mpc.warble3.warble.user.DuplicateUsernameException;
import edu.utexas.mpc.warble3.warble.user.InvalidPasswordException;
import edu.utexas.mpc.warble3.warble.user.InvalidUsernameException;
import edu.utexas.mpc.warble3.warble.user.User;
import edu.utexas.mpc.warble3.warble.user.UserManager;

public class Warble {
    private static final String TAG = "Warble";

    private UserManager userManager;
    private ThingManager thingManager;

    private List<AbstractSelector> template = new ArrayList<>();

    public Warble() {
        userManager = UserManager.getInstance();
        thingManager = ThingManager.getInstance();
    }

    // =========== Selector ===========
    public void addSelector(AbstractSelector selector) {
        if (selector != null) {
            template.add(selector);
        }
    }

    public List<AbstractSelector> getTemplate() {
        return template;
    }

    public void setTemplate(List<AbstractSelector> template) {
        this.template = template;
    }

    public void clearTemplate() {
        this.template.clear();
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

    public List<Thing> fetch() {
        List<Thing> things = new ArrayList<>();

        if ((template == null) || (template.size() == 0)) {
            template.add(new AllThingSelector());
        }

        for (AbstractSelector selector : template) {
            List<Thing> newThings = selector.fetch();
            if (newThings != null) {
                things.addAll(newThings);
            }
        }

        if (things.size() == 0) {
            return null;
        }
        else {
            return things;
        }
    }

    public void authenticateThings() {
        thingManager.authenticateThings(fetch());
    }

    public void updateThing(Thing thing) {
        thingManager.saveThing(thing);
    }

    public Thing loadThing(Thing thing) {
        return thingManager.loadThing(thing);
    }

    // =========== Command ============
    public Response sendCommand(Command command, Thing thing) {
        return ThingManager.getInstance().sendCommand(command, thing);
    }
}
