package edu.utexas.mpc.warble3.frontend.setup_page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.adapter.SimpleAdapter;

public class BridgeTypeActivity extends AppCompatActivity {
    private RecyclerView bridgeTypeRecyclerView;
    private RecyclerView.LayoutManager bridgeTypeLayoutManager;
    private RecyclerView.Adapter bridgeTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_type);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleBridgeType_addBridgePage);

        bridgeTypeRecyclerView = findViewById(R.id.bridgeType_recyclerView);
        bridgeTypeRecyclerView.setHasFixedSize(false);

        bridgeTypeLayoutManager = new LinearLayoutManager(this);
        bridgeTypeRecyclerView.setLayoutManager(bridgeTypeLayoutManager);

        // TODO: Change to list of bridge types
        List<String> bridgeTypes = new ArrayList<>();
        bridgeTypes.add("Philips Hub");
        bridgeTypes.add("Wink Hub");

        bridgeTypeAdapter = new SimpleAdapter(getApplicationContext(), bridgeTypes);
        bridgeTypeRecyclerView.setAdapter(bridgeTypeAdapter);
    }
}
