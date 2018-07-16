package edu.utexas.mpc.warble3.model.thing.credential;

public class UsernamePasswordCredential extends ThingAccessCredential {
    public static final String TAG = "UsernamePasswordCredential";

    private String username;
    private String password;
    private String token;

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
    public String toString() {
        String string = "";
        string += String.format("%s - username \"%s\", password \"%s\"", TAG, username, password);
        return string;
    }
}
