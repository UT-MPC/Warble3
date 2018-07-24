package edu.utexas.mpc.warble3.model.user;

import android.database.sqlite.SQLiteConstraintException;

import edu.utexas.mpc.warble3.database.AppDatabase;

public class UserManager {
    private static final String TAG = "UserManager";

    private static UserManager instance = new UserManager();

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private Boolean validateUsername(String username) {
        return !username.equals("");
    }

    private Boolean validatePassword(String password) {
        return !password.equals("");
    }

    public void createUser(String username, String password) throws DuplicateUsernameException, InvalidUsernameException, InvalidPasswordException {
        if (!validateUsername(username)) {
            throw new InvalidUsernameException();
        }
        else if (!validatePassword(password)) {
            throw new InvalidPasswordException();
        }
        else {
            try {
                AppDatabase.getDatabase().addUser(new User(username, password));
            }
            catch (SQLiteConstraintException e) {
                throw new DuplicateUsernameException();
            }
        }
    }

    public User getUser(String username) {
        return AppDatabase.getDatabase().getUserByUsername(username);
    }

    public User authenticateUser(String username, String password) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }
        else if (user.getUsername().equals(username) & user.getPassword().equals(password)) {
            return user;
        }
        else {
            return null;
        }
    }
}
