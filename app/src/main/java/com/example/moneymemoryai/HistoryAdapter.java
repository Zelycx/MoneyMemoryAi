package com.example.moneymemoryai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Transaction> transactionList;

    public HistoryAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transaction transaction = transactionList.get(position);

        holder.tvType.setText(transaction.getType());
        holder.tvCategory.setText(transaction.getTitle());
        holder.tvDate.setText(transaction.getDate());
        holder.tvDetails.setText(transaction.getDetails());

        if (transaction.getType().equals("Income")) {
            holder.tvType.setTextColor(android.graphics.Color.parseColor("#2E7D32"));
            holder.tvAmount.setTextColor(android.graphics.Color.parseColor("#2E7D32"));
            holder.tvAmount.setText(String.format("+₱%.2f", transaction.getAmount()));
        } else {
            holder.tvType.setTextColor(android.graphics.Color.parseColor("#D32F2F"));
            holder.tvAmount.setTextColor(android.graphics.Color.parseColor("#D32F2F"));
            holder.tvAmount.setText(String.format("-₱%.2f", transaction.getAmount()));
        }

        if (transaction.isExpanded()) {
            holder.layoutDetails.setVisibility(View.VISIBLE);
            holder.tvViewDetails.setText("▲ Hide Details");
        } else {
            holder.layoutDetails.setVisibility(View.GONE);
            holder.tvViewDetails.setText("▼ View Details");
        }

        holder.tvViewDetails.setOnClickListener(v -> {

            transaction.setExpanded(!transaction.isExpanded());

            notifyItemChanged(position);

        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvType;
        TextView tvAmount;
        TextView tvCategory;
        TextView tvDate;
        TextView tvViewDetails;
        TextView tvDetails;
        LinearLayout layoutDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tvType);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvViewDetails = itemView.findViewById(R.id.tvViewDetails);
            tvDetails = itemView.findViewById(R.id.tvDetails);

            layoutDetails = itemView.findViewById(R.id.layoutDetails);
        }
    }
}