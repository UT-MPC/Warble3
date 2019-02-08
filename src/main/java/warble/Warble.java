package warble;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import context.ContextManager;
import correlation.DBinding;
import correlation.Plan;
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
import user.DuplicateUsernameException;
import user.InvalidPasswordException;
import user.InvalidUsernameException;
import user.User;
import user.UserManager;

public class Warble {
    private static final String TAG = Warble.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private UserManager userManager;
    private ThingManager thingManager;
    private ContextManager contextManager;

    private ServiceAdapterManager serviceAdapterManager;

    /**
     * Warble constructor. No parameters need to be given.
     */
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

    /**
     * Authenticate username and password from records in database. If they don't match, null is returned.
     *
     * @param username unique username registered in the database
     * @param password password to check with the password in the database
     * @return the User object with the specified username.
     * If username-password combination is incorrect, returns null
     */
    public User authenticateUser(String username, String password) {
        return userManager.authenticateUser(username, password);
    }

    /**
     * Create a new User instance and storing it to the database
     *
     * @param username a unique new username
     * @param password user-defined password
     * @throws DuplicateUsernameException
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     */
    public void createUser(String username, String password) throws DuplicateUsernameException, InvalidUsernameException, InvalidPasswordException {
        userManager.createUser(username, password);
    }

    // ============ Thing =============

    /**
     * Triggering discovery process
     */
    public void discoverThings() {
        thingManager.discover();
    }

    /**
     * Trigger discovery process followed by authenticating the discovered things
     *
     * @param authenticateThings boolean option (true: authenticate, false: not authenticate)
     */
    public void discoverThings(boolean authenticateThings) {
        discoverThings();
        if (authenticateThings) {
            authenticateThings();
        }
    }

    public Thing fetch(String uuid) {
        return thingManager.getThing(uuid);
    }

    /**
     * Fetch all relevant Things matching the Selectors requirements in template
     *
     * @param template list of Selectors that define the criteria on how the things are selected
     * @return all relevant Things
     */
    public List<Thing> fetch(List<Selector> template) {
        return fetch(template, -1);
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
        List<Thing> things = thingManager.getThings();
        if (things == null) {
            return null;
        }

        List<Thing> returnThings = new ArrayList<>();

        if (template == null) {
            template = new ArrayList<>();
        }

        if (template.size() == 0) {
            template.add(new AllThingSelector());
        }

        for (Selector selector : template) {
            List<Thing> selectedThings = selector.select(things);
            if (selectedThings != null) {
                returnThings.addAll(selectedThings);
            }
        }

        if (returnThings.size() == 0) {
            return null;
        } else if (numberOfThings == -1) {
            return returnThings;
        } else if (returnThings.size() > numberOfThings) {
            return returnThings.subList(0, numberOfThings);
        } else {
            return null;
        }
    }

    /**
     * Reject the list of Things as a feedback to Warble that the Things returned by fetch() are not relevant
     *
     * @param things things to be rejected
     */
    public void reject(List<Thing> things) {
        // TODO: Implement
    }

    /**
     * Authenticate all discovered Things
     */
    public void authenticateThings() {
        thingManager.authenticateThings(fetch(null, -1));
    }

    /**
     * Save by overwriting the Thing in the database
     *
     * @param thing an updated Thing to be saved into the database
     */
    public void updateThing(Thing thing) {
        thingManager.saveThing(thing);
    }

    /**
     * Get the most updated Thing instance from the database
     *
     * @param thing a Thing to be loaded from the database
     * @return Thing instance with the most updated information from the database
     */
    public Thing loadThing(Thing thing) {
        return thingManager.loadThing(thing);
    }

    // =========== Command ============

    /**
     * Send an internal Command to the Thing. Warble uses Commands to control the Thing.
     *
     * @param command an internal Command to be sent to the Thing
     * @param thing   Thing to be controlled
     * @return Response after sending the command
     */
    public Response sendCommand(Command command, Thing thing) {
        return thingManager.sendCommand(contextManager.getContext(), command, thing);
    }

    // =========== Service Adapter ============

    /**
     * Get the assigned Service Adapter specified by SERVICE_ADAPTER_TYPE_INPUT
     *
     * @param serviceAdapterType Service Adapter Input type to be obtained
     * @return Service Adapter instance according to the given type
     */
    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT serviceAdapterType) {
        return serviceAdapterManager.getServiceAdapter(serviceAdapterType);
    }

    /**
     * Get the assigned Service Adapter specified by SERVICE_ADAPTER_TYPE_OUTPUT
     *
     * @param serviceAdapterType Service Adapter Output type to be obtained
     * @return Service Adapter instance according to the given type
     */
    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT serviceAdapterType) {
        return serviceAdapterManager.getServiceAdapter(serviceAdapterType);
    }

    /**
     * Set the new Service Adapter specified by SERVICE_ADAPTER_TYPE_INPUT
     *
     * @param serviceAdapterType Service Adapter Input type to be set
     * @param serviceAdapter     Service Adapter to be set
     */

    public void setServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT serviceAdapterType, ServiceAdapter serviceAdapter) {
        serviceAdapterManager.setServiceAdapter(serviceAdapterType, serviceAdapter);

        userManager.setServiceAdapter(serviceAdapterManager);
        thingManager.setServiceAdapter(serviceAdapterManager);
        contextManager.setServiceAdapter(serviceAdapterManager);
    }

    /**
     * Set the new Service Adapter specified by SERVICE_ADAPTER_TYPE_OUTPUT
     *
     * @param serviceAdapterType Service Adapter Output type to be set
     * @param serviceAdapter     Service Adapter to be set
     */
    public void setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT serviceAdapterType, ServiceAdapter serviceAdapter) {
        serviceAdapterManager.setServiceAdapter(serviceAdapterType, serviceAdapter);

        userManager.setServiceAdapter(serviceAdapterManager);
        thingManager.setServiceAdapter(serviceAdapterManager);
        contextManager.setServiceAdapter(serviceAdapterManager);
    }

    // =========== Dynamic Binding ============

    /**
     * @param template
     * @return
     */
    public DBinding dynamicBind(List<Selector> template) {
        return dynamicBind(template, -1);
    }

    /**
     * @param template
     * @param numberOfThings
     * @return
     */
    public DBinding dynamicBind(List<Selector> template, int numberOfThings) {
        DBinding dBinding = new DBinding(this, template, numberOfThings);
        return dBinding;
    }

    public DBinding dynamicBind(List<Selector> template, int numberOfThings, Plan plan) {
        DBinding dBinding = new DBinding(this, template, numberOfThings);
        return dBinding;
    }
}
