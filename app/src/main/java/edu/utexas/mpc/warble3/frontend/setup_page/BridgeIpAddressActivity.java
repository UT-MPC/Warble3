package edu.utexas.mpc.warble3.frontend.setup_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

import edu.utexas.mpc.warble3.R;

public class BridgeIpAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_ip_address);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleIpAddress_addBridgePage);
    }
}
