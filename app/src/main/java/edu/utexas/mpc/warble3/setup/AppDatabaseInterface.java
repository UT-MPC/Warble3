package edu.utexas.mpc.warble3.setup;

import java.util.List;

import edu.utexas.mpc.warble3.model.user.User;

public interface AppDatabaseInterface {
    void addUser(User newUser);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteAllUsers();
}
