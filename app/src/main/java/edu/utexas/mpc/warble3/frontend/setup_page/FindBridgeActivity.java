package edu.utexas.mpc.warble3.frontend.setup_page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.adapter.CriteriaAdapter;

public class FindBridgeActivity extends AppCompatActivity {
    private RecyclerView criteriaRecyclerView;
    private RecyclerView.Adapter criteriaAdapter;
    private RecyclerView.LayoutManager criteriaLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bridge);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleFindBridge_addBridgePage);

        criteriaRecyclerView = findViewById(R.id.criteria_addBridgePage_recyclerView);
        criteriaRecyclerView.setHasFixedSize(true);

        criteriaLayoutManager = new LinearLayoutManager(this);
        criteriaRecyclerView.setLayoutManager(criteriaLayoutManager);

        Map<String, String> criteriaMap = new LinkedHashMap<>();
        criteriaMap.put("Bridge Type", "");
        criteriaMap.put("IP Address", "");

        criteriaAdapter = new CriteriaAdapter(criteriaMap);
        criteriaRecyclerView.setAdapter(criteriaAdapter);
    }
}
