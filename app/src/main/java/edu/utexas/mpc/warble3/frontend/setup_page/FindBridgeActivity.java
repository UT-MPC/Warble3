package edu.utexas.mpc.warble3.frontend.setup_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.adapter.Criterion;
import edu.utexas.mpc.warble3.frontend.adapter.CriterionAdapter;

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

        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new Criterion(getResources().getString(R.string.titleBridgeType_addBridgePage),
                "",
                Criterion.LIST_TYPE,
                new Intent(getApplicationContext(), BridgeTypeActivity.class)
        ));
        criteria.add(new Criterion(getResources().getString(R.string.titleIpAddress_addBridgePage),
                "",
                Criterion.EDITTEXT_TYPE,
                new Intent(getApplicationContext(), BridgeIpAddressActivity.class)
        ));

        criteriaAdapter = new CriterionAdapter(getApplicationContext(), criteria);
        criteriaRecyclerView.setAdapter(criteriaAdapter);
    }
}
