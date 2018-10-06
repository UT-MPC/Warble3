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

package edu.utexas.mpc.warble.thing.credential;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernamePasswordCredential extends ThingAccessCredential {
    private static final String TAG = UsernamePasswordCredential.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private String username;
    private String password;
    private String token;

    public UsernamePasswordCredential() {
        super();
    }

    public UsernamePasswordCredential(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toStoreableText() {
        return String.format("username:%s password:%s token:%s", username, password, token);
    }

    @Override
    public void fromStoreableText(String storeableText) {
        Pattern pattern = Pattern.compile("(username:)([a-zA-Z0-9]*)\\s+(password:)([a-zA-Z0-9]*)\\s+(token:)([a-zA-Z0-9]*)");
        Matcher matcher = pattern.matcher(storeableText);

        if (matcher.find()) {
            username = matcher.group(2);
            if (!matcher.group(4).equals("")) {
                password = matcher.group(4);
            }
            if (!matcher.group(6).equals("")) {
                token = matcher.group(6);
                if (token.equals("null")) {
                    token = null;
                }
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean returnVal = super.equals(object);

        if (!(object instanceof UsernamePasswordCredential)) {
            return false;
        }

        UsernamePasswordCredential t = (UsernamePasswordCredential) object;

        return returnVal &&
                (this.username.equals(t.username)) &&
                (this.password.equals(t.password)) &&
                (this.token.equals(t.token));
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s - username \"%s\", password \"%s\"", TAG, username, password);
        return string;
    }
}
