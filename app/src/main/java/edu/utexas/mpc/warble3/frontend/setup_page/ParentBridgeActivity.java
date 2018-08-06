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
import edu.utexas.mpc.warble3.frontend.adapter.SimpleAdapter;

public class ParentBridgeActivity extends AppCompatActivity {
    private RecyclerView parentBridgeRecycleView;
    private RecyclerView.LayoutManager parentBridgeLayoutManager;
    private SimpleAdapter parentBridgeAdapter;

    private List<String> parentBridges = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_bridge);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleParentBridge_addLightPage);

        // TODO: Change to list of parent bridges
        parentBridges.add("Bridge 1");
        parentBridges.add("Bridge 2");
        parentBridges.add("Bridge 3");
        parentBridges.add("Bridge 4");

        parentBridgeRecycleView = findViewById(R.id.parentBridge_recyclerView);
        parentBridgeLayoutManager = new LinearLayoutManager(this);
        parentBridgeRecycleView.setLayoutManager(parentBridgeLayoutManager);
        parentBridgeAdapter = new SimpleAdapter(this, parentBridges);
        parentBridgeAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT", parentBridges.get(position));
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        parentBridgeRecycleView.setAdapter(parentBridgeAdapter);
    }
}
