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

package edu.utexas.mpc.warble3.frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;
import edu.utexas.mpc.warble3.util.WarbleHandler;
import edu.utexas.mpc.warble3.warble.Warble;
import edu.utexas.mpc.warble3.warble.user.DuplicateUsernameException;
import edu.utexas.mpc.warble3.warble.user.InvalidPasswordException;
import edu.utexas.mpc.warble3.warble.user.InvalidUsernameException;
import edu.utexas.mpc.warble3.warble.user.User;

public class WelcomeActivity extends AppCompatActivity {
    public static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Warble warble = WarbleHandler.getInstance();

        SharedPreferences sharedPrefs = SharedPreferenceHandler.getSharedPrefsCurrentUserSettings(this);
        if (sharedPrefs.getString(SharedPreferenceHandler.SHARED_PREFS_USERNAME, null) != null) {
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
                    String enteredPassword = "password";
                    if (Logging.INFO) Log.i(TAG, String.format("Entered username = %s, password = %s", enteredUsername, enteredPassword));

                    User user = warble.authenticateUser(enteredUsername, enteredPassword);

                    if (user == null) {
                        if (Logging.INFO) Log.i(TAG, String.format("Authentication unsuccessful", enteredUsername));
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.authenticationUnsuccessful_welcomePage), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (Logging.INFO) Log.i(TAG, String.format("Username %s is found in database", enteredUsername));

                        SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(WelcomeActivity.this);
                        editor.putString(SharedPreferenceHandler.SHARED_PREFS_USERNAME, enteredUsername);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            };
            Button gobutton1 = findViewById(R.id.usernameSend_welcomePage_button);
            gobutton1.setOnClickListener(onClickListener1);

            View.OnClickListener onClickListener2 = new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    String enteredNewUsername = new_username_editText.getText().toString();
                    String enteredNewPassword = "password";
                    if (Logging.INFO) Log.i(TAG, String.format("Entered new username = %s, new password = %s", enteredNewUsername, enteredNewPassword));

                    try {
                        warble.createUser(enteredNewUsername, enteredNewPassword);
                        if (Logging.INFO) Log.i(TAG, String.format("Username %s is successfully created", enteredNewUsername));
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.newUsernameInserted_welcomePage), Toast.LENGTH_SHORT).show();
                    }
                    catch (DuplicateUsernameException e) {
                        if (Logging.INFO) Log.i(TAG, String.format("Username %s is already in database", enteredNewUsername));
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.duplicateUsername_welcomePage), Toast.LENGTH_SHORT).show();
                    }
                    catch (InvalidUsernameException e) {
                        if (Logging.INFO) Log.i(TAG, String.format("Username %s is invalid", enteredNewUsername));
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.invalidUsername_welcomePage), Toast.LENGTH_SHORT).show();
                    }
                    catch (InvalidPasswordException e) {
                        if (Logging.INFO) Log.i(TAG, String.format("Password %s is invalid", enteredNewPassword));
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.invalidPassword_welcomePage), Toast.LENGTH_SHORT).show();
                    }

                    SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(WelcomeActivity.this);
                    editor.putString(SharedPreferenceHandler.SHARED_PREFS_USERNAME, enteredNewUsername);
                    editor.apply();
                }
            };
            Button gobutton2 = findViewById(R.id.newUsernameSend_welcomePage_button);
            gobutton2.setOnClickListener(onClickListener2);
        }
    }
}
