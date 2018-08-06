package edu.utexas.mpc.warble3.frontend.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.model.resource.Resource;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.thing.credential.UsernamePasswordCredential;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;

public class AddThingAccessCredentialActivity extends AppCompatActivity {
    private static final String TAG = "AddThingAccessCred";

    public static final String THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA = "edu.texas.mpc.warble3.frontend.thing.AddThingAccessCredentialActivity.THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA";
    public static final String THING_INTENT_EXTRA = "edu.texas.mpc.warble3.frontend.thing.AddThingAccessCredentialActivity.THING_INTENT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String thingAccessCredentialClass = intent.getStringExtra(THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA);
        Bundle thingBundle = intent.getBundleExtra(THING_INTENT_EXTRA);
        final Thing thing = (Thing) thingBundle.getSerializable(THING_INTENT_EXTRA);

        switch (thingAccessCredentialClass) {
            case "UsernamePasswordCredential":
                setContentView(R.layout.activity_add_username_password_credential);

                final EditText username = findViewById(R.id.username_addUsernamePasswordCredential_editText);
                final EditText password = findViewById(R.id.password_addUsernamePasswordCredential_editText);

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
                                newCred.setUser(Resource.getInstance().getUser(currentUsername));
                                newCred.setThing(thing);

                                thing.addThingAccessCredentials(newCred);
                                Resource.getInstance().updateThing(thing);

                                thing.authenticate();
                            }
                            finish();
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
}
