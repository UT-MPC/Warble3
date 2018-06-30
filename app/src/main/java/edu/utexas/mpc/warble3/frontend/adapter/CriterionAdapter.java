package edu.utexas.mpc.warble3.frontend.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.utexas.mpc.warble3.R;

public class CriterionAdapter extends RecyclerView.Adapter<CriterionAdapter.ViewHolder> {

    private Context context;
    private List<Criterion> criteria;

    public CriterionAdapter(Context context, List<Criterion> criteria) {
        this.context = context;
        this.criteria = criteria;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView criteriaName_textView;
        TextView selectedValue_textView;
        // ImageView rightArrow_imageView; // TODO: Not needed

        public ViewHolder(final Context context, final View itemView) {
            super(itemView);
            this.context = context;
            criteriaName_textView = itemView.findViewById(R.id.criteriaName_textView);
            selectedValue_textView = itemView.findViewById(R.id.selectedValue_textView);
            // rightArrow_imageView = itemView.findViewById(R.id.rightArrow_imageView); // TODO: Not needed

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((mClickListener) view.getContext()).mClick(view, getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public CriterionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_criteria, parent, false);
        return new ViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CriterionAdapter.ViewHolder holder, int position) {
        holder.criteriaName_textView.setText(criteria.get(position).name);
        holder.selectedValue_textView.setText(criteria.get(position).value);
    }

    @Override
    public int getItemCount() {
        return criteria.size();
    }

    public interface mClickListener {
        void mClick(View view, int position);
    }
}
