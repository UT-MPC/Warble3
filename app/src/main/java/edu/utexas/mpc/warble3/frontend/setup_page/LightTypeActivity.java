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

public class LightTypeActivity extends AppCompatActivity {
    private RecyclerView lightTypeRecycleView;
    private RecyclerView.LayoutManager lightTypeLayoutManager;
    private SimpleAdapter lightTypeAdapter;

    private List<String> lightTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_type);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleLightType_addLightPage);

        lightTypeRecycleView = findViewById(R.id.lightType_recyclerView);
        lightTypeRecycleView.setHasFixedSize(false);

        lightTypeLayoutManager = new LinearLayoutManager(this);
        lightTypeRecycleView.setLayoutManager(lightTypeLayoutManager);

        // TODO: Change to list of light types
        lightTypes.add("Philips Hue Light");
        lightTypes.add("GE Light");

        lightTypeAdapter = new SimpleAdapter(getApplicationContext(), lightTypes);
        lightTypeAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT", lightTypes.get(position));
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        lightTypeRecycleView.setAdapter(lightTypeAdapter);
    }
}
