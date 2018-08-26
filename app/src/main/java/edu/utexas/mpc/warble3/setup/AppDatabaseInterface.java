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

package edu.utexas.mpc.warble3.setup;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.connection.Connection;
import edu.utexas.mpc.warble3.model.thing.credential.ThingAccessCredential;
import edu.utexas.mpc.warble3.model.user.User;

public interface AppDatabaseInterface {
    void onInitialize();
    void onTerminate();

    void addUser(User newUser);
    User getUserByUsername(String username);
    void deleteAllUsers();

    List<Thing> getThings();
    long saveThing(Thing thing);
    List<Long> saveThings(List<Thing> things);
    void deleteAllThings();

    List<Connection> getConnections();
    long saveConnection(Connection connection);
    List<Long> saveConnections(List<Connection> connections);
    void deleteAllConnections();

    List<ThingAccessCredential> getThingAccessCredentials();
    long saveThingAccessCredential(ThingAccessCredential thingAccessCredential);
    List<Long> saveThingAccessCredentials(List<ThingAccessCredential> thingAccessCredentials);
    void deleteAllThingAccessCredentials();
}
