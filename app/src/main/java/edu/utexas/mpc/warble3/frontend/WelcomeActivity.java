package edu.utexas.mpc.warble3.frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.UserDb;
import edu.utexas.mpc.warble3.database.converter.UserConverter;
import edu.utexas.mpc.warble3.model.user.User;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;

public class WelcomeActivity extends AppCompatActivity {
    public static final String TAG = "WelcomeActivity";

    public static String SHARED_PREFS_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = SharedPreferenceHandler.getSharedPrefsCurrentUserSettings(this);
        if (sharedPrefs.getString(SHARED_PREFS_USERNAME, null) != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_welcome);

            final EditText username_editText = findViewById(R.id.username_welcomePage_editText);
            final EditText new_username_editText = findViewById(R.id.newUsername_welcomePage_editText);

            View.OnClickListener onClickListener1 = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String enteredUsername = username_editText.getText().toString();
                    if (Logging.INFO) Log.i(TAG, String.format("Entered username is %s", enteredUsername));

                    if (!User.validateUsername(enteredUsername)) {
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.invalidUsername_welcomePage), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AppDatabase db = AppDatabase.getDatabase(WelcomeActivity.this);
                        User currentUser = UserConverter.toUser(db.userDbDao().getUserDb(enteredUsername));
                        if (currentUser == null) {
                            if (Logging.INFO) Log.i(TAG, String.format("Username %s is NOT found in database", enteredUsername));

                            Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.usernameNotFound_welcomePage), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (Logging.INFO) Log.i(TAG, String.format("Username %s is found in database", enteredUsername));

                            SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(WelcomeActivity.this);
                            editor.putString(SHARED_PREFS_USERNAME, enteredUsername);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            };
            Button gobutton1 = findViewById(R.id.usernameSend_welcomePage_button);
            gobutton1.setOnClickListener(onClickListener1);

            View.OnClickListener onClickListener2 = new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    String enteredNewUsername = new_username_editText.getText().toString();
                    if (Logging.INFO) Log.i(TAG, String.format("Entered new username is %s", enteredNewUsername));

                    if (!User.validateUsername(enteredNewUsername)) {
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.invalidUsername_welcomePage), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AppDatabase db = AppDatabase.getDatabase(WelcomeActivity.this);

                        try {
                            db.userDbDao().insert(new UserDb(enteredNewUsername, "password"));

                            if (Logging.INFO) Log.i(TAG, String.format("Username %s is successfully inserted to database", enteredNewUsername));

                            Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.newUsernameInserted_welcomePage), Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(WelcomeActivity.this);
                            editor.putString(SHARED_PREFS_USERNAME, enteredNewUsername);
                            editor.apply();
                        }
                        catch (SQLiteConstraintException e) {
                            if (Logging.INFO) Log.i(TAG, String.format("Username %s is already in database", enteredNewUsername));

                            Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.duplicateUsername_welcomePage), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
            Button gobutton2 = findViewById(R.id.newUsernameSend_welcomePage_button);
            gobutton2.setOnClickListener(onClickListener2);
        }
    }
}
