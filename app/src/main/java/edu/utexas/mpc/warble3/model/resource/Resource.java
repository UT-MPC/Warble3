package edu.utexas.mpc.warble3.model.resource;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.ThingManager;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;
import edu.utexas.mpc.warble3.model.user.DuplicateUsernameException;
import edu.utexas.mpc.warble3.model.user.InvalidPasswordException;
import edu.utexas.mpc.warble3.model.user.InvalidUsernameException;
import edu.utexas.mpc.warble3.model.user.User;
import edu.utexas.mpc.warble3.model.user.UserManager;

public class Resource {
    private static final String TAG = "Resource";

    private static Resource instance;

    private UserManager userManager;
    private ThingManager thingManager;

    private Resource() {
        userManager = UserManager.getInstance();
        thingManager = ThingManager.getInstance();
    }

    public static void initializeInstance() {
        if (instance == null) {
            instance = new Resource();
        }
    }

    public static Resource getInstance() {
        if (instance != null) {
            return instance;
        }
        else {
            throw new NullPointerException("Resource is uninitialized");
        }
    }

    // User
    public User getUser(String username) {
        userManager.getUser(username);
        return null;
    }

    public User authenticateUser(String username, String password) {
        return userManager.authenticateUser(username, password);
    }

    public void createUser(String username, String password) throws DuplicateUsernameException, InvalidUsernameException, InvalidPasswordException {
        userManager.createUser(username, password);
    }

    // Thing
    public void discoverThings() {
        thingManager.discover();
    }

    public List<Thing> getThings() {
        return thingManager.getThings();
    }

    public void authenticateThings() {
        // TODO: implement
    }

    public void addThing(Thing newThing) {
        // TODO: implement
    }

    public void updateThing(Thing thing) {
        thingManager.saveThing(thing);
    }

    public void setThingState(Thing thing, ThingState thingState) {
        // TODO: implement
    }

    @Deprecated
    public void removeThing(Thing thing) {
        // TODO: implement
    }

    public void addThings(List<Thing> newThings) {
        // TODO: implement
    }

    public void updateThings(List<Thing> things) {
        // TODO: implement
    }

    @Deprecated
    public void removeThings(List<Thing> things) {
        // TODO: implement
    }
}
