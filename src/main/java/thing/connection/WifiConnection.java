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

package thing.connection;

import thing.component.Thing;

import java.util.logging.Logger;

public class WifiConnection extends Connection {
    private static final String TAG = WifiConnection.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public WifiConnection() {
        super();
        setDirectionalType(DIRECTIONAL_TYPE.UNIDIRECTIONAL);
    }

    public WifiConnection(Thing source, Thing destination) {
        super(source, destination);
        setDirectionalType(DIRECTIONAL_TYPE.UNIDIRECTIONAL);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof WifiConnection)) {
            return false;
        }

        WifiConnection c = (WifiConnection) object;

        return (this.getSource().equals(c.getSource()));
    }

    @Override
    public String toStoreableText() {
        return null;
    }

    @Override
    public void fromStoreableText(String storeableText) {

    }
}
