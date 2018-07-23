package edu.utexas.mpc.warble3.setup;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.user.User;

public interface AppDatabaseInterface {
    void addUser(User newUser);
    User getUserByUsername(String username);
    void deleteAllUsers();

    List<Thing> getThings();
    void saveThing(Thing thing);
    void saveThings(List<Thing> things);
}
