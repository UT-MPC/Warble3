package edu.utexas.mpc.warble3.frontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import edu.utexas.mpc.warble3.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button gobutton1 = findViewById(R.id.usernameSend_welcomePage_button);
        Button gobutton2 = findViewById(R.id.newUsernameSend_welcomePage_button);
    }
}
