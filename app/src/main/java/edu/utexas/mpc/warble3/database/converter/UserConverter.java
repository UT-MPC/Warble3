package edu.utexas.mpc.warble3.database.converter;

import java.util.ArrayList;
import java.util.List;

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

    public static List<User> toUsers(List<UserDb> userDbs) {
        List<User> users = new ArrayList<>();
        for (UserDb userDb : userDbs) {
            users.add(toUser(userDb));
        }
        return users;
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

    public static List<UserDb> toUserDbs(List<User> users) {
        List<UserDb> userDbs = new ArrayList<>();
        for (User user : users) {
            userDbs.add(toUserDb(user));
        }
        return userDbs;
    }
}
