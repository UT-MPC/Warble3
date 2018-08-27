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

package edu.utexas.mpc.warble3.frontend.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.async_tasks.AuthenticateAsyncTask;
import edu.utexas.mpc.warble3.model.Warble;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.credential.UsernamePasswordCredential;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;

public class AddThingAccessCredentialActivity extends AppCompatActivity implements AuthenticateAsyncTask.AuthenticateAsyncTaskInterface {
    private static final String TAG = "AddThingAccessCred";

    public static final String THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA = "edu.texas.mpc.warble3.frontend.thing.AddThingAccessCredentialActivity.THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA";
    public static final String THING_INTENT_EXTRA = "edu.texas.mpc.warble3.frontend.thing.AddThingAccessCredentialActivity.THING_INTENT_EXTRA";

    private ProgressBar progressBar;
    private Thing thing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String thingAccessCredentialClass = intent.getStringExtra(THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA);
        Bundle thingBundle = intent.getBundleExtra(THING_INTENT_EXTRA);
        thing = (Thing) thingBundle.getSerializable(THING_INTENT_EXTRA);

        switch (thingAccessCredentialClass) {
            case "UsernamePasswordCredential":
                setContentView(R.layout.activity_add_username_password_credential);

                final EditText username = findViewById(R.id.username_addUsernamePasswordCredential_editText);
                final EditText password = findViewById(R.id.password_addUsernamePasswordCredential_editText);
                progressBar = findViewById(R.id.asyncTaskProgressBar_addUsernamePasswordCredential_progressBar);

                Button submitButton = findViewById(R.id.submit_addUsernamePasswordCredential_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (thing != null) {
                            UsernamePasswordCredential newCred = new UsernamePasswordCredential(username.getText().toString(), password.getText().toString());
                            String currentUsername = SharedPreferenceHandler.getSharedPrefsCurrentUserSettings(AddThingAccessCredentialActivity.this).getString(SharedPreferenceHandler.SHARED_PREFS_USERNAME, null);

                            if (currentUsername == null) {
                                if (Logging.WARN) Log.w(TAG, "Unable to add the new Credential, because the username is unable to be fetched");
                                Toast.makeText(AddThingAccessCredentialActivity.this, "Unable to add the new Credential", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                newCred.setUser(Warble.getInstance().getUser(currentUsername));
                                newCred.setThing(thing);
                                thing.addThingAccessCredentials(newCred);

                                new AuthenticateAsyncTask(AddThingAccessCredentialActivity.this).execute(thing);
                            }
                        }
                        else {
                            Toast.makeText(AddThingAccessCredentialActivity.this, "The thing is null. Potential Bug", Toast.LENGTH_SHORT).show();
                            if (Logging.WARN) Log.w(TAG, "The thing is null. Potential Bug");
                            finish();
                        }
                    }
                });

                break;
            default:
                break;
        }
    }

    @Override
    public void onAuthenticateTaskStart() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onAuthenticateTaskComplete(Thing thing) {
        this.thing = thing;
        Warble.getInstance().updateThing(thing);

        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        finish();
    }
}
