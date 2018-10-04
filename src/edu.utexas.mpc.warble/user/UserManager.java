/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.utexas.mpc.warble.user;

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
