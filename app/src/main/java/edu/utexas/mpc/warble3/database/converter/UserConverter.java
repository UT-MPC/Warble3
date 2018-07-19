package edu.utexas.mpc.warble3.database.converter;

import edu.utexas.mpc.warble3.database.UserDb;
import edu.utexas.mpc.warble3.model.user.User;

public class UserConverter {
    public static User toUser(UserDb userDb) {
        if (userDb == null) {
            return null;
        }
        else {
            User user = new User(userDb.getUsername(), userDb.getPassword());
            user.setEmailAddress(userDb.getEmailAddress());
            return user;
        }
    }

    public static UserDb toUserDb(User user) {
        if (user == null) {
            return null;
        }
        else {
            UserDb userDb = new UserDb(user.getUsername(), user.getPassword());
            userDb.setEmailAddress(user.getEmailAddress());
            return userDb;
        }
    }
}
