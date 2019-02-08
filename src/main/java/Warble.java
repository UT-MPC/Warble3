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

import java.util.List;
import java.util.logging.Logger;

import correlation.DBinding;
import correlation.Plan;
import selector.Selector;
import service.SERVICE_ADAPTER_TYPE_INPUT;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;
import service.ServiceAdapter;
import thing.command.Command;
import thing.command.Response;
import thing.component.Thing;
import user.DuplicateUsernameException;
import user.InvalidPasswordException;
import user.InvalidUsernameException;
import user.User;

/**
 * Warble's main class as well as the API class.
 * It defines the main API functions.
 */
public class Warble {
    private static final String TAG = Warble.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private warble.Warble warble;

    /**
     * Warble constructor. No parameters need to be given.
     */
    public Warble() {
        warble = new warble.Warble();
    }

    // ============= User =============

    /**
     * Authenticate username and password from records in database. If they don't match, null is returned.
     *
     * @param username unique username registered in the database
     * @param password password to check with the password in the database
     * @return the User object with the specified username.
     * If username-password combination is incorrect, returns null
     */
    public User authenticateUser(String username, String password) {
        return warble.authenticateUser(username, password);
    }

    /**
     * Create a new User instance and storing it to the database
     * @param   username                    a unique new username
     * @param   password                    user-defined password
     * @throws DuplicateUsernameException
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     */
    public void createUser(String username, String password) throws DuplicateUsernameException, InvalidUsernameException, InvalidPasswordException {
        warble.createUser(username, password);
    }

    // ============ Thing =============

    /**
     * Triggering discovery process
     */
    public void discoverThings() {
        warble.discoverThings();
    }

    /**
     * Trigger discovery process followed by authenticating the discovered things
     * @param   authenticateThings  boolean option (true: authenticate, false: not authenticate)
     */
    public void discoverThings(boolean authenticateThings) {
        warble.discoverThings(authenticateThings);
    }

    public Thing fetch(String uuid) {
        return warble.fetch(uuid);
    }

    /**
     * Fetch all relevant Things matching the Selectors requirements in template
     * @param   template  list of Selectors that define the criteria on how the things are selected
     * @return all relevant Things
     */
    public List<Thing> fetch(List<Selector> template) {
        return warble.fetch(template);
    }

    /**
     * Fetch the relevant Things matching the Selectors requirements in template
     * @param   template  list of Selectors that define the criteria on how the Things are selected
     * @return list of relevant things
     */

    /**
     * Fetch the relevant Things matching the Selectors requirements in template
     *
     * @param template       list of Selectors that define the criteria on how the Things are selected
     * @param numberOfThings the maximum number of relevant Things to be fetched. -1 means all relevant Things.
     * @return list of relevant things. If no selected Things is available, will return null
     */
    public List<Thing> fetch(List<Selector> template, int numberOfThings) {
        return fetch(template, numberOfThings);
    }

    /**
     * Reject the list of Things as a feedback to Warble that the Things returned by fetch() are not relevant
     * @param things  things to be rejected
     */
    public void reject(List<Thing> things) {
        warble.reject(things);
    }

    /**
     * Authenticate all discovered Things
     */
    public void authenticateThings() {
        warble.authenticateThings();
    }

    /**
     * Save by overwriting the Thing in the database
     * @param thing  an updated Thing to be saved into the database
     */
    public void updateThing(Thing thing) {
        warble.updateThing(thing);
    }

    /**
     * Get the most updated Thing instance from the database
     * @param thing  a Thing to be loaded from the database
     * @return Thing instance with the most updated information from the database
     */
    public Thing loadThing(Thing thing) {
        return warble.loadThing(thing);
    }

    // =========== Command ============

    /**
     * Send an internal Command to the Thing. Warble uses Commands to control the Thing.
     * @param command  an internal Command to be sent to the Thing
     * @param thing    Thing to be controlled
     * @return Response after sending the command
     */
    public Response sendCommand(Command command, Thing thing) {
        return warble.sendCommand(command, thing);
    }

    // =========== Service Adapter ============

    /**
     * Get the assigned Service Adapter specified by SERVICE_ADAPTER_TYPE_INPUT
     * @param serviceAdapterType  Service Adapter Input type to be obtained
     * @return Service Adapter instance according to the given type
     */
    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT serviceAdapterType) {
        return warble.getServiceAdapter(serviceAdapterType);
    }

    /**
     * Get the assigned Service Adapter specified by SERVICE_ADAPTER_TYPE_OUTPUT
     * @param serviceAdapterType  Service Adapter Output type to be obtained
     * @return Service Adapter instance according to the given type
     */
    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT serviceAdapterType) {
        return warble.getServiceAdapter(serviceAdapterType);
    }

    /**
     * Set the new Service Adapter specified by SERVICE_ADAPTER_TYPE_INPUT
     * @param serviceAdapterType  Service Adapter Input type to be set
     * @param serviceAdapter      Service Adapter to be set
     */

    public void setServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT serviceAdapterType, ServiceAdapter serviceAdapter) {
        warble.setServiceAdapter(serviceAdapterType, serviceAdapter);
    }

    /**
     * Set the new Service Adapter specified by SERVICE_ADAPTER_TYPE_OUTPUT
     * @param serviceAdapterType  Service Adapter Output type to be set
     * @param serviceAdapter      Service Adapter to be set
     */
    public void setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT serviceAdapterType, ServiceAdapter serviceAdapter) {
        warble.setServiceAdapter(serviceAdapterType, serviceAdapter);
    }

    // =========== Dynamic Binding ============

    /**
     * @param template
     * @param numberOfThings
     * @return
     */
    public DBinding dynamicBind(List<Selector> template, int numberOfThings, Plan plan) {
        return warble.dynamicBind(template, numberOfThings, plan);
    }
}
