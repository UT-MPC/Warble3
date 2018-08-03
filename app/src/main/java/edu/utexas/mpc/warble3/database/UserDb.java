package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Locale;

@Entity(tableName = "UserDb")
public class UserDb {
    @Ignore
    public static final String TAG = "UserDb";

    @PrimaryKey
    @NonNull
    private String username;
    private String password;
    private String emailAddress;

    @Ignore
    public UserDb() {}

    public UserDb(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
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
    public String toString() {
        String string = "";
        string += String.format(Locale.getDefault(), "%s", getUsername());
        return string;
    }
}