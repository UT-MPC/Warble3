package edu.utexas.mpc.warble3.frontend.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.utexas.mpc.warble3.R;

public class CriteriaAdapter extends RecyclerView.Adapter<CriteriaAdapter.ViewHolder> {

    private Map<String, String> criteriaMap;

    public CriteriaAdapter(Map<String, String> criteriaMap) {
        this.criteriaMap = criteriaMap;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView criteriaName_textView;
        TextView selectedValue_textView;
        ImageView rightArrow_imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            criteriaName_textView = itemView.findViewById(R.id.criteriaName_textView);
            selectedValue_textView = itemView.findViewById(R.id.selectedValue_textView);
            rightArrow_imageView = itemView.findViewById(R.id.rightArrow_imageView);
        }
    }

    @NonNull
    @Override
    public CriteriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_criteria, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CriteriaAdapter.ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(criteriaMap.keySet());
        String key = keys.get(position);

        holder.criteriaName_textView.setText(key);
        holder.selectedValue_textView.setText(criteriaMap.get(key));
    }

    @Override
    public int getItemCount() {
        return criteriaMap.size();
    }
}
