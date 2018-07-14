package edu.utexas.mpc.warble3.frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;

public class WelcomeActivity extends AppCompatActivity {
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
                    SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(WelcomeActivity.this);
                    editor.putString(SHARED_PREFS_USERNAME, username_editText.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            };
            Button gobutton1 = findViewById(R.id.usernameSend_welcomePage_button);
            gobutton1.setOnClickListener(onClickListener1);

            View.OnClickListener onClickListener2 = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(WelcomeActivity.this);
                    editor.putString(SHARED_PREFS_USERNAME, new_username_editText.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            };
            Button gobutton2 = findViewById(R.id.newUsernameSend_welcomePage_button);
            gobutton2.setOnClickListener(onClickListener2);
        }
    }
}
