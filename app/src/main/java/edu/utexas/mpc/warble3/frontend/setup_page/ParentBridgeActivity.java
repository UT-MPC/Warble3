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

public class ParentBridgeActivity extends AppCompatActivity {
    RecyclerView parentBridgeRecycleView;
    RecyclerView.LayoutManager parentBridgeLayoutManager;
    RecyclerView.Adapter parentBridgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_bridge);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleParentBridge_addLightPage);

        // TODO: Change to list of light types
        List<String> parentBridges = new ArrayList<>();
        parentBridges.add("Bridge 1");
        parentBridges.add("Bridge 2");
        parentBridges.add("Bridge 3");
        parentBridges.add("Bridge 4");

        parentBridgeRecycleView = findViewById(R.id.parentBridge_recyclerView);
        parentBridgeLayoutManager = new LinearLayoutManager(this);
        parentBridgeRecycleView.setLayoutManager(parentBridgeLayoutManager);
        parentBridgeAdapter = new SimpleAdapter(this, parentBridges);
        parentBridgeRecycleView.setAdapter(parentBridgeAdapter);
    }
}
