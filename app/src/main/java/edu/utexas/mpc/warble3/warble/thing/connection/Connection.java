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

package edu.utexas.mpc.warble3.warble.thing.connection;

import java.io.Serializable;

import edu.utexas.mpc.warble3.database.interfaces.ConnectionStoreable;
import edu.utexas.mpc.warble3.database.interfaces.Storeable;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public abstract class Connection implements Serializable, Storeable, ConnectionStoreable {
    private static final String TAG = "Connection";

    public static enum DIRECTIONAL_TYPE {
        UNIDIRECTIONAL,
        BIDIRECTIONAL
    }

    private Thing source;
    private Thing destination;

    private DIRECTIONAL_TYPE directionalType;

    private long dbid;

    public Connection() {
    }

    public Connection(Thing source, Thing destination) {
        this.source = source;
        this.destination = destination;
    }

    public Thing getSource() {
        return source;
    }

    public void setSource(Thing source) {
        this.source = source;
    }

    public Thing getDestination() {
        return destination;
    }

    public void setDestination(Thing destination) {
        this.destination = destination;
    }

    public DIRECTIONAL_TYPE getDirectionalType() {
        return directionalType;
    }

    public void setDirectionalType(DIRECTIONAL_TYPE directionalType) {
        this.directionalType = directionalType;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    @Override
    public void onPostStore(long dbid) {
        setDbid(dbid);
    }

    @Override
    public void onPostLoad(long dbid) {
        setDbid(dbid);
    }

    @Override
    public abstract boolean equals(Object object);
}
