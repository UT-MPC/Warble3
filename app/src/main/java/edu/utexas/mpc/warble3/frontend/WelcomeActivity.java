package edu.utexas.mpc.warble3.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.setup_page.FindBridgeActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindBridgeActivity.class);
                startActivity(intent);
            }
        };

        Button gobutton1 = findViewById(R.id.usernameSend_welcomePage_button);
        gobutton1.setOnClickListener(onClickListener);
        Button gobutton2 = findViewById(R.id.newUsernameSend_welcomePage_button);
        gobutton2.setOnClickListener(onClickListener);
    }
}
