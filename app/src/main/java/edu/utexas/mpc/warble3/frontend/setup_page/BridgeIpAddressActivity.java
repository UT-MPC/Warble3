package edu.utexas.mpc.warble3.frontend.setup_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import edu.utexas.mpc.warble3.R;

public class BridgeIpAddressActivity extends AppCompatActivity {
    private EditText ipAddress_editText;
    private Button ipAddressSend_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_ip_address);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleIpAddress_addBridgePage);

        ipAddress_editText = findViewById(R.id.ipAddress_editText);
        ipAddressSend_button = findViewById(R.id.ipAddressSend_button);

        ipAddressSend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("RESULT", ipAddress_editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
