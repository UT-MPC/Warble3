package edu.utexas.mpc.warble3.model.resource;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.user.User;

public class Resource {
    private static final String TAG = "Resource";

    private static Resource instance;

    private List<User> users = new ArrayList<>();
    private List<Thing> things = new ArrayList<>();

    private Resource() {}

    public static Resource getInstance() {
        if (instance == null) {
            instance = new Resource();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User newUser) {
        // TODO: implement
    }

    public void addUsers(List<User> newUsers) {
        // TODO: implement
    }

    public List<Thing> getThings() {
        return things;
    }

    public void addThings(List<Thing> newThings) {
        // TODO: implement
    }
}
