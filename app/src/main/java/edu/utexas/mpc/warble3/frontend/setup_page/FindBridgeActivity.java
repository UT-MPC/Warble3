/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
