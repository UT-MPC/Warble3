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
import edu.utexas.mpc.warble3.frontend.adapter.Criterion;
import edu.utexas.mpc.warble3.frontend.adapter.CriterionAdapter;

public class FindBridgeActivity extends AppCompatActivity implements CriterionAdapter.mClickListener {
    private RecyclerView criteriaRecyclerView;
    private RecyclerView.Adapter criteriaAdapter;
    private RecyclerView.LayoutManager criteriaLayoutManager;

    private List<Criterion> criteria = new ArrayList<>();
    private List<Intent> criterionIntents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bridge);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleFindBridge_addBridgePage);

        criteria.add(new Criterion(getResources().getString(R.string.titleBridgeType_addBridgePage), "", Criterion.LIST_TYPE));
        criterionIntents.add(new Intent(getApplicationContext(), BridgeTypeActivity.class));
        criteria.add(new Criterion(getResources().getString(R.string.titleIpAddress_addBridgePage), "", Criterion.EDITTEXT_TYPE));
        criterionIntents.add(new Intent(getApplicationContext(), BridgeIpAddressActivity.class));

        criteriaRecyclerView = findViewById(R.id.criteria_addBridgePage_recyclerView);
        criteriaRecyclerView.setHasFixedSize(true);
        criteriaLayoutManager = new LinearLayoutManager(this);
        criteriaRecyclerView.setLayoutManager(criteriaLayoutManager);
        criteriaAdapter = new CriterionAdapter(getApplicationContext(), criteria);
        criteriaRecyclerView.setAdapter(criteriaAdapter);
    }

    @Override
    public void mClick(View view, int position) {
        Intent intentToExecute = criterionIntents.get(position);
        startActivityForResult(intentToExecute, position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            criteria.get(requestCode).setValue(data.getStringExtra("RESULT"));
            criteriaAdapter.notifyDataSetChanged();
        }
        else {
            // TODO: handle
        }
    }
}
