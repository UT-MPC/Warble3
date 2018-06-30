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

public class FindLightActivity extends AppCompatActivity implements CriterionAdapter.mClickListener {
    private RecyclerView criteriaRecyclerView;
    private RecyclerView.Adapter criteriaAdapter;
    private RecyclerView.LayoutManager criteriaLayoutManager;

    private List<Criterion> criteria = new ArrayList<>();
    private List<Intent> criterionIntents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_light);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleFindLight_addLightPage);

        criteria.add(new Criterion("Light Type", "", Criterion.LIST_TYPE));
        criterionIntents.add(new Intent(this, LightTypeActivity.class));
        criteria.add(new Criterion("Parent Bridge", "", Criterion.LIST_TYPE));
        criterionIntents.add(new Intent(this, ParentBridgeActivity.class));
        criteria.add(new Criterion("Serial Number", "", Criterion.EDITTEXT_TYPE));
        criterionIntents.add(new Intent(this, SerialNumberActivity.class));

        criteriaRecyclerView = findViewById(R.id.criteria_addLightPage_recyclerView);
        criteriaLayoutManager = new LinearLayoutManager(this);
        criteriaRecyclerView.setLayoutManager(criteriaLayoutManager);
        criteriaAdapter = new CriterionAdapter(this, criteria);
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
            // TODO: Handle
        }
    }
}
