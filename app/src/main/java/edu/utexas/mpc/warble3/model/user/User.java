package edu.utexas.mpc.warble3.model.user;

public class User {
    public static final String TAG = "User";

    private String username;
    private String password;
    private String emailAddress;

    public User(String username, String password) {
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof User)) {
            return false;
        }

        User u = (User) object;

        return (this.username.equals(u.username));
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s %s - %s", TAG, username, emailAddress);
        return string;
    }
}
