package edu.utexas.mpc.warble3.model.resource;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.ThingManager;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.component.ThingState;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.GE.GEDiscovery;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueUPnPDiscovery;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.Wink.WinkDiscovery;
import edu.utexas.mpc.warble3.model.thing.discovery.Discovery;
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
        List<Discovery> discoveries = new ArrayList<>();

        // TODO: Discovery list has to be managed somewhere else
        discoveries.add(new PhilipsHueUPnPDiscovery());
        discoveries.add(new WinkDiscovery());
        discoveries.add(new GEDiscovery());

        List<Thing> things = new ArrayList<>();
        for(Discovery discovery: discoveries) {
            List<? extends Thing> things1 = discovery.onDiscover();
            List<? extends Thing> things2 = discovery.onDiscoverDescendants();
            if (things1 != null) {
                things.addAll(things1);
            }
            if (things2 != null) {
                things.addAll(things2);
            }
        }
    }

    public List<Thing> getThings() {
        thingManager.getThings();
        return null;
    }

    public void authenticateThings() {
        // TODO: implement
    }

    public void addThing(Thing newThing) {
        // TODO: implement
    }

    public void updateThing(Thing thing) {
        // TODO: implement
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
