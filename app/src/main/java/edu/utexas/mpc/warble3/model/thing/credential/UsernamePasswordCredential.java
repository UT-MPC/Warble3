package edu.utexas.mpc.warble3.model.thing.credential;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernamePasswordCredential extends ThingAccessCredential {
    public static final String TAG = "UsernamePasswordCredential";

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
            }
        }
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s - username \"%s\", password \"%s\"", TAG, username, password);
        return string;
    }
}
