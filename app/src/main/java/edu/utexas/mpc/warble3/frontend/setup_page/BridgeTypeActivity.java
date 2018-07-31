package edu.utexas.mpc.warble3.frontend.setup_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.adapter.SimpleAdapter;

public class BridgeTypeActivity extends AppCompatActivity {
    private RecyclerView bridgeTypeRecyclerView;
    private RecyclerView.LayoutManager bridgeTypeLayoutManager;
    private SimpleAdapter bridgeTypeAdapter;

    private List<String> bridgeTypes = new ArrayList<>();

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
        bridgeTypes.add("Philips Hub");
        bridgeTypes.add("Wink Hub");

        bridgeTypeAdapter = new SimpleAdapter(getApplicationContext(), bridgeTypes);
        bridgeTypeAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT", bridgeTypes.get(position));
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        bridgeTypeRecyclerView.setAdapter(bridgeTypeAdapter);
    }
}
