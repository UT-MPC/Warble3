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
 *
 */

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
