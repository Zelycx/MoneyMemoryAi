package com.example.moneymemoryai;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistoryItem> historyList;

    public HistoryAdapter(List<HistoryItem> historyList) {
        this.historyList = historyList;
    }


    @Override
    public int getItemViewType(int position) {
        return historyList.get(position).getType();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == HistoryItem.TYPE_HEADER) {

            View view = inflater.inflate(
                    R.layout.item_history_header,
                    parent,
                    false
            );

            return new HeaderViewHolder(view);

        } else {

            View view = inflater.inflate(
                    R.layout.item_transaction,
                    parent,
                    false
            );

            return new TransactionViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            int position
    ) {

        HistoryItem item = historyList.get(position);


        // HEADER
        if (holder instanceof HeaderViewHolder) {

            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvHeader.setText(
                    item.getHeaderTitle()
            );


        }


        // TRANSACTION
        else if (holder instanceof TransactionViewHolder) {

            TransactionViewHolder transactionHolder =
                    (TransactionViewHolder) holder;

            Transaction transaction =
                    item.getTransaction();


            transactionHolder.tvType.setText(transaction.getType());
            transactionHolder.tvCategory.setText(transaction.getTitle());
            transactionHolder.tvDate.setText(transaction.getTime());
            transactionHolder.tvDetails.setText(transaction.getDetails());


            // Income / Expense color
            if (transaction.getType().equals("Income")) {

                transactionHolder.tvType.setTextColor(
                        Color.parseColor("#2E7D32")
                );

                transactionHolder.tvAmount.setTextColor(
                        Color.parseColor("#2E7D32")
                );

                transactionHolder.tvAmount.setText(
                        String.format("+₱%.2f", transaction.getAmount())
                );

            } else {

                transactionHolder.tvType.setTextColor(
                        Color.parseColor("#D32F2F")
                );

                transactionHolder.tvAmount.setTextColor(
                        Color.parseColor("#D32F2F")
                );

                transactionHolder.tvAmount.setText(
                        String.format("-₱%.2f", transaction.getAmount())
                );
            }


            // Expand / Collapse state
            if (transaction.isExpanded()) {

                transactionHolder.layoutDetails.setVisibility(View.VISIBLE);
                transactionHolder.tvViewDetails.setText("▲ Hide Details");

            } else {

                transactionHolder.layoutDetails.setVisibility(View.GONE);
                transactionHolder.tvViewDetails.setText("▼ View Details");

            }


            transactionHolder.tvViewDetails.setOnClickListener(v -> {

                transaction.setExpanded(
                        !transaction.isExpanded()
                );

                notifyItemChanged(position);

            });

        }
    }


    @Override
    public int getItemCount() {
        return historyList.size();
    }



    // Transaction cards holder
    public static class TransactionViewHolder
            extends RecyclerView.ViewHolder {


        TextView tvType;
        TextView tvAmount;
        TextView tvCategory;
        TextView tvDate;
        TextView tvViewDetails;
        TextView tvDetails;

        LinearLayout layoutDetails;


        public TransactionViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);


            tvType = itemView.findViewById(R.id.tvType);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);

            tvViewDetails =
                    itemView.findViewById(R.id.tvViewDetails);

            tvDetails =
                    itemView.findViewById(R.id.tvDetails);

            layoutDetails =
                    itemView.findViewById(R.id.layoutDetails);
        }
    }



    // Date Header Holder
    public static class HeaderViewHolder
            extends RecyclerView.ViewHolder {


        TextView tvHeader;


        public HeaderViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            tvHeader =
                    itemView.findViewById(R.id.tvHeader);
        }
    }
}