package edu.utexas.mpc.warble3.setup;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.model.user.User;

public interface AppDatabaseInterface {
    void addUser(User newUser);
    User getUserByUsername(String username);
    void deleteAllUsers();

    List<Thing> getThings();
    long saveThing(Thing thing);
    List<Long> saveThings(List<Thing> things);

    List<Connection> getConnections();
    long saveConnection(Connection connection);
    List<Long> saveConnections(List<Connection> connections);
}
