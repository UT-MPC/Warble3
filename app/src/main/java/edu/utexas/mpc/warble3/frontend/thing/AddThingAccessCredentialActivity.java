package edu.utexas.mpc.warble3.frontend.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.model.resource.Resource;

public class AddThingAccessCredentialActivity extends AppCompatActivity {
    private static final String TAG = "AddThingAccessCredentialActivity";

    public static final String THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA = "edu.texas.mpc.warble3.frontend.thing.AddThingAccessCredentialActivity.THING_ACCESS_CREDENTIAL_CLASS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String thingAccessCredentialClass = intent.getStringExtra(THING_ACCESS_CREDENTIAL_CLASS_INTENT_EXTRA);

        switch (thingAccessCredentialClass) {
            case "UsernamePasswordCredential":
                setContentView(R.layout.activity_add_username_password_credential);

                EditText username = findViewById(R.id.username_addUsernamePasswordCredential_editText);
                EditText password = findViewById(R.id.password_addUsernamePasswordCredential_editText);

                Button submitButton = findViewById(R.id.submit_addUsernamePasswordCredential_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : when submit button is clicked, save the credential to database.
                    }
                });

                break;
            default:
                break;
        }
    }
}
